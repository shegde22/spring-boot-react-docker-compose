#studentRegistration

# Steps:
1. Create a file ./sr-rest/src/main/resources/application.properties and add the following lines to it:

"
spring.datasource.driverClassName=<jdbcDriver>

spring.datasource.url=<jdbcUrl>

spring.datasource.username=<jdbcUrl>

spring.datasource.password=<jdbcpass>

spring.datasource.hikari.minimumPoolSize=1

spring.datasource.hikari.maximumPoolSize=1

spring.datasource.hikari.initialPoolSize=1

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=<org.hibernate.dialect.Oracle12cDialect>

spring.devtools.remote.secret=<thisismysecret>

spring.devtools.livereload.port=<35730>
"
