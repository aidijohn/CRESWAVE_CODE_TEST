
Spring Boot Blog Application.

## Setup Steps.

**1. Clone the application**

    git clone <repository_url>


**2. Set Up database**

Create an empty PostgreSQL database called 'blog'. Ensure that the database username and password 
you're using match the credentials specified in the application.properties file.

    (Note: For this application, the following credentials have been specified but can be modified)
    username=postgres
    password=queen


**3. Change the database credentials as per your installation**

open `src/main/resources/application.properties` and update the
`spring.datasource.username` and `spring.datasource.password` properties according to your database installation.


**4. Run the application

    Open the project using IntelliJ IDEA.
    Build and run the project.

    The app will start running at <http://localhost:8080>



**5. Explore Rest APIs

Access the endpoints via the local Swagger URL: http://localhost:8080/swagger-ui/index.html

Access the Postman test endpoints at /project/API_Documentation/.


**6. Running the Endpoints
    -Open Postman and import the Postman JSON file above.
    -Run the 'signup' endpoint with the desired 'email' and 'password' to Register a user.
    -Note: The Administrator will be created by default when the project runs. You can access 
        the Administrator details in the database, ('tbl_user')
            
            Administrator credentials:
            email: admin@gmail.com
            password: admin

**7. Signin
    -Use the administrator credentials above or the credentials of the created user to log in using the 'signin' endpoint.

**8. Run other Endpoints
    -Copy the token generated from the 'signin' response.
    -Open any other endpoint and use this token in the Authorization section. Select Bearer Token and paste the token.
    -Run the endpoint.