package com.radoslav.javaee.coureses.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.radoslav.javaee.courses.db.accessors.InMemoryAccessor;
import com.radoslav.javaee.courses.entities.EntityType;
import com.radoslav.javaee.courses.entities.User;

@ManagedBean
@SessionScoped
public class Login {

  private String userName;
  private InMemoryAccessor accessor;

  @PostConstruct
  public void onCreate() {
    accessor = new InMemoryAccessor();
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void checkForActiveSession() throws Exception {
    if (isUserLoggedIn() == false && isLoginPage() == false) {
      redirectToLoginPage();
    } else if (isUserLoggedIn() && isLoginPage() && isGetRequest()){
      redirectToHomePage();
    }
  }

  private boolean isUserLoggedIn() {
    HttpSession session = getSession();

    return session != null && session.getAttribute(User.SESSION_ATTRIBUTE) != null;
  }
  
  private boolean isLoginPage() {
    String currentPage = getCurrentPage();

    return currentPage != null && currentPage.endsWith("login.xhtml");
  }

  private String getCurrentPage() {
    HttpServletRequest request = getServletRequest();

    return request.getPathInfo();
  }

  private boolean isGetRequest() {
    HttpServletRequest request = getServletRequest();

    return "GET".equals(request.getMethod());
  }
  private void redirectToHomePage() throws Exception {
    redirectTo("index.xhtml");
  }
  
  private void redirectToLoginPage() throws Exception {
    redirectTo("login.xhtml");
  }

  private void redirectTo(String page) throws Exception {
    HttpServletResponse response = getServletResponse();

    response.sendRedirect(page);

    commitResponse();
  }

  private void commitResponse() {
    FacesContext.getCurrentInstance().responseComplete();
  }

  private HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }

  private HttpServletRequest getServletRequest() {
    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }

  private HttpServletResponse getServletResponse() {
    return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
  }

  public void login() throws Exception {
    if (userExists() == false) {
      createUser();
    }

    putUserIntoSession();
    redirectToHomePage();
  }

  private boolean userExists() {
    User user = (User) accessor.select(EntityType.USER, userName);

    return user != null;
  }

  private void createUser() {
    accessor.insert(new User(userName));
  }
  
  private void putUserIntoSession() {
    HttpSession session = createSession();

    if (userName != null) { 
      session.setAttribute(User.SESSION_ATTRIBUTE, userName);
    }
  }

  private HttpSession createSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
  }
}
