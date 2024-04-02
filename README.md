# quarkus-crud-demo

The fastest CRUD in the west

```
curl -LOv https://github.com/vaadin/base-starter-flow-quarkus/archive/refs/heads/v24.zip
unzip 24.zip
mv base-starter-flow-quarkus-24 qcd
```

edit pom.xml
```
    <groupId>qcd</groupId>
    <artifactId>qcd</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>17</maven.compiler.release>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>

        <maven.compiler.parameters>true</maven.compiler.parameters>
        <failOnMissingWebXml>false</failOnMissingWebXml>

        <vaadin.version>24.3.8</vaadin.version>

        <quarkus.platform.version>3.9.1</quarkus.platform.version>
        <surefire-plugin.version>3.0.0</surefire-plugin.version>

    </properties>
```

add extensions
```
./mvnw quarkus:add-extension -Dextensions="agroal,jdbc-postgresql,hibernate-orm,hibernate-orm-panache"
```

fix package

run app
```
./mvnw quarkus:dev
```


check db health

MainView.java
```
@Inject
DataSource ds;

public Map<String, String> getHealthMap() {
    try (var conn = ds.getConnection();
        var stmt = conn.createStatement();
        var rs = stmt.executeQuery("SELECT 1+1")){
            var ok = rs.next() && 2 == rs.getInt(1);
            if (ok) 
                return Map.of("status", "ok");
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }   
    return Map.of("status", "error");
}

@PostConstruct
public void init(){
    var hcButton = new Button("Check Health", e -> {
        var status = getHealthMap().get("status");
        add(new Paragraph("Health status: " + status));
    });
    add(hcButton);
}
```

Add Entity
```

```

Add CREATE
```
import java.util.stream.Stream;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

public class Pizzas {
    @Transactional
    void init(@Observes StartupEvent ev) {
        var p1 = Pizza.of("Pizza Margherita", "Via Margherita, 42");
        var p2 = Pizza.of("Pizza Mozzarella", "Via Mozzarella, 333");
        var p3 = Pizza.of("Pizza Napoli", "Via Napoli, 1");
        Stream.of(p1, p2, p3).forEach(p -> p.persist());
    }
}
```

Simple READ
```
var listButton = new Button("List Pizzas", e -> {
    Pizza.listAll()
        .stream()
        .map(p -> new Paragraph(p.toString()))
        .forEach(this::add);
});
add(listButton);
```

better list

pom.xml
```
<repositories>
  <repository>
    <id>Vaadin Directory</id>
    <url>https://maven.vaadin.com/vaadin-addons</url>
 </repository>
```

```
<dependency>
   <groupId>org.vaadin.crudui</groupId>
   <artifactId>crudui</artifactId>
   <version>7.1.0</version>
</dependency>
```