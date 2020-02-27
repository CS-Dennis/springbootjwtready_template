# springbootjwtready_template

This is a spring boot maven project with jwt already set up ready to go! User authentication database table is customizable.

Knowing setting up JWT for spring boot is not that simple and the setup process can be tedious. For new spring boot developers, learning the set up of JWT in spring boot can be confusing and time consuming; for the experienced spring boot developers, recreating the wheels probabbly is not the most efficient way to build a new web application. This project is an empty spring boot Maven project with JWT set up and ready to go!

## How to start?
Import this folder as a maven project in your IDE. As you run this project, the **default database** will be set up in the **application.properties** file under **resources** folder.
```
spring.jpa.hibernate.ddl-auto=update
# The default schema of database is named springboot, you can change it to your own schema name and make sure your correspoding database has that schema availabe.
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=dennis
spring.datasource.password=security
jwt.secret=myspringsecurityjwt
```
