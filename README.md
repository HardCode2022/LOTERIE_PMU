# TEST_PMU
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
