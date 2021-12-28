Reproduce experiment
==============
Follow the instructions below to reproduce this experiment.
## Install docker 
If you have docker installed already, continue with [Build docker images](https://github.com/GreenfishK/BEAR/blob/master/README.md#build-docker-images)
install docker on [Ubuntu](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository) or [other OS](https://docs.docker.com/get-docker/)
[get access as a non-root user](https://docs.docker.com/engine/install/linux-postinstall/#manage-docker-as-a-non-root-user). Find the commands from that page bellow (07.12.2021).
```
(sudo groupadd docker)
sudo usermod -aG docker $USER 
newgrp docker
docker run hello-world
```

## Build docker images
Go to BEAR/src/Jena_TDB and build with docker. The docker file uses a maven image to build and package the project with dependencies: 
```
docker build -t bear-jena .
```
Go to BEAR/src/HDT and build with docker.
```
docker build -t bear-hdt .
```
### Troubleshoot
Error1: “Docker does not have a release file”

Fix: Edit etc/apt/source.list.d/docker.list and set the release version to an Ubuntu version for which there is a docker release, e.g. “focal”: https://stackoverflow.com/questions/41133455/docker-repository-does-not-have-a-release-file-on-running-apt-get-update-on-ubun 

## Get data
Create the local data directories for this experiment. Download the datasets & queries and compute the changesets from the ICs to build the RDF* dataset. See [here](https://github.com/GreenfishK/BEAR/tree/master/data).

## Load data into Jena and HDT
Use the scripts from our [scripts directory](https://github.com/GreenfishK/BEAR/tree/master/scripts/load_data) to load the data. These scripts assume that you already created a .BEAR directory in your home directory. Execute the scripts one by one. After termination following new files will be added (marked with *):

```
├── databases
│   ├── tdb-bearb-hour
│   │   ├── cb
│   │   │   ├── *0  
│   │   │   │   ├── *add  
│   │   │   │   │   └── *jena database files 
│   │   │   │   ├── *del  
│   │   │   │   │   └── *jena database files  
│   │   │   │   ├── *.  
│   │   │   │   ├── *.  
│   │   │   │   ├── *.  
│   │   │   ├── *1298  
│   │   │   │   ├── *add  
│   │   │   │   │   └── *jena database files 
│   │   │   │   └── *del  
│   │   │   │       └── *jena database files  
│   │   └── ic
│   │   │   ├── *0  
│   │   │   │   ├── *jena database files  
│   │   │   ├── *.  
│   │   │   ├── *.  
│   │   │   ├── *.  
│   │   │   ├── *1298  
│   │   │   │   └── *jena database files 
│   │   ├── tb
│   │   │   ├── *jena database files
│   │   └── tb_star 
│   │       └── *jena database files
│   └── hdt-bearb-hour
│       ├── ic  
│       │   ├── *0.hdt  
│       │   ├── *.  
│       │   ├── *.  
│       │   ├── *.  
│       │   └── *1298.hdt  
│       └── cb  
│           ├── *0.add.hdt  
│           ├── *1.add.hdt  
│           ├── *1.del.hdt  
│           ├── *.  
│           ├── *.  
│           ├── *.  
│           ├── *1298.del.hdt  
│           └── *1298.del.hdt  
└── output
    └── logs
        └── *log files from data import  

```

## Run queries and log performance
Run the queries via docker for [Jena](https://github.com/GreenfishK/BEAR/blob/master/scripts/evaluation/run-docker-tdb.sh) and HDT to evaluate the triple store vendors' performance for different archiving policies and query categories.

## Plot performance measurements
Use the [python script](https://github.com/GreenfishK/BEAR/blob/master/scripts/plot_tb_and_tb_star.py) to plot the performance across all versions for different archiving policies, query categories and query sets.

Contact
==============
filip.kovacevic@tuwien.ac.at

# Original OSTRICH README
see README_orig.txt
