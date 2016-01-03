package com.moutain.microclimate.service.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name="users")
@NamedQueries({
  @NamedQuery(name="findAll", query="SELECT u FROM User u"),
  @NamedQuery(name="findUserByEmailAndPassword", query="SELECT u FROM User u WHERE u.email=:email AND u.password=:password"),
  @NamedQuery(name="findUserById", query="SELECT u FROM User u WHERE u.id = :id")
})
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private long id;

  private String email;

  private String firstname;

  private String lastname;

  private String password;

  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(
      joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
      inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id")
  )
  private Set<Role> roles;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
  public static User findUserById(EntityManager entityManager, int userId) {
    Query query = entityManager.createNamedQuery("findUserById")
        .setParameter("id", userId);
    
    return (User) query.getSingleResult();
  }
  
  public static User findUserByEmailAndPassword(EntityManager entityManager, String email, String password) {
    Query query = entityManager
        .createNamedQuery("findUserByEmailAndPassword")
        .setParameter("email", email)
        .setParameter("password", password);
    
    return (User) query.getSingleResult();
  }

  public static void persisteEntity(EntityManager entityManager, User user) {
    entityManager.persist(user);
  }
  
}
