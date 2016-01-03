package com.intercity.database.accessor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.intercity.database.connection.Connector;
import com.intercity.database.entity.User;
import com.intercity.exception.ClientCredentialException;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

public class UserAccessor {
  
  private String email = null;
  private String password = null;
  
  private static final String EMAIL_PATTERN = 
      "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  
  public UserAccessor() {
    this(null, null);
  }
  
  public UserAccessor(String email) {
    this(email, null);
  }
  
  public UserAccessor(String email, String password) {
    this.email = email;
    this.password = password;
  }
  
  @SuppressWarnings("unchecked")
  public User validateCredentials() throws ClientCredentialException, DatabaseConnectionException {
    EntityManager entityManager = null;
    
    try {
      validateEmail();
      validatePassword();
      
      entityManager = Connector.getEntityManager();
      Query query = entityManager
          .createNamedQuery("User.validateClientCredentials")
          .setParameter("email", email)
          .setParameter("password", password);
      
      List<User> users = query.getResultList();
      
      if (users == null || users.size() == 0) {
        throw new ClientCredentialException("Client doen't exists.", 204);
      } else if (users.size() > 1) {
        throw new ClientCredentialException("Illegal database content.", 500);
      }
      
      return users.get(0);
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  private void validatePassword() throws ClientCredentialException {
    if (password == null) {
      throw new ClientCredentialException("Password field can not be empty.", 400);
    }
    
    if (password.trim().length() <= 5) {
      throw new ClientCredentialException("Password length must be larger or equal to 6 symbols.", 400);
    }
  }
  
  private void validateEmail() throws ClientCredentialException {
    if (email == null) {
      throw new ClientCredentialException("Email field can not be empty.", 400);
    }

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    
    if (matcher.matches() == false) {
      throw new ClientCredentialException("Email field should contain valid email.", 400);
    }
  }
  
  @SuppressWarnings("unchecked")
  public User getUser(int userId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager.createNamedQuery("User.findUser")
          .setParameter("id", userId);
      
      List<User> users = query.getResultList();
      if (users.size() > 1) {
        throw new DatabaseResultException("Database content conflict.", 500);
      } else if (users.size() == 0) {
        throw new DatabaseResultException("No content.", 204);
      }
      return users.get(0);
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  public void registerUser(String firstName, String lastName, String type) throws DatabaseConnectionException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      User user = createUser(firstName, lastName, type);
      
      entityManager.getTransaction().begin();
      entityManager.persist(user);
      entityManager.getTransaction().commit();
    } finally {
      if (entityManager != null) {
        if (entityManager.getTransaction().isActive()) {
          entityManager.getTransaction().rollback();
        }
        entityManager.close();
      }
    }
  }
  
  private User createUser(String firstName, String lastName, String type) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    user.setFirstname(firstName);
    user.setLastname(lastName);
    user.setType(type);
    return user;
  }
  
}
