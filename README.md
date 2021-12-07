﻿# BEAR
BEAR Benchmark on RDF archives

_This repo has been modified for running OSTRICH experiments_

[Learn more on the BEAR website](https://aic.ai.wu.ac.at/qadlod/bear.html), which contains all raw data and queries.

_[Original repo](https://github.com/webdata/BEAR)_

Publication
==============
This benchmark is provided as part of the paper:

_BEAR: Benchmarking the Efficiency of RDF Archiving. Javier D. Fernández, Jurgen Umbrich, Axel Polleres and Magnus knuth. Evaluating Query and Storage Strategies for RDF Archives. Proceedings of the 12th International Conference on Semantic Systems, 2016._

~~~~
@inproceedings{fernandez2016Evaluating,
 author = {Fern\'{a}ndez, Javier D. and Umbrich, J{\"u}rgen and Polleres, Axel and Knuth, Magnus},
 title = {Evaluating Query and Storage Strategies for RDF Archives},
 booktitle = {Proceedings of the 12th International Conference on Semantic Systems},
 series = {SEMANTICS '16},
 year={2016}
}
~~~~
Contents
==============
- src: includes the source code of the benchmark for three archiving policies: IC, CB and TB.
- plots: includes plots of the experiments.
- data: includes datasets, queries and scripts to run the experiments.

Reproduce experiment
==============
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
Go to BEAR/src/Jena_TDB and build docker with: 
```
docker build -t bear-jena .
```
Go to BEAR/src/HDT and build docker with: 
```
docker build -t bear-hdt .
```
Error1: “Docker does not have a release file”

Fix: Edit etc/apt/source.list.d/docker.list and set the release version to an Ubuntu version for which there is a docker release, e.g. “focal”: https://stackoverflow.com/questions/41133455/docker-repository-does-not-have-a-release-file-on-running-apt-get-update-on-ubun 

## Get data
See [here](https://github.com/GreenfishK/BEAR/tree/master/data).

## Load data into Jena and HDT
Use the scripts from our [data directory](https://github.com/GreenfishK/BEAR/tree/master/data) to load the data. Execute the scripts one by one. After termination following new files will be added (marked with *):

```
home/.BEAR/  
.
.
.
├── tdb-bearb-hour  
│   ├── cb  
│   │   ├── *0  
│   │   │   ├── *add  
│   │   │   │   ├── *jena database files  
│   │   │   ├── *del  
│   │   │   │   └── *jena database files  
│   │   │   ├── *.  
│   │   │   ├── *.  
│   │   │   ├── *.  
│   │   ├── *1298  
│   │   │   ├── *add  
│   │   │   │   ├── *jena database files  
│   │   │   └── *del  
│   │   │       └── *jena database files  
│   ├── ic  
│   │   ├── *0  
│   │   │   ├── *jena database files  
│   │   ├── *.  
│   │   ├── *.  
│   │   ├── *.  
│   │   ├── *1298  
│   │   │   ├── jena database files  
│   └── tb  
│       ├── *jena database files  
├── hdt-bearb-hour  
│   ├── ic  
│   │   ├── *0.hdt  
│   │   ├── *.  
│   │   ├── *.  
│   │   ├── *.  
│   │   ├── *1298.hdt  
│   ├── cb  
│   │   ├── *0.add.hdt  
│   │   ├── *1.add.hdt  
│   │   ├── *1.del.hdt  
│   │   ├── *.  
│   │   ├── *.  
│   │   ├── *.  
│   │   ├── *1298.del.hdt  
│   │   ├── *1298.del.hdt  
└── output  
    └──*log files from data import   
```

## Run queries and log performance
Run the queries via docker for [Jena](https://github.com/GreenfishK/BEAR/tree/master/src/jena_TDB), HDT and Jena_RDF_star.

Contact
==============
filip.kovacevic@tuwien.ac.at
