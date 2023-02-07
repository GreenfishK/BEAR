#!/bin/bash

# Logging variables
log_file=/starvers_eval/output/logs/download/downloads.txt
log_timestamp() { date +%Y-%m-%d\ %A\ %H:%M:%S; }
log_level="root:INFO"

# Prepare directories and files
rm -rf /starvers_eval/output/logs/download
mkdir -p /starvers_eval/output/logs/download
> $log_file

# Path variables
snapshot_dir=`grep -A 2 '[general]' /starvers_eval/configs/eval_setup.toml | awk -F '"' '/snapshot_dir/ {print $2}'`

# Other variables
datasets=$(grep -E '\[datasets\.[A-Za-z_]+\]' /starvers_eval/configs/eval_setup.toml | awk -F "." '{print $2}' | sed 's/.$//')

for dataset in ${datasets[@]}; do
    download_link_snapshots=`grep -A 5 -E "\[datasets\.${dataset}\]"/starvers_eval/configs/eval_setup.toml | grep 'download_link_snapshots' | awk '{print $3}' | sed 's/"//g'`
    download_link_ng_dataset=`grep -A 5 -E "\[datasets\.${dataset}\]"/starvers_eval/configs/eval_setup.toml | grep 'download_link_ng_dataset' | awk '{print $3}' | sed 's/"//g'`
    yn_nested_archives=`grep -A 5 -E "\[datasets\.${dataset}\]"/starvers_eval/configs/eval_setup.toml | grep 'yn_nested_archives' | awk '{print $3}' | sed 's/"//g'`

    # Download
    echo "$(log_timestamp) ${log_level}: Downloading ${dataset} snapshots..." >> $log_file
    wget -t 3 -c -P /starvers_eval/rawdata/${dataset} ${download_link_snapshots}
    mkdir -p /starvers_eval/rawdata/beara/${snapshot_dir}
    echo "$(log_timestamp) ${log_level}: Extracting ${dataset} snapshots..." >> $log_file
    tar -xf /starvers_eval/rawdata/beara/alldata.IC.nt.tar.gz -C /starvers_eval/rawdata/${dataset}/${snapshot_dir}
    
    if [[ $yn_nested_archives == "true" ]]; then
        cd /starvers_eval/rawdata/bearb_hour/${snapshot_dir}
        for f in *.gz ; do gzip -d < "$f" > /starvers_eval/rawdata/bearb_hour/${snapshot_dir}/"${f%.*}" ; done
        rm *.gz
    fi
    
    echo "$(log_timestamp) ${log_level}: Downloading ${dataset} named graphs dataset..." >> $log_file
    wget -t 3 -c -P /starvers_eval/rawdata/${dataset} ${download_link_ng_dataset}
    echo "$(log_timestamp) ${log_level}: Extracting ${dataset} named graphs dataset..." >> $log_file
    gzip -d < /starvers_eval/rawdata/${dataset}/alldata.TB.nq.gz > /starvers_eval/rawdata/${dataset}/alldata.TB.nq
    # for CB and CBNG policy: empty initial delete changeset
    > /starvers_eval/rawdata/${dataset}/empty.nt 
    echo "$(log_timestamp) ${log_level}: Downloading and extracting ${dataset} datasets finished." >> $log_file

done