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
Go to BEAR/rdfstarArchive and build the java rdfstoreQuery project with docker. The docker file uses a maven image to build and package the project with dependencies: 
```
docker build -t bear-rdfstarstores .
```
Go to the built jar, which should be in `/var/lib/docker/overlay2/<latest_build_id>/diff/target/rdfstoreQuery.0.9.jar` and add following line to the file `META-INF/services/org.eclipse.rdf4j.rio.RDFParserFactory` :
`org.eclipse.rdf4j.rio.nquads.NQuadsParserFactory` \
The reason is that we have two dependencies - rdf4j-rio-turtle and rdf4j-rio-nquads - which are both implementations of the same interface. That is why the RDFParserFactory gets overriden with whichever is listed first in the dependencies in pom.xml (df4j-rio-turtle in our case). 

### Troubleshoot
Error1: “Docker does not have a release file”

Fix: Edit etc/apt/source.list.d/docker.list and set the release version to an Ubuntu version for which there is a docker release, e.g. “focal”: https://stackoverflow.com/questions/41133455/docker-repository-does-not-have-a-release-file-on-running-apt-get-update-on-ubun 

## Get data
Create the local data directories for this experiment. Download the datasets & queries and either build the RDF-star dataset from the BEAR-B ICs and change sets or use our two pre-computed RDF-star datasets. See [here](https://github.com/GreenfishK/BEAR/tree/master/data).

## Run the evaluation script 
Run the built jar file via docker using our [bash script](https://github.com/GreenfishK/BEAR/blob/master/scripts/evaluation) to evaluate data ingestion and query performance with Jena and GraphDB for the three timestamp-based archiving policies (tb (=named graphs), tb\_rdf\_star\_h, tb\_rdf\_star\_f) and materialization queries. 

## Plot performance measurements
Use the [python script](https://github.com/GreenfishK/BEAR/blob/master/scripts/visualization.py) to plot the performance across all versions for different timestamp-based archiving policies, query categories and query sets.

Contact
==============
filip.kovacevic@tuwien.ac.at

# Original OSTRICH README
see [https://github.com/rdfostrich/BEAR](https://github.com/rdfostrich/BEAR)
