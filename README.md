# WaymoreServer
This is the server side code for Waymore app.

# File structure
- The whole java project is managed with Maven. `pom.xml` is the configuration file specifying the repositories and dependencies needed in this project.
- The core RESTful service is in `src/main/java/com/cloudcomputing/rest/jersey/WaymoreServer.java`
- Resources requested by client side are represented by Java class, including
  `src/main/java/com/cloudcomputing/rest/jersey/Route.java`
  `src/main/java/com/cloudcomputing/rest/jersey/ReturnedRoute.java`
  `src/main/java/com/cloudcomputing/rest/jersey/User.java`
  `src/main/java/com/cloudcomputing/rest/jersey/KeyPoint.java`
  `src/main/java/com/cloudcomputing/rest/jersey/MapPoint.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Comment.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Snippet.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Snippets.java`
  `src/main/java/com/cloudcomputing/rest/jersey/Like.java`
- The database connection management and transactions (insert, update, query, delete etc.) are implemented in `src/main/java/com/cloudcomputing/rest/jersey/DBManager.java`
- SQL scripts for table creation and deletion in MySQL are in `WaymoreServer/Database scripts/`

#How to run
- Right now our server and database are running on the AWS cloud service. The server endpoint is `waymore-env.elasticbeanstalk.com/rest/waymore/`. If you want to deploy the server on a different environment, remember to replace to endpoint address in client side code with the new one.
- If you want to use your own database, please replace to endpoint `conn=DriverManager.getConnection("jdbc:mysql://aa2imaz7ur0qon.cynqzrqdfvg7.us-east-1.rds.amazonaws.com:3306/waymore?user=<Username>&password=<Password>");` in `src/main/java/com/cloudcomputing/rest/jersey/DBManager.java` with new endpoint, port number, username and password.

#Environment
- The development environment: `Java version 1.7.0_75 + Tomcat 8 + JAX-RS + Jersey 1.9 + Maven 3.3.1 + MySQL 14.14`
- Running environment: `AWS ElasticBeanstalk (Tomcat 8) + AWS RDS (MySQL)`
