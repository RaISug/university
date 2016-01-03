package com.moutain.microclimate.service.dbaccessors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.moutain.microclimate.service.exceptions.InternalServerErrorException;

public class TransactionHandler {

  public static <T> T handleTransaction(EntityManager entityManager, CriticalSection<T> criticalSection) throws Exception {
    T executionResult = null;
    EntityTransaction transaction = null;
    
    try {
      transaction = entityManager.getTransaction();
      
      transaction.begin();
      
      criticalSection.executeQuery();
      
      transaction.commit();
      
      executionResult = criticalSection.getResult();
    } catch (Exception exception) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw exception;
    } finally {
      if (transaction.isActive()) {
        transaction.rollback();
        throw new InternalServerErrorException("Transaction was not closed properly.");
      }
    }
    return executionResult;
  }
}
