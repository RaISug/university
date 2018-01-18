package com.radoslav.javaee.coureses.beans;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.radoslav.javaee.courses.entities.Image;
import com.radoslav.javaee.courses.db.accessors.InMemoryAccessor;
import com.radoslav.javaee.courses.db.accessors.PersistenceAccessor;
import com.radoslav.javaee.courses.entities.EntityType;
import com.radoslav.javaee.courses.entities.InMemoryEntity;
import com.radoslav.javaee.courses.entities.PersistentEntity;
import com.radoslav.javaee.courses.entities.User;

@ManagedBean
@SessionScoped
public class ImageList {

  private String filename;
  private PersistenceAccessor accessor;

  public String getFilename() {
    return filename;
  }

  public String showImage() {
    return "/image.xhtml";
  }

  @PostConstruct
  public void initialize() {
    accessor = new InMemoryAccessor();
  }

  public List<String> getImageNames() {
    String userName = getUsername();

    Set<? extends PersistentEntity> entities = accessor.selectAll(EntityType.IMAGE);

    return entities
      .stream()
      .filter((entity) -> ((Image) entity).getOwner().equals(userName))
      .map((entity) -> ((InMemoryEntity) entity).getIdentifier())
      .collect(Collectors.toList());
  }

  private String getUsername() {
    String userName = getUserNameFromQueryParam();
    if (userName == null || userName.isEmpty() || userName.equals("null")) {
      return getCurrentlyLoggedInUser();
    }

    return userName;
  }

  private String getUserNameFromQueryParam() {
    return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("userName");
  }

  private String getCurrentlyLoggedInUser() {
    HttpSession session = getSession();

    return (String) session.getAttribute(User.SESSION_ATTRIBUTE);
  }

  private HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }
}
