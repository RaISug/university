package com.radoslav.javaee.coureses.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.radoslav.javaee.courses.db.accessors.InMemoryAccessor;
import com.radoslav.javaee.courses.db.accessors.PersistenceAccessor;
import com.radoslav.javaee.courses.entities.Conversation;
import com.radoslav.javaee.courses.entities.Conversation.Message;
import com.radoslav.javaee.courses.entities.EntityType;
import com.radoslav.javaee.courses.entities.User;

@ManagedBean
@RequestScoped
public class Chat {

  private static final String IDENTIFIER_TEMPLATE = "%s-%s";

  private String userName;
  private String newMessage;
  private String responseMessage;
  private PersistenceAccessor accessor;

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

  public String getNewMessage() {
    return newMessage;
  }
  
  public void setNewMessage(String newMessage) {
    this.newMessage = newMessage;
  }

  public void setResponseMessage(String message) {
    this.responseMessage = message;
  }

  public String getResponseMessage() {
    return responseMessage;
  }

  public void sendMessage() throws Exception {
    String sender = getSender();
    String recipient = getRecipient();

    Conversation entity = getOrCreateConversationBetween(sender, recipient);
    entity.addMessage(sender, recipient, newMessage);

    accessor.insert(entity);
  }

  private Conversation getOrCreateConversationBetween(String sender, String recipient) {
    Conversation entity = retrieveConversation(sender, recipient);
    if (entity != null) {
      return entity;
    }

    return new Conversation(String.format(IDENTIFIER_TEMPLATE, sender, recipient));
  }

  private Conversation retrieveConversation(String sender, String recipient) {
    String identifier = String.format(IDENTIFIER_TEMPLATE, recipient, sender);

    Conversation entity = (Conversation) accessor.select(EntityType.CONVERSATION, identifier);
    if (entity != null) {
      return entity;
    }

    identifier = String.format(IDENTIFIER_TEMPLATE, sender, recipient);

    return (Conversation) accessor.select(EntityType.CONVERSATION, identifier);
  }

  public void retrieveMessages() throws Exception {
    String sender = getSender();
    String recipient = getRecipient();

    Conversation entity = retrieveConversation(sender, recipient);

    responseMessage = buildConversationAsHTML(entity.getMessages(), sender);
  }

  private String getRecipient() {
    return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("userName");
  }

  private String getSender() throws Exception {
    HttpSession session = getSession();

    return (String) session.getAttribute(User.SESSION_ATTRIBUTE);
  }

  private HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }

  private String buildConversationAsHTML(List<Message> messages, String currentUser) {
    StringBuilder builder = new StringBuilder("<div style='width: 100%'>");

    for (Message message : messages) {
      if (message.getSender().equals(currentUser)) {
        builder.append("<div style='min-width: 75%; float: right; text-align: right;'><b>Аз:</b></div>");

        builder.append("<div class='senderMessage'>");
        builder.append(message.getMessage());
        builder.append("</div>");
      } else {
        builder.append("<div style='min-width: 75%; float: left; text-align: left;'><b>").append(message.getSender()).append(":</b></div>");

        builder.append("<div class='recipientMessage'>");
        builder.append(message.getMessage());
        builder.append("</div>");
      }
    }

    builder.append("</div>");

    return builder.toString();
  }

  public String constructImageListUrl() {
    return "imagelist.xhtml?faces-redirect=true&userName=" + getRecipient();
  }
}
