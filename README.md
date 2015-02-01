# Armada
Armada is a (planned) open-souce Spring/Hibernate based project that monitors and manages your docker containers.

Armada has two main components: core "Armada" (this repository), and the "Agent". Armada runs as a deployable Jetty jar, which wraps a spring based service for collecting container log data from different Agents. Your Agents will run locally on whichever server you wish to track containers on.

Armada will consume data from the Agents, storing them in a MySQL database (as of now), and provides a operations API for a Javascript front end, currently included as well in the Jetty server. The Armada front end can be accessed at localhost:8085/ and will plan to feature graphs of currently active containers across all your environments, a list of running containers across all your environments, and realtime statistics from those running containers regarding CPU, Memory and Filesystem usage.

Docker is planned to have a spring 2015 release date, currently being developed by:

Paul Trzyna
John Davidson
Deonte Johnson
John Plante

Special thanks to DePaul University, and Professor Xiaoping Jia.

