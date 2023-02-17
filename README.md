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
##Pour lancer le server zookeeper: se positionné à la racine du dossier kafka et lancer cette commande
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
##Ensuite, ouvrir une autre commande à la racine et lance cette commande pour lancer le server kafka
.\bin\windows\kafka-server-start.bat .\config\server.properties
##pour creer un topic 
kafka-topics.bat --create --bootstrap-server localhost:9092 --topic course_created
##Pour consulter la console du producer (Se placer dans le dossier config)
kafka-console-producer.bat --broker-list localhost:9092 --topic course_created
##Pour consulter la console du consumer(Se placer dans le dossier config)
kafka-console-consumer.bat --topic course_created --bootstrap-server localhost:9092 --from-beginning
##Pour arreter le server zookeeper:
.\bin\windows\zookeeper-server-stop.bat .\config\zookeeper.properties
##Pour arreter le server kafka:
.\bin\windows\kafka-server-stop.bat .\config\server.properties
###############################################################################################################################

