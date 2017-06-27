package com.radoslav.javaee.coureses.beans;

import java.io.ByteArrayInputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.radoslav.javaee.courses.db.accessors.InMemoryAccessor;
import com.radoslav.javaee.courses.entities.EntityType;
import com.radoslav.javaee.courses.entities.PersistentEntity;

@ManagedBean
@RequestScoped
public class Image {

  private static String fName;
  
  private String fileName;
  private InMemoryAccessor accessor;

  @PostConstruct
  public void onCreate() {
    if (fileName == null) {
      fileName = getFilenameQueryParam();
    }

    accessor = new InMemoryAccessor();
  }

  public StreamedContent getStreamedContent() {
    String fileName = getFileName();

    PersistentEntity entity = accessor.select(EntityType.IMAGE, fileName);
    if (entity == null) {
      return new DefaultStreamedContent();
    }

    return new DefaultStreamedContent(new ByteArrayInputStream(((com.radoslav.javaee.courses.entities.Image) entity).getContent()), "image/png");
  }

  private String getFileName() {
    if (fileName == null && fName == null) {
      return null;
    } else if (fileName != null) {
      fName = fileName;
    } else if (fileName == null && fName != null){
      return fName;
    }
    
    return fName;
  }

  private String getFilenameQueryParam() {
    return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filename");
  }
}
