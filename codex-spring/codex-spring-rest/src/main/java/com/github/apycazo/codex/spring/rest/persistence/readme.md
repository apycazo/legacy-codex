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