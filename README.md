# SpringBoot-CRUD
A simple RESTful web service that performs basic CRUD (Create, Read, Update, Delete) operations on a resource using Java Spring Boot framework.

**How to Use**: 
You will need the follwoing softwares in order to run the project.
  IDE - Run the SpringBoot project
  XAMPP control panel - Activate the server and start the SQL port
  POSTMAN - To check the API requests

The API endpoints used in this projet are secured with spring security and JWT therefore it cannot be accessed unless the user receive a bearer Token. The following steps will demonstarte how to receive a bearer token and access the secure APis.

##Step 01: Open the XAMPP control panel and start Apache server and MySQL server. <br/>

![Screenshot (361)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/bdee0c4c-f941-4b8a-a02e-fc418895b9b1)

**Step 02:** Run the project using a preffered IDE and checks whether the server is up and running on the port that you have assigned it to be.

![Screenshot (362)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/c7c3691b-fe3b-4f55-88d6-98cb34908fcd)

**Step 03:** Then you have to access the registration API endpoint using POSTMAN software tool and the link is "http://localhost:8090/api/auth/register". Then you have to enter a username and password for the registration and it has to be in JSON format. After sending the request through POST method you will receive a message indicating whether the registration was succesfull or not.

![Screenshot (364)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/1ecc6f78-7118-4e7e-94c9-9eb63e68a12b)

**Step 04:** Now you have to access the token API endpoint endpoint using POSTMAN software tool and the link is "http://localhost:8090/api/auth/token". Then you have to go to the authorization tab and select Basic Auth. After that you can enter the username and passoword that you have entered in the registration process and send the request using POST method. Then you will receive a Token and that is the bearer token that can be used access the secured APIs.

![Screenshot (365)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/791c2e70-21d5-4ab0-bc68-0d86dae7155b)

**Step 05:** Now you can select any preffered endpoint in your controller which is secured and go to the Authorization tab and select "Bearer Token" and enter the token you received in the previous step. Now you can access the API endpoints that were secured early.

![Screenshot (366)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/a18d30ee-3368-4c6f-a9fa-85009b22c96f)

**Note :** You can chnage the database connection by accessing the application properties in the project and connect it to your own database.

![Screenshot (363)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/80fd4189-c34a-4666-bff3-35386642e4d3)


