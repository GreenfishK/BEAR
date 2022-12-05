#!/bin/bash
# Params:
# dataset
# policy
# ic0

# Set variables
baseDir=/starvers_eval
script_dir=/starvers_eval/scripts
#graphdb_port=$((7200))
export JAVA_HOME=/opt/java/openjdk
export PATH=/opt/java/openjdk/bin:$PATH
export GDB_JAVA_OPTS="$GDB_JAVA_OPTS -Dgraphdb.home.data=${baseDir}/databases/graphdb_${policy}_${dataset}/data"

# Clean repository
rm -rf ${baseDir}/databases/graphdb_${policy}_${dataset}

repositoryID=${policy}_${dataset}
cp ${script_dir}/2_preprocess/configs/graphdb-config_template.ttl ${script_dir}/2_preprocess/configs/graphdb-config.ttl
sed -i "s/{{repositoryID}}/$repositoryID/g" ${script_dir}/2_preprocess/configs/graphdb-config.ttl

# Ingest ic0
/opt/graphdb/dist/bin/preload -c ${script_dir}/2_preprocess/configs/graphdb-config.ttl ${baseDir}/rawdata/${dataset}/${ic0} --force

# Start database server and run in background
/opt/graphdb/dist/bin/graphdb -d -s

# Wait until server is up
# GraphDB doesn't deliver HTTP code 200 for some reason ...
echo "Waiting..."
while [[ $(curl -I http://Starvers:7200 2>/dev/null | head -n 1 | cut -d$' ' -f2) != '406' ]]; do
    sleep 1s
done
echo "GraphDB server is up"