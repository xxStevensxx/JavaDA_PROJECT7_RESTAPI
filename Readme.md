Technical:
SpringToolSuite Version: 4.8.1.RELEASE
Java 8
Thymeleaf
Bootstrap v.4.3.1


1)  Vérifier que vos bdd en local ne porte pas le nom test sinon


    a) Modifier les nom dans le fichier application-prod.properties par le nom voulu
    
    
    b) Modifier le nom de bdd dans le script data.sql situer dans le dossier doc
    
2) Lancer le script sql via workbench
6) Cleaner puis compiler l'app
7) Lancer l'app via cette commande : mvn spring-boot:run -Dspring-boot.run.profiles=prod 
        
        a) en mode test mvn spring-boot:run -Dspring-boot.run.profiles=test
        b) pour lancer les tests un mvn clean install suffira.
        
9) Enjoy !!
