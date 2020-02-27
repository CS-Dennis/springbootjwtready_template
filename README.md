# springbootjwtready_template

This is a spring boot maven project with jwt already set up ready to go! User authentication database table is customizable.

Knowing setting up JWT for spring boot is not that simple and the setup process can be tedious. For new spring boot developers, learning the set up of JWT in spring boot can be confusing and time consuming; for the experienced spring boot developers, recreating the wheels probabbly is not the most efficient way to build a new web application. This project is an empty spring boot Maven project with JWT set up and ready to go!

## How to start?
1. Import this folder as a maven project in your IDE. As you run this project, the **default database** will be set up in the **application.properties** file under **resources** folder.
```
spring.jpa.hibernate.ddl-auto=update
# The default schema of database is named springboot, you can change it to your own schema name and make sure your correspoding database has that schema availabe.
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=dennis
spring.datasource.password=security
jwt.secret=myspringsecurityjwt
```
2. UserEntity Class is the class reflecting the table for authentication in your database. As you run the app for the first time, the table named user_entity will be automatically created if it doesn't exist in the database.
You can add more columns to the table as needed. In this case, email will be a unique column and be authenticated with the value of password column.
```
@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private LocalDateTime createdDateTime;
	private LocalDateTime modifieDateTime;
	
	public UserEntity() {
		
	}
	
	public UserEntity(String email, String password, String firstname, String lastname) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		
		this.createdDateTime = LocalDateTime.now();
		this.modifieDateTime = LocalDateTime.now();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public LocalDateTime getModifieDateTime() {
		return modifieDateTime;
	}
	public void setModifieDateTime(LocalDateTime modifieDateTime) {
		this.modifieDateTime = modifieDateTime;
	}
}
```
