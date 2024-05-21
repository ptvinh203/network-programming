# jsp/servlet application

## Create new project

```sh
mvn archetype:generate -DgroupId=com.example -DartifactId=web -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```

## Getting started

1. **Add dependency to pom.xml**

```java
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>2.2</version>
        </plugin>
    </plugins>
</build>
```

2. **Start project**

- PLEASE MOVE TO WEB FOLDER BEFORE

```sh
mvn clean && mvn install && mvn tomcat7:run
```
