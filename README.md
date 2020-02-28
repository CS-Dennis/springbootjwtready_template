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
2. **UserEntity** Class is the class reflecting the table for authentication in your database. As you run the app for the first time, the table named **user_entity** will be automatically created if it doesn't exist in the database.
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
3. **UserDAO** Class is the class that extends CrudRepository interface for SQL queries. You can change **email** in the query to any column name you are searching a user based on.
```
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springbootproject.SpringBootWithSecurity.model.UserEntity;

public interface UserDAO extends CrudRepository<UserEntity, Long>{
	@Query(value = "SELECT * from user_entity WHERE email = ?1", nativeQuery = true)
	public List<UserEntity> findUserByEmail(String email);
}
```
4. **JwtRequest** Class is the class that receive the JSON from the front end. The least two fields you need will be, in this case, email and passwrod. You can change email to other user identification field based on the identity column in your authentication table. You can also add other additional fields (e.g., gender, firstname, lastname, birthday) in this class based on your needs.
```
import java.io.Serializable;

//This class is required for storing the email and password we receive from the client.
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String email, String password, String firstname, String lastname) {
		this.setEmail(email);
		this.setPassword(password);
		this.setFirstname(firstname);
		this.setLastname(lastname);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}
```
5. Last but not least, **WebSecurityConfig** Class contains all the configurations of spring boot security. In this setup, you only need to focus on the the method antMatchers parameters, in this case, **"/authenticate"** and **"/register"**. The RESTController functions linked to these two URLs are not required to be authenticated. You can add more URLs without authentication and defined the functions in the **JwtAuthenticationController** Class.

**WebSecurityConfig.java**
```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate", "/register").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
```
**JwtAuthenticationController.java**
```
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springbootproject.SpringBootWithSecurity.config.JwtTokenUtil;
import com.springbootproject.SpringBootWithSecurity.model.JwtRequest;
import com.springbootproject.SpringBootWithSecurity.model.JwtResponse;
import com.springbootproject.SpringBootWithSecurity.service.JwtUserDetailsService;

//Expose a POST API /authenticate using the JwtAuthenticationController.
//The POST API gets username and password in the body- Using Spring Authentication Manager we authenticate the username and password.
//If the credentials are valid, a JWT token is created using the JWTTokenUtil and provided to the client.

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	@PostMapping(value = "/register")
	public int register(@RequestBody JwtRequest jwtRequest) {
		return userDetailsService.registerUser(jwtRequest);
	}
}
```

Thanks and enjoy!
Dennis
Email: ynyz2003@gmail.com
