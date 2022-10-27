#!/bin/bash

policies=("tbsh") # cb tbsf tbsh tb
datasets=("bearb-day") # bearb-day beara bearc
triple_stores=("jenatdb2") # jenatdb2
graphdb_port=$((7200))
jenatdb2_port=$((3030))

#docker network create 4_evaluation_default
# Start containers with their respective policy, dataset and triple store
for triple_store in ${triple_stores[@]}; do

    if [ ${triple_store} == "jenatdb2" ]; then
    mkdir -p /run/configuration
        for policy in ${policies[@]}; do
            for dataset in ${datasets[@]}; do
                # Export variables
                export JAVA_HOME=/usr/local/openjdk-11
                export PATH=/usr/local/openjdk-11/bin:$PATH
                export FUSEKI_HOME=/jena-fuseki
                export JAVA_OPTIONS="-Xmx5g -Xms5g"
                export ADMIN_PASSWORD=starvers

                # Start database server and run in background
                cp /starvers_eval/configs/jenatdb2_${policy}_${dataset}/*.ttl /run/configuration
                nohup /jena-fuseki/fuseki-server --port=3030 --tdb2 &

                # Wait until server is up
                echo "Waiting..."
                while [[ $(curl -I http://localhost:3030 2>/dev/null | head -n 1 | cut -d$' ' -f2) != '200' ]]; do
                    sleep 1s
                done
                echo "Fuseki server is up"

                # Evaluate
                /starvers_eval/python_venv/bin/python3 -u /starvers_eval/scripts/4_evaluation/evaluate.py ${triple_store} ${policy} ${dataset} ${jenatdb2_port} # > /starvers_eval/output/logs/queries.txt

                # Stop database server
            done
        done

    elif [ ${triple_store} == "graphdb" ]; then
        for policy in ${policies[@]}; do
            for dataset in ${datasets[@]}; do
                echo "to be implemented"

            done
        done
    fi
 
done

### Evaluate ################################################################
# Check if all GraphDB (and JenaTDB2 (TODO)) instances are running.
#active_containers=0
#while [ $active_containers -ne $((${#policies[@]} * ${#datasets[@]})) ]; do
#    docker-compose logs | grep -w "Started GraphDB in workbench mode" > log.txt
#    active_containers=$((`sed -n '$=' log.txt`))
#done


#docker network rm 4_evaluation_default

# TODO: free heap space in graphdb by deactivating the repository after querying it

