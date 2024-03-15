
Spring Boot Blog Application.

## Setup Steps.

**1. Clone the application**

    git clone <repository_url>


**2. Set Up database**

Create empty postgres database called 'blog'
Database username and password you're using should be matched in the application.properties

    (NB: For this application, below credentials have been specified, but can be modified)
    username=postgres
    password=queen


**3. Change the database credentials as per your installation**

open `src/main/resources/application.properties`
change `spring.datasource.username` and `spring.datasource.password` as per your database installation


**4. Run the application

    open the project using intellij idea
    build and run the project

    The app will start running at <http://localhost:8080>



**5. Explore Rest APIs

Access the endpoints via localhost swagger url: http://localhost:8080/swagger-ui/index.html

Access the postman test endpoints at /project/API_Documentation/


**6. Running the Endpoints
    -Open Postman and import the Postman Json file above.
    -Run the 'signup' endpoint with desired 'email' and 'password' to Register a user.
    -Note: The Administrator will be created by default when the project runs. You can access 
        the Administrator details in the database, tbl_user
            
            Administrator credentials:
            email: admin@gmail.com
            password: admin

**7. Signin
    -Use the Administrator credentials above or the created user credentials to login using the 'signin' endpoint.


**8. Run other Endpoints
    -Copy the token generated from 'signin' response.
    -Open any other endpoint and use this token on the Authorization section. Select Bearer Token and paste.
    -Run the endpoint.