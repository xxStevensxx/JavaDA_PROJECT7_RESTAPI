Technical:
Framework: Version: 4.8.1.RELEASE
Java 8
Thymeleaf
Bootstrap v.4.3.1
Setup with Intellij IDE
Create project from Initializr: File > New > project > Spring Initializr
Add lib repository into pom.xml
Add folders
Source root: src/main/java
View: src/main/resources
Static: src/main/resource/static
Create database with name "demo" as configuration in application.properties
Run sql script to create table doc/data.sql
Implement a Feature
Create mapping domain class and place in package com.nnk.springboot.domain
Create repository class and place in package com.nnk.springboot.repositories
Create controller class and place in package com.nnk.springboot.controllers
Create view files and place in src/main/resource/templates
Write Unit Test
Create unit test and place in package com.nnk.springboot in folder test > java
Security
Create user service to load user from database and place in package com.nnk.springboot.services
Add configuration class and place in package com.nnk.springboot.config
