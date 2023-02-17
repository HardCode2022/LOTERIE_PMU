# TEST_PMU [PMU - Exercice technique.pdf](https://github.com/HardCode2022/TEST_PMU/files/10770548/PMU.-.Exercice.technique.pdf)
#####################################################################REALISATION############################################
Stack:
-java 17
-Spring boot
-Maven
-Bus Kafka
-Swagger
-Junit 5 Mockito
-PostgreSQL 
-Api Rest orienté MicroService
###############################################################################KAFKA BUS####################################
POUR CONSULTER LES MESSAGE DANS LE BUS KAFKA (Il faut installer kafka en local et respecter les commandes ci-dessous) 
##Pour lancer le server zookeeper: se positionné à la racine du dossier kafka sur votre C: et lancer cette commande
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
##Ensuite, ouvrir une autre invite de commande à la racine et lancer la commande ci-dessous pour lancer le server kafka
.\bin\windows\kafka-server-start.bat .\config\server.properties
##Pour creer un topic voici la commande: 
kafka-topics.bat --create --bootstrap-server localhost:9092 --topic course_created
##Pour consulter la console du producer:
kafka-console-producer.bat --broker-list localhost:9092 --topic course_created
##Pour consulter la console du consumer pour voir les messages:
kafka-console-consumer.bat --topic course_created --bootstrap-server localhost:9092 --from-beginning
###############################################################################################################################

