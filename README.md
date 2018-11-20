#studentRegistration

# Steps:
1. Add application properties with following properties:
# ===============================
# = DATA SOURCE
# ===============================
# Set here configurations for the database connection
spring.datasource.driverClassName=<jdbcDriver>
spring.datasource.url=<jdbcUrl>
spring.datasource.username=<jdbcUrl>
spring.datasource.password=<jdbcpass>
spring.datasource.hikari.minimumPoolSize=1
spring.datasource.hikari.maximumPoolSize=1
spring.datasource.hikari.initialPoolSize=1
# ===============================
# = JPA / HIBERNATE
# ===============================
# Show or not log for each sql query
spring.jpa.show-sql=true
# Allows Hibernate to generate SQL optimized for a particular DBMS
spring.jpa.properties.hibernate.dialect=<org.hibernate.dialect.Oracle12cDialect>
spring.devtools.remote.secret=<thisismysecret>
spring.devtools.livereload.port=<35730>
