package com.radoslav.microclimate.service.dbaccessors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.radoslav.microclimate.service.entities.User;
import com.radoslav.microclimate.service.exceptions.InternalServerErrorException;
import com.radoslav.microclimate.service.exceptions.MicroclimateException;
import com.radoslav.microclimate.service.exceptions.UnauthorizedException;
import com.radoslav.microclimate.service.helpers.ValidationUtil;

public class UserPersistenceHelper {

  private EntityManager entityManager = null;
  
  public UserPersistenceHelper(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  
  public User getUserByEmailAndPassword(String email, String password) throws MicroclimateException {
    EntityManager entityManager = null;
    
    try {
      ValidationUtil.validateEmail(email);
      ValidationUtil.validatePassword(password);
      
      return User.findUserByEmailAndPassword(entityManager, email, password);
    }  catch (NoResultException exception) {
      throw new UnauthorizedException("User does not exists.");
    }  catch (NonUniqueResultException exception) {
      throw new InternalServerErrorException("Illegal database content.");
    }
  }
  
  public User getUserById(int userId) throws MicroclimateException {
    try {
      return User.findUserById(entityManager, userId);
    } catch (NoResultException exception) {
      throw new UnauthorizedException("User does not exists.");
    }  catch (NonUniqueResultException exception) {
      throw new InternalServerErrorException("Illegal database content.");
    }
  }
  
  public void registerUser(final String email, final String password, final String firstName, final String lastName) throws Exception {
    TransactionHandler.handleTransaction(entityManager, new CriticalSection<User>() {

      User user = null;
      
      public void executeQuery() {
        user = createUser(email, password, firstName, lastName);
        User.persisteEntity(entityManager, user);
      }
      
      public User getResult() {
        return user;
      }
      
    });
  }
  
  private User createUser(String email, String password, String firstName, String lastName) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    user.setFirstname(firstName);
    user.setLastname(lastName);
    return user;
  }
  
}
