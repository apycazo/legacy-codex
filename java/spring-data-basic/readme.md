# Sample for Spring-Data

## HikariCP

HikariCP will be used by spring-data if found on classpath.

## Typical properties

```
# Only for mysql style values
spring.datasource.url: jdbc:mysql://localhost/test
# Only if DB is password protected
spring.datasource.username: dbuser
spring.datasource.password: dbpass
# Inferred by spring data
spring.datasource.driver-class-name: com.mysql.jdbc.Driver
```

### H2 properties

H2 uses the following property value if used as a file-based persistence

```spring.datasource.url: jdbc:h2:file:./h2db```

### Database restart on init

Use 'create-drop' to reset the DB on restart:

```spring.jpa.hibernate.ddl-auto: create-drop```

Otherwise, use one of these (or none):

* __validate__: validate the schema, makes no changes to the database.
* __update__: update the schema.
* __create__: creates the schema, destroying previous data.
* __create-drop__: drop the schema at the end of the session

## Outcome on correct start

```
2016-07-08 22:24:19.578  INFO 3174 --- [           main] c.github.apycazo.sample.springdata.App   : Starting App on fedora23 with PID 3174 (/home/manager/IdeaProjects/com.github.apycazo/sample-spring-data/target/classes started by manager in /home/manager/IdeaProjects/com.github.apycazo/sample-spring-data)
2016-07-08 22:24:19.579  INFO 3174 --- [           main] c.github.apycazo.sample.springdata.App   : No active profile set, falling back to default profiles: default
2016-07-08 22:24:19.613  INFO 3174 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@6dab91f1: startup date [Fri Jul 08 22:24:19 CEST 2016]; root of context hierarchy
2016-07-08 22:24:20.334  INFO 3174 --- [           main] j.LocalContainerEntityManagerFactoryBean : Building JPA container EntityManagerFactory for persistence unit 'default'
2016-07-08 22:24:20.348  INFO 3174 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [
	name: default
	...]
2016-07-08 22:24:20.384  INFO 3174 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate Core {4.3.11.Final}
2016-07-08 22:24:20.385  INFO 3174 --- [           main] org.hibernate.cfg.Environment            : HHH000206: hibernate.properties not found
2016-07-08 22:24:20.386  INFO 3174 --- [           main] org.hibernate.cfg.Environment            : HHH000021: Bytecode provider name : javassist
2016-07-08 22:24:20.602  INFO 3174 --- [           main] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {4.0.5.Final}
2016-07-08 22:24:20.758  INFO 3174 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
2016-07-08 22:24:20.825  INFO 3174 --- [           main] o.h.h.i.ast.ASTQueryTranslatorFactory    : HHH000397: Using ASTQueryTranslatorFactory
2016-07-08 22:24:20.967  INFO 3174 --- [           main] org.hibernate.tool.hbm2ddl.SchemaExport  : HHH000227: Running hbm2ddl schema export
2016-07-08 22:24:20.971  INFO 3174 --- [           main] org.hibernate.tool.hbm2ddl.SchemaExport  : HHH000230: Schema export complete
2016-07-08 22:24:21.130  INFO 3174 --- [           main] c.g.a.s.springdata.components.TestBench  : Running test
2016-07-08 22:24:21.187  INFO 3174 --- [           main] c.g.a.s.springdata.components.TestBench  : Persisted : 'Record(id=10, name=test, updates=0)'
2016-07-08 22:24:21.198  INFO 3174 --- [           main] c.g.a.s.springdata.components.TestBench  : Persisted (final): 'Record(id=10, name=test-2, updates=3)'
2016-07-08 22:24:21.319  INFO 3174 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2016-07-08 22:24:21.325  INFO 3174 --- [           main] c.github.apycazo.sample.springdata.App   : Started App in 2.043 seconds (JVM running for 4.588)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 3.965s
[INFO] Finished at: Fri Jul 08 22:24:21 CEST 2016
[INFO] Final Memory: 37M/318M
[INFO] ------------------------------------------------------------------------
2016-07-08 22:24:21.438  INFO 3174 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@6dab91f1: startup date [Fri Jul 08 22:24:19 CEST 2016]; root of context hierarchy
2016-07-08 22:24:21.443  INFO 3174 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
2016-07-08 22:24:21.443  INFO 3174 --- [       Thread-2] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2016-07-08 22:24:21.443  INFO 3174 --- [       Thread-2] org.hibernate.tool.hbm2ddl.SchemaExport  : HHH000227: Running hbm2ddl schema export
2016-07-08 22:24:21.448  INFO 3174 --- [       Thread-2] org.hibernate.tool.hbm2ddl.SchemaExport  : HHH000230: Schema export complete

Process finished with exit code 0
```