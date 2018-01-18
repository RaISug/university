package com.radoslav.javaee.coureses.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CaptureEvent;

import com.radoslav.javaee.courses.db.accessors.InMemoryAccessor;
import com.radoslav.javaee.courses.db.accessors.PersistenceAccessor;
import com.radoslav.javaee.courses.entities.Image;
import com.radoslav.javaee.courses.entities.User;

@ManagedBean
@RequestScoped
public class Photo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String fileName;
  private PersistenceAccessor accessor;

  @PostConstruct
  public void initialize() {
    accessor = new InMemoryAccessor();
  }

  public String getFileName() {
    return fileName;
  }

  public void saveImage(CaptureEvent event) {
    fileName = generateUniqueFileName();

    try {
      byte[] imageContent = event.getData();
      if (imageContent == null) {
        System.out.println("Image data was not found.");

        return;
      }

      accessor.insert(new Image(fileName, getCurrentlyLoggedInUser(), imageContent));
    } catch (Exception exception){
      System.out.println("An unexpected exception occurred. Exception: ");
      exception.printStackTrace(System.err);
    }
  }

  private String generateUniqueFileName() {
    long currentTimeInMillis = System.currentTimeMillis();

    return currentTimeInMillis + ".png";
  }

  private String getCurrentlyLoggedInUser() {
    HttpSession session = getSession();

    return (String) session.getAttribute(User.SESSION_ATTRIBUTE);
  }

  private HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }
}
