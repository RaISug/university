package com.radoslav.javaee.coureses.beans;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.radoslav.javaee.courses.db.accessors.InMemoryAccessor;
import com.radoslav.javaee.courses.db.accessors.PersistenceAccessor;
import com.radoslav.javaee.courses.entities.EntityType;
import com.radoslav.javaee.courses.entities.PersistentEntity;
import com.radoslav.javaee.courses.entities.User;

@ManagedBean
@RequestScoped
public class UsersList {

  private PersistenceAccessor accessor;

  @PostConstruct
  public void onCreate() {
    accessor = new InMemoryAccessor();
  }

  public String getConversationLink() throws Exception {
    String userName = getUsername();

    return "/chat.xhtml?faces-redirect=true&userName=" + userName;
  }

  private String getUsername() {
    return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("userName");
  }

  public List<String> getActiveUsers() {
    Set<? extends PersistentEntity> entities = accessor.selectAll(EntityType.USER);

    return entities
        .stream()
        .map((entity) -> ((User) entity).getIdentifier())
        .filter((user) -> !user.equals(getCurrentlyLoggedInUser()))
        .collect(Collectors.toList());
  }

  private String getCurrentlyLoggedInUser() {
    HttpSession session = getSession();

    return (String) session.getAttribute(User.SESSION_ATTRIBUTE);
  }

  private HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }
}
