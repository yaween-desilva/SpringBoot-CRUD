# SpringBoot-CRUD
A simple RESTful web service that performs basic CRUD (Create, Read, Update, Delete) operations on a resource using Java Spring Boot framework.

You will need the follwoing softwares in order to run the project.
- IDE : Run the SpringBoot project
- XAMPP control panel : Activate the server and start the SQL port
- POSTMAN : To check the API requests

The API endpoints used in this projet are secured with spring security and JWT therefore it cannot be accessed unless the user receive a bearer Token. The following steps will demonstarte how to receive a bearer token and access the secure APis.   

## Step 01
Open the XAMPP control panel and start Apache server and MySQL server.  

![Screenshot (361)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/bdee0c4c-f941-4b8a-a02e-fc418895b9b1)

## Step 02 
Run the project using a preffered IDE and checks whether the server is up and running on the port that you have assigned it to be.

![Screenshot (362)](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/c7c3691b-fe3b-4f55-88d6-98cb34908fcd)

## Step 03
Now we have to use Postman software to check the results when we invoke API endpoints. First we have to set up an environment and add a variable as Token and keep the value null. The use of this environment is to pass the current token value as a varibale therefore we dont need to update token everytime this will automatically updates the latest token to its current value.

![Setting up environment](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/df469a0b-be5e-44cf-ae98-fcb0baf63997)

## Step 04
After that we have to write a script in the token receiving API to pass it to the secured APIs automatically so that we dont need to enter the bearer token each time we get.

![Passing the token to secured APIs](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/e761411f-6f1f-48c1-bb88-60bb3496be6d)

## Step 05
Then we have to pass the reference to all the secured methods which is the final step of setting the automatic token transfering process. We have to select bearer token field from authorization tab to enter this.

![Setting Token to all methods](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/4b31e903-bed6-4dcc-8606-c4bdacf1ae38)

## Step 06
As all the setting up is done now we have to register as a new user by entering a username and password. We have to use POST request and send the credentilas using JSON format. You will receive a message upon successfull registration.

![Registering a User](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/e516b74c-96d2-486a-aaa8-ffa3d9e8dfdc)

## Step 07
Then we have to login to genrate and receive a token. We have to use the registred credentials here and send the details in JSON format as a POST request. So you will receive Token upon successfull login.

![Login in and recieving the Token](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/c963194f-b293-4621-99b0-b0b9e5bc3478)

## Step 08
Finally we have to access the secured API and since we have already set up the token trnasfering process, once we invoke the endpoint it will give the result in status 200.

![Accessing secured API](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/537abbe9-58c0-4234-8a02-da7a10dea461)

### How to access different pages when calling getProducts method
Use the getproduct url and additionally add the following line to ot and u can change the number in the page to get the page to view the page you want and page number starts with 0 which means to access page 1 you should put 0 infront of page in the URL.

![Accessing Elements by pagination](https://github.com/yaween-desilva/SpringBoot-CRUD/assets/172358358/f3146def-9daf-4504-83e0-1d8cb8417205)

## Note
- You do not need to setup environment and use scripts mandatorily to get the expected output. I did mention it to make the process easy
- I have used a base url which i set it up as a variable in the environment so we can use that instead of typing the long url. This is also not mandatory.








