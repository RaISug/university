package com.intercity.database.accessor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.intercity.database.connection.Connector;
import com.intercity.database.entity.Company;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

public class CompanyAccessor {

  @SuppressWarnings("unchecked")
  public List<Company> getCompanies() throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager.createNamedQuery("Company.findAll");
      List<Company> companies = query.getResultList();
      
      if (companies.size() == 0) {
        throw new DatabaseResultException("No content.", 204);
      }
      return companies;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  public Company getCompanyById(int companyId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager.createNamedQuery("Company.getById")
          .setParameter("id", companyId);
      Company company = (Company) query.getSingleResult();
      if (company == null) {
        throw new DatabaseResultException("No content.", 204);
      }
      return company;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
}
