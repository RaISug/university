package com.radoslav.microclimate.service.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

@Entity
@Table(name="users_to_roles_mapping")
@NamedQueries(
    @NamedQuery(name="findUserRolesByUserId",
          query="SELECT r FROM UserToRoleMapping urm"
              + " JOIN Role r ON r.id = urm.role_id"
              + " JOIN User u ON u.id = urm.user_id"
              + " WHERE u.id = :userId")
)
public class UserToRoleMapping implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private long id;

  @Column(name="user_id")
  private long userId;
  
  @Column(name="role_id")
  private long roleId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }
  
  public static List<Role> fingUserRolesByUserId(EntityManager entityManager, long userId) {
    TypedQuery<Role> query = entityManager
                      .createNamedQuery("findUserRolesByUserId", Role.class)
                      .setParameter("userId", userId);
    
    return query.getResultList();
  }
  
}
