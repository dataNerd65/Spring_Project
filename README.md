# Learning Spring Boot - Task Management API

A beginner-friendly Spring Boot project demonstrating core concepts of building a REST API with Spring Framework. This project implements a simple Task Management system with full CRUD (Create, Read, Update, Delete) operations.

<img width="327" height="192" alt="image" src="https://github.com/user-attachments/assets/ca57d300-4186-4fd2-b6e4-652862dd4be4" />

## 📚 What You'll Learn

This project demonstrates the following Spring Boot concepts:

### 1. **Spring Boot Basics**
- `@SpringBootApplication`: The main annotation that combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`
- Auto-configuration: Spring Boot automatically configures your application based on dependencies
- Embedded server (Tomcat): No need to deploy to external servers

### 2. **REST API Development**
- `@RestController`: Marks a class as a REST controller (combines `@Controller` + `@ResponseBody`)
- `@RequestMapping`: Maps HTTP requests to handler methods
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Shortcuts for specific HTTP methods
- `@PathVariable`: Extracts values from the URI path
- `@RequestBody`: Binds HTTP request body to method parameters
- `@RequestParam`: Extracts query parameters from the URL

### 3. **Dependency Injection (DI)**
- Constructor injection in `TaskController` (recommended approach)
- Spring automatically creates and injects the `TaskRepository` instance
- Promotes loose coupling and testability

### 4. **Spring Data JPA**
- `@Entity`: Marks a class as a JPA entity (database table)
- `@Id` and `@GeneratedValue`: Define primary key and auto-generation strategy
- `JpaRepository<T, ID>`: Provides built-in CRUD methods without writing SQL
- Automatic query generation from method names

### 5. **Database Integration**
- H2 in-memory database (perfect for learning and testing)
- Automatic schema creation from entities
- No manual SQL or table creation needed

### 6. **Cross-Origin Resource Sharing (CORS)**
- `@CrossOrigin`: Allows frontend applications to access the API from different domains

## 🏗️ Project Structure

```
src/main/java/com/example/heyWorld/
├── HeyWorldApplication.java          # Main application entry point
├── controller/
│   └── TaskController.java           # REST endpoints (API layer)
├── model/
│   └── Task.java                     # Entity/Data model (represents database table)
└── repository/
    └── TaskRepository.java           # Data access layer (talks to database)
```

### Architecture Pattern: MVC (Model-View-Controller)
- **Model** (`Task.java`): Represents the data structure
- **Repository** (`TaskRepository.java`): Handles database operations
- **Controller** (`TaskController.java`): Handles HTTP requests and responses

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven (included via Maven Wrapper)

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd Spring_Project
   ```

2. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   On Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

3. **The application will start on** `http://localhost:8080`

### Testing the Endpoints

#### 1. Hello World Endpoint
```bash
curl http://localhost:8080/hey
# Response: Hello World!

curl http://localhost:8080/hey?name=Spring
# Response: Hello Spring!
```

#### 2. Get All Tasks
```bash
curl http://localhost:8080/tasks
# Response: [] (empty initially)
```

#### 3. Create a New Task
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"description":"Learn Spring Boot"}'
```

#### 4. Get All Tasks (after creating)
```bash
curl http://localhost:8080/tasks
# Response: [{"id":1,"description":"Learn Spring Boot","completed":false}]
```

#### 5. Mark Task as Completed
```bash
curl -X PUT http://localhost:8080/tasks/1
# Response: {"id":1,"description":"Learn Spring Boot","completed":true}
```

#### 6. Delete a Task
```bash
curl -X DELETE http://localhost:8080/tasks/1
```

## 📖 Code Walkthrough

### 1. Main Application (`HeyWorldApplication.java`)
```java
@SpringBootApplication  // Enables auto-configuration and component scanning
@RestController         // Makes this class a REST controller
public class HeyWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeyWorldApplication.class, args);  // Starts the application
    }
    
    @GetMapping("/hey")  // Maps GET requests to /hey
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name){
        return String.format("Hello %s!", name);
    }
}
```
**Key concepts**: Entry point, embedded server, simple REST endpoint

### 2. Entity/Model (`Task.java`)
```java
@Entity  // Tells JPA this is a database table
public class Task {
    @Id  // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment
    private long id;
    
    private String description;
    private boolean completed = false;
    
    // Constructors, getters, and setters...
}
```
**Key concepts**: Object-Relational Mapping (ORM), entities map to database tables

### 3. Repository (`TaskRepository.java`)
```java
public interface TaskRepository extends JpaRepository<Task, Long> {
    // No code needed! Spring Data JPA provides:
    // - findAll()
    // - findById(Long id)
    // - save(Task task)
    // - deleteById(Long id)
    // And many more...
}
```
**Key concepts**: Spring Data JPA magic - no SQL needed, just declare an interface!

### 4. Controller (`TaskController.java`)
```java
@RestController
@RequestMapping("/tasks")  // Base path for all endpoints in this controller
@CrossOrigin  // Allows requests from different origins (for frontend apps)
public class TaskController {
    private final TaskRepository repository;
    
    // Constructor injection (Spring automatically injects TaskRepository)
    public TaskController(TaskRepository repository){
        this.repository = repository;
    }
    
    @GetMapping  // GET /tasks
    public List<Task> getAllTasks(){
        return repository.findAll();
    }
    
    @PostMapping  // POST /tasks
    public Task addTask(@RequestBody Task task){
        return repository.save(task);
    }
    
    @PutMapping("/{id}")  // PUT /tasks/{id}
    public Task updateTask(@PathVariable Long id){
        Task task = repository.findById(id).orElseThrow();
        task.setCompleted(true);
        return repository.save(task);
    }
    
    @DeleteMapping("/{id}")  // DELETE /tasks/{id}
    public void deleteTask(@PathVariable Long id){
        repository.deleteById(id);
    }
}
```
**Key concepts**: RESTful endpoints, dependency injection, CRUD operations

## 🔧 Technologies Used

- **Spring Boot 3.5.7**: Framework for building Java applications
- **Spring Web**: For building REST APIs
- **Spring Data JPA**: For database access with JPA
- **H2 Database**: In-memory database (data is lost when app stops)
- **Maven**: Build and dependency management tool
- **Java 17**: Programming language

## 🎯 Learning Exercises

Try these exercises to deepen your understanding:

1. **Add a new field**: Add a `dueDate` field to the Task entity
2. **Create custom query**: Add a method in `TaskRepository` to find completed tasks: `List<Task> findByCompleted(boolean completed);`
3. **Add validation**: Use `@NotBlank` and `@Valid` to validate task description
4. **Add pagination**: Modify `getAllTasks()` to return paginated results using `Pageable`
5. **Add a Service layer**: Create a `TaskService` class between Controller and Repository
6. **Switch database**: Replace H2 with PostgreSQL or MySQL
7. **Add error handling**: Create a `@ControllerAdvice` class for global exception handling
8. **Add unit tests**: Write tests for your controller using MockMvc

## 📚 Additional Resources

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-tutorial)

## 🐛 Troubleshooting

**Port 8080 already in use?**
```bash
# Add this to src/main/resources/application.properties
server.port=8081
```

**Can't see database data?**
H2 is in-memory, so data is lost when the app stops. To persist data, switch to a file-based database or use PostgreSQL/MySQL.

**Want to see the H2 console?**
Add to `application.properties`:
```properties
spring.h2.console.enabled=true
```
Then visit: `http://localhost:8080/h2-console`

## 📝 Notes

- This project uses an **in-memory H2 database**, which means all data is lost when you stop the application
- The application runs on port **8080** by default
- Spring Boot automatically creates database tables based on your entities
- No XML configuration needed - Spring Boot uses Java annotations and auto-configuration

## 🎓 Key Takeaways

1. **Convention over Configuration**: Spring Boot makes intelligent defaults so you write less configuration
2. **Dependency Injection**: Spring manages object creation and dependencies for you
3. **Annotations are powerful**: They tell Spring how to handle your classes and methods
4. **REST principles**: Use appropriate HTTP methods (GET, POST, PUT, DELETE) for different operations
5. **Layered architecture**: Separate concerns (Controller → Service → Repository → Database)

Happy Learning! 🚀
