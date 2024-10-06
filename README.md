# Todo-List-API

## Description
This project is a Spring Boot application primarily focused on user authentication through JWT implementation. It provides a RESTful API for user registration and login, integrating with a MySQL database to securely manage user data, including usernames, emails, and passwords. Additionally, the application facilitates todo management, offering APIs to create, delete, update, and retrieve todos, as well as apply filtering and sorting to efficiently organize tasks.

## Requirements

- **Eclipse IDE**: For developing and managing the Spring Boot application.
- **Postman**: For testing the API endpoints.
- **MySQL Server**: To host the MySQL database.
- **MySQL Workbench**: For database management and visual representation of database schema.
- **Git for Windows**: For version control and managing the project's source code.

## How to Setup Project in your local

**Clone the Git Repository:**
   - Use the following command to clone the repository to your local system:<br>
     *git clone https://github.com/jatinraju/Todo-List-API.git*
     

**Download the Zip File:**
   - Alternatively, you can download the zip file of the repository. After downloading, extract the folder.

**Import the Project into Eclipse IDE:**
   - Open Eclipse IDE.
   - Go to `File` > `Import...`.
   - Select `Existing Maven Projects` under the `Maven` category and click `Next`.
   - Browse to the directory where you extracted the folder or cloned the repository.
   - Click `Finish` to complete the import process.

## How to Run the Spring Boot Application

**Open the Project in Eclipse IDE**:
   - After importing the project, ensure all Maven dependencies are downloaded.

**Configure the Application Properties**:
   - Navigate to `src/main/resources/application.properties`.
   - Update the database connection settings according to your local MySQL configuration.<br> For example:
          *spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name*<br>
     *spring.datasource.username=your_username*<br>
     *spring.datasource.password=your_password*<br>
     
** Install Lombok **     
- Click on `Help` in the top menu.
- Select `Install New Software...` from the dropdown.
- Paste the following URL in the **Work With:** field and press enter:<br>
		*https://projectlombok.org/p2*
- Let it search for Lombok.
- Select Lombok and click on **Next**.
- Accept the Terms and Conditions.
- After installation, restart your Eclipse IDE.

**Run the Application**:
   - Right-click on the project in the Project Explorer.
   - Select `Run As` > `Java Application` or `Spring Boot App`.
   - Alternatively, you can run the application using the terminal:
     *./mvn spring-boot:run*
   - Ensure the application starts without any errors in the console. 
   
## How to Set Up APIs and Environment in Postman

To interact with the APIs provided in this project, follow these steps to set up Postman with the provided files from the Git repository.

**Locate the Postman Files:**<br> 
- Navigate to the cloned repository folder and locate the following files:<br>
*todo-api-collection.json:* This file contains the collection of API endpoints.<br>
*todo-api-environment.json:* This file contains the environment variables needed for API requests.
 
 **Import the Postman Collection**
 - Open Postman: Launch the Postman application on your system.

- Go to the Collections Tab: Click on the "Collections" tab in the left sidebar.

- Click on the "Import" button at the top left of the Collections tab.
- Choose the `File` option, then drag and drop `Todo-List-API.postman_collection.json` into the import window or click "Choose Files" to upload it.<br>
- Click on the `Import` button to complete the process.
 		
**Import the Postman Environment**
- Go to the Environments Tab: Click on the "Environments" tab in the left sidebar.
- Click on the "Import" button at the top left of the Environments tab.
- Choose the `File` option, then drag and drop `Todo-List-API.postman_environment.json` into the import window or click `Choose Files` to upload it.
- Click on the `Import` button to complete the process.

**Set Up Environment Variables**
- In the top right corner of Postman, click on the environment dropdown menu and select the imported environment.
- Ensure the variables defined in `todo-api-environment.json` are set correctly, such as:<br>
		`jwt_token`<br> `refresh_token`<br> 
		*Both values are initially null. When you call the `register` or `login` API, the application will inject the necessary values. This allows subsequent API calls to retrieve these values for authentication and user management.*
		
## How to Set Up Database
- Open the MySQL Workbench.
- Login to `local instance`
- Fill password if asked.
- Open New SQL Script and execute following command: `create database todo_db;`
- To check if the database has been created successfully, look for it on the left-hand side under the **Schemas** section. This will display a list of all available databases, including the one you just created for this project.<br>

 ** Note **
- Make sure your database name matches the one specified in the `application.properties` file.
- Don’t forget to update the username and password for your local MySQL instance in the `application.properties` file.

## Play Around with Endpoints

You can test the following endpoints in your application:

- **Register User**
    - `POST` http://localhost:8080/v1/register
    - Check Request Body before Sending
  
- **Login User**
    - `POST` http://localhost:8080/v1/login
    - Check Request Body before Sending
  
- **Refresh Token**
    - `POST` http://localhost:8080/v1/refresh-token

- **Test Endpoint**
    - `GET` http://localhost:8080/v1/test

- **Public Hello Endpoint**
    - `GET` http://localhost:8080/public/hello

** Todo Endpoints**
  - ** Get All Todos**
    - `GET` http://localhost:8080/v1/todo
  - ** Get Todo by ID**
    - `GET` http://localhost:8080/v1/todo/{todo_id}
    - Replace `{todo_id}` with yout Todo ID.
  - ** Update Todo by ID**
    - `PUT` http://localhost:8080/v1/todo/{todo_id}
    - Replace `{todo_id}` with yout Todo ID.
  - ** Paginated Todos**
    - `GET` http://localhost:8080/v1/todo?page=1&limit=10
  - ** Paginated Todos with Filter**
    - `GET` http://localhost:8080/v1/todo?page=1&limit=10&filter=0
  - ** Paginated Todos with Sorting**
    - `GET` http://localhost:8080/v1/todo?page=1&limit=10&sortDirection=DESC
    - `GET` http://localhost:8080/v1/todo?page=1&limit=10&filter=0&sortDirection=ASC

### 🌟 Explore the Project ###
Dive into my Todo List API project at this `https://roadmap.sh/projects/todo-list-api`. Your support means the world to me, so if you appreciate my solution, I would be thrilled if you could upvote it!

### 🤝 Let’s Connect!  ###
I’d love to connect with you on LinkedIn! `<a href="https://www.linkedin.com/in/jatinraju/">link</a>`

## Thank you for your support! ##
 
 
