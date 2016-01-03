package com.radoslav.microclimate.service.rest;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.radoslav.microclimate.service.annotations.EntityManagerBinding;
import com.radoslav.microclimate.service.dbaccessors.UserPersistenceHelper;
import com.radoslav.microclimate.service.helpers.ValidationUtil;

@Path("users")
@EntityManagerBinding
public class UserManagementAPI {

  private static final Logger logger = LoggerFactory.getLogger(UserManagementAPI.class);
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(@Context EntityManager entityManager, UserBean user) throws Exception {
    UserPersistenceHelper persistenceHelper = new UserPersistenceHelper(entityManager);
    
    logger.debug("User registration with the following properties: [{}] will be proccessed.", user);
    
    String email = user.getEmail();
    String password = user.getPassword();
    String firstName = user.getFirstName();
    String lastName = user.getLastName();
    
    ValidationUtil.validateEmail(email);
    ValidationUtil.validatePassword(password);
    ValidationUtil.validateThatParamIsNotEmpty(firstName, "First name");
    ValidationUtil.validateThatParamIsNotEmpty(lastName, "Last name");
    
    logger.debug("Start user registration.");
    
    persistenceHelper.registerUser(email, password, firstName, lastName);
    
    logger.debug("User was successfully registrated.");
    
    return Response.status(Status.CREATED).build();
  }
  
  public class UserBean {
    
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    
    public String getEmail() {
      return email;
    }
    
    public String getPassword() {
      return password;
    }
    
    public String getFirstName() {
      return firstName;
    }
    
    public String getLastName() {
      return lastName;
    }
    
    @Override
    public String toString() {
      return "User=[email=" + email + ", first name=" + firstName + ", last name=" + lastName +"]";
    }
  }
}
