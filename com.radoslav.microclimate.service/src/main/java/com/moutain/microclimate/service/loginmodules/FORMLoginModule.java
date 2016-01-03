package com.moutain.microclimate.service.loginmodules;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.moutain.microclimate.service.dbaccessors.DatabaseConnector;
import com.moutain.microclimate.service.dbaccessors.UserPersistenceHelper;
import com.moutain.microclimate.service.entities.Role;
import com.moutain.microclimate.service.entities.User;
import com.moutain.microclimate.service.exceptions.MicroclimateException;
import com.moutain.microclimate.service.principals.RolePrincipal;
import com.moutain.microclimate.service.principals.UserPrincipal;

public class FORMLoginModule implements LoginModule {

  private User user = null;
  private Subject subject = null;
  private CallbackHandler callbackHandler = null;

  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
  }

  public boolean login() throws LoginException {
    Callback[] callbacks = new Callback[] {
          new NameCallback("username"),
          new PasswordCallback("password", false)
    };
    
    try {
      callbackHandler.handle(callbacks);

      String email = ((NameCallback) callbacks[0]).getName();
      String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

      EntityManager entityManager = DatabaseConnector.getEntityManager();
      UserPersistenceHelper persistenceHelper = new UserPersistenceHelper(entityManager);
      user = persistenceHelper.getUserByEmailAndPassword(email, password);
    } catch (IOException e) {
      throw new LoginException(e.getMessage());
    } catch (UnsupportedCallbackException e) {
      throw new LoginException(e.getMessage());
    } catch (MicroclimateException e) {
      throw new LoginException(e.getMessage());
    }
    
    return true;
  }

  public boolean commit() throws LoginException {
    if (user == null) {
      return false;
    }

    String username = user.getFirstname() + " " + user.getLastname();

    Principal userPrincipal = new UserPrincipal(username);
    subject.getPrincipals().add(userPrincipal);
    
    for (Role role : user.getRoles()) {
      Principal rolePrincipal = new RolePrincipal(role.getRoleName());
      subject.getPrincipals().add(rolePrincipal);
    }

    return true;
  }

  public boolean abort() throws LoginException {
    return false;
  }

  public boolean logout() throws LoginException {
    subject.getPrincipals().clear();
    return true;
  }

}
