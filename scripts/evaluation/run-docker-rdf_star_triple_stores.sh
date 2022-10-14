#!/bin/bash

case "$1" in
  beara)
    datasetdir=~/.BEAR/rawdata/beara/ 
    querydir=~/.BEAR/queries/queries_beara/
    outputdir=~/.BEAR/output/time/beara/
    limit=9
    ;;
  bearb-day)
    datasetdir=~/.BEAR/rawdata/bearb/day/ 
    querydir=~/.BEAR/queries/queries_bearb/
    outputdir=~/.BEAR/output/time/bearb-day/
    limit=88
    ;;
  bearb-hour)
    datasetdir=~/.BEAR/rawdata/bearb/hour/ 
    querydir=~/.BEAR/queries/queries_bearb/
    outputdir=~/.BEAR/output/time/bearb-hour/
    limit=1298
    ;;
  bearb-instant)
    datasetdir=~/.BEAR/rawdata/bearb/instant/ 
    querydir=~/.BEAR/queries/queries_bearb/
    outputdir=~/.BEAR/output/time/bearb-instant/
    limit=21045
    ;;
  bearc)
    datasetdir=~/.BEAR/rawdata/bearc/ 
    querydir=~/.BEAR/queries/queries_bearc/
    outputdir=~/.BEAR/output/time/bearc/
    limit=31
    ;;
  *)
    echo "Usage: $0 {beara|bearb-day|bearb-hour}"
    exit 2
    ;;
esac


policies="cb" # tb tb_star_h tb_star_f ic cb cbtb
categories="mat" # mat diff ver
queries=$(cd ${querydir} && ls -v)
tripleStores="JenaTDB" # JenaTDB GraphDB

echo ${queries}
# If building with maven-assembly-plugin use: java -cp target/tdbQuery-0.8-jar-with-dependencies.jar org/ai/wu/ac/at/rdfArchive/tools/RDFArchive_query \
for tripleStore in ${tripleStores[@]}; do
    for policy in ${policies[@]}; do

        case $policy in 
            cb) ds_name="alldata.CB_computed.nt" ;;
            tb) ds_name="alldata.TB.nq" ;;
            tb_star_f) ds_name="alldata.TB_star_flat.ttl" ;;
            tb_star_h) ds_name="alldata.TB_star_hierarchical.ttl" ;;
            *) echo "Other polices than timestamp-based are not covered yet" ;;
        esac

        for category in ${categories[@]}; do
            for query in ${queries[@]}; do

            echo "===== Running docker for ${policy}, ${category}, ${query}, ${tripleStore} ===== \n"
            docker run \
                -it \
                --rm \
                -v ${datasetdir}:/var/data/dataset/ \
                -v ${querydir}:/var/data/queries/ \
                -v ${outputdir}:/var/data/output/ \
                bear-rdfstarstores \
                java -cp target/rdfstoreQuery-0.8.jar org/ai/wu/ac/at/rdfstarArchive/tools/RDFArchive_query \
                    -e ${limit} \
                    -j 1 \
                    -p ${policy} \
                    -d /var/data/dataset/${ds_name} \
                    -r spo \
                    -c ${category} \
                    -T ${tripleStore} \
                    -a /var/data/queries/${query} \
                    -t /var/data/output/time-${policy}-${category}-$(echo ${query} | sed "s/\//-/g").csv 

            done
        done
    done
done

# Move to directory with local host name and local timestamp
lokal_timestamp="$(TZ=UTC-1 date "+%Y-%m-%dT%H:%M:%S")"
sudo mkdir ${outputdir}/${HOSTNAME}-${lokal_timestamp}
sudo mv ${outputdir}/time* ${outputdir}/${HOSTNAME}-${lokal_timestamp}
sudo mv ${outputdir}dataset_infos.csv ${outputdir}/${HOSTNAME}-${lokal_timestamp}
