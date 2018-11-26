#studentRegistration

# Steps:
1. Install docker-compose: https://docs.docker.com/compose/install/#prerequisites 
2. Create a file ./sr-rest/src/main/resources/application.properties and add the following lines to it:
  spring.datasource.driverClassName=oracle.jdbc.driver.OracleDriver    
  spring.datasource.url=jdbcUrl  
  spring.datasource.username=jdbcUser    
  spring.datasource.password=jdbcpass  
  spring.datasource.hikari.minimumPoolSize=1  
  spring.datasource.hikari.maximumPoolSize=1  
  spring.datasource.hikari.initialPoolSize=1  
  spring.jpa.show-sql=true  
  spring.jpa.properties.hibernate.dialect=<org.hibernate.dialect.Oracle12cDialect>  
  spring.devtools.remote.secret=thisismysecret  
  spring.devtools.livereload.port=35730    

3. Setup Proxy connection via ssl.binghamton.edu (Pulse secure)  
4. Run command
  > docker-compose up

5. Access the server at localhost:3000
