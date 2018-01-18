package com.radoslav.javaee.courses.entities;

import java.util.ArrayList;
import java.util.List;

public class Conversation implements InMemoryEntity {

  private String identifier;
  private List<Message> messages;

  public Conversation(String identifier) {
    this.identifier = identifier;
    this.messages = new ArrayList<>();
  }

  @Override
  public EntityType getType() {
    return EntityType.CONVERSATION;
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void addMessage(String sender, String recipient, String message) {
    messages.add(new Message(sender, recipient, message));
  }

  public class Message {
    
    private String sender;
    private String recipient;
    private String message;

    public Message(String messageFrom, String messageTo, String message) {
      this.sender = messageFrom;
      this.recipient = messageTo;
      this.message = message;
    }

    public String getSender() {
      return sender;
    }

    public String getRecipient() {
      return recipient;
    }

    public String getMessage() {
      return message;
    }

  }
}
