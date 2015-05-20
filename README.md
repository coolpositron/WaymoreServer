# WaymoreServer
This is the server side code for Waymore app.

# File structure
1. The whole java project is managed with Maven. `pom.xml` is the configuration file specifying the repositories and dependencies needed in this project.
2. The core RESTful service is in `src/main/java/com/cloudcomputing/rest/jersey/WaymoreServer.java`
3. Resources requested by client side are represented by Java class, including
  `src/main/java/com/cloudcomputing/rest/jersey/Route.java`
  `src/main/java/com/cloudcomputing/rest/jersey/ReturnedRoute.java`
  `src/main/java/com/cloudcomputing/rest/jersey/User.java`
  `src/main/java/com/cloudcomputing/rest/jersey/KeyPoint.java`
  `src/main/java/com/cloudcomputing/rest/jersey/MapPoint.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Comment.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Snippet.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Snippets.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Like.java`
4. The database connection management and transactions (insert, update, query, delete etc.) are implemented in `src/main/java/com/cloudcomputing/rest/jersey/DBManager.java`
5. SQL scripts for table creation and deletion in MySQL are in `WaymoreServer/Database scripts/`

#Environment
1. The development environment: `Java version 1.7.0_75 + Tomcat 8 + JAX-RS + Jersey 1.9 + Maven 3.3.1 + MySQL 14.14`
2. Running environment: `AWS ElasticBeanstalk (Tomcat 8) + AWS RDS (MySQL)`
