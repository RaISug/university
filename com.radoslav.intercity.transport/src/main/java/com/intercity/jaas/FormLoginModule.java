package com.intercity.jaas;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.intercity.database.accessor.UserAccessor;
import com.intercity.database.entity.User;
import com.intercity.exception.ClientCredentialException;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.jaas.principal.RolePrincipal;
import com.intercity.jaas.principal.UserPrincipal;

public class FormLoginModule implements LoginModule{
  
  private User user = null;
  private Subject subject = null;
  private CallbackHandler callbackHandler = null;
    
  public void initialize(Subject subject, CallbackHandler callbackHandler,
      Map<String, ?> sharedState, Map<String, ?> options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
  }

  public boolean login() throws LoginException {
    Callback[] callbacks = new Callback[] { new NameCallback("username"), new PasswordCallback("password", false) };
    try {
      callbackHandler.handle(callbacks);
      
      String email = ((NameCallback) callbacks[0]).getName();
      String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());
      
      user = new UserAccessor(email, password).validateCredentials();
    } catch (IOException e) {
      throw new LoginException(e.getMessage());
    } catch (UnsupportedCallbackException e) {
      throw new LoginException(e.getMessage());
    } catch (ClientCredentialException e) {
      throw new LoginException(e.getMessage());
    } catch (DatabaseConnectionException e) {
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
    Principal rolePrincipal = new RolePrincipal(user.getType());
    
    subject.getPrincipals().add(userPrincipal);
    subject.getPrincipals().add(rolePrincipal);
    
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
