# rates-service Fabrizio Granieri

#   HOW TO CONFIGURE THE APPLICATION   ###################

# Maven build

- mvn clean install

# deploy WAR file on Tomcat 8

- use port 8080

# database configuration

- create_entropay_db.sql (automatically executed at server startup)

- mvc-dispatcher-servlet.xml (configure schema username and password)

<bean id="dataSource"
	class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	<property name="driverClassName" value="com.mysql.jdbc.Driver" />
	<property name="url" value="jdbc:mysql://localhost:3306/entropay" />
	<property name="username" value="root" />
	<property name="password" value="" />
</bean>

#   HOW TO USE THE APPLICATION   		 ###################

Use the Chrome Rest Client to make the following requests

# Start the Spring Batch job

- GET http://localhost:8080/rates-retrieval-service/batch/start   

# Get all rates

- GET http://localhost:8080/rates-retrieval-service/rest/rates/all

# Get all rates filtered by date

- GET http://localhost:8080/rates-retrieval-service/rest/rates/2016-01-01

# Delete rate by id

- DELETE http://localhost:8080/rates-retrieval-service/rest/rates/1

# Insert a new rate

- POST http://localhost:8080/rates-retrieval-service/rest/rates/save

Body:
Content-Type: application/json

{
  	"file": "rates-2016-01-06.DAT",
	"buyCurrency": "USD",
	"sellCurrency": "GBP",
	"validDate": "2016-01-06",
	"rate": 0.70245
}

# Update a rate

- POST http://localhost:8080/rates-retrieval-service/rest/rates/save

Body:
Content-Type: application/json

{
  	"id": 31,
	"file": "rates-2016-01-06.DAT",
	"buyCurrency": "USD",
	"sellCurrency": "GBP",
	"validDate": "2016-01-06",
	"rate": 0.71174
}


#   JAVA CLASSES   		 ###################



# Rate
Hibernate Entity that maps the DB table

# RatesDao 
DAO class that access database to retrieve, filter by date, insert, update, delete Rates from DB

# RatesService
Spring Service that access the DAO and manage transactions

# RateRestController
Spring RestController annotated class that maps the HTTP requests as explained in above section

# BatchConfiguration
Class with Spring Batch annotations to configure the task and set the Rate Reader, Processor and Writer

















