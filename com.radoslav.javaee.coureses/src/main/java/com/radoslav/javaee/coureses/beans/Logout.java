package com.radoslav.javaee.coureses.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.radoslav.javaee.courses.entities.User;

@ManagedBean
@SessionScoped
public class Logout {

  public void logout() throws Exception {
    HttpSession session = getSession();

    if (session != null && session.getAttribute(User.SESSION_ATTRIBUTE) != null) {
      session.invalidate();
    }

    redirectToLoginPage();
  }

  private HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }
  
  private void redirectToLoginPage() throws Exception {
    FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
  }

}
