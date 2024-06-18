# SpringBoot-CRUD
A simple RESTful web service that performs basic CRUD (Create, Read, Update, Delete) operations on a resource using Java Spring Boot framework.

**How to Use**: 
You will need the follwoing softwares in order to run the project.
  IDE - Run the SpringBoot project
  XAMPP control panel - Activate the server and start the SQL port
  POSTMAN - To check the API requests

The API endpoints used in this projet are secured with spring security and JWT therefore it cannot be accessed unless the user receive a bearer Token. The following steps will demonstarte how to receive a bearer token and access the secure APis.

Step 01: Open the XAMPP control panel and start Apache server and MySQL server.

Step 02: Run the project using a preffered IDE and checks whether the server is up and running on the port that you have assigned it to be.

Step 03: Then you have to access the registration API endpoint using POSTMAN software tool and the link is "http://localhost:8090/api/auth/register". Then you have to enter a username and password for the registration and it has to be in JSON format. After sending the request through POST method you will receive a message indicating whether the registration was succesfull or not.

Step 04: Now you have to access the token API endpoint endpoint using POSTMAN software tool and the link is "http://localhost:8090/api/auth/token". Then you have to go to the authorization tab and select Basic Auth. After that you can enter the username and passoword that you have entered in the registration process and send the request using POST method. Then you will receive a Token and that is the bearer token that can be used access the secured APIs.

Step 05: Now you can select any preffered endpoint in your controller which is secured and go to the Authorization tab and select "Bearer Token" and enter the token you received in the previous step. Now you can access the API endpoints that were secured early.

Note : You can chnage the database connection by accessing the application properties in the project and connect it to your own database.

