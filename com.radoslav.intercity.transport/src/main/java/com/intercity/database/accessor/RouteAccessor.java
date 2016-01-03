package com.intercity.database.accessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.intercity.application.pojo.SearchBean;
import com.intercity.database.connection.Connector;
import com.intercity.database.entity.Route;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;
import com.intercity.exception.InputParamException;

public class RouteAccessor {

  private final String DIRECT = "direct";
  
  @SuppressWarnings("unchecked")
  public List<Route> getRoutes() throws DatabaseConnectionException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      List<Route> result = entityManager.createNamedQuery("Route.findAll").getResultList();
      
      return result == null ? new ArrayList<Route>() : result;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  public Route getRouteById(int routeId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager
          .createNamedQuery("Route.findRouteById")
          .setParameter("id", routeId);
      Route route = (Route) query.getSingleResult();
      if (route == null) {
        throw new DatabaseResultException("No data.", 204);
      }
      return route;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<Date> getDepartureTimeList() throws DatabaseConnectionException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager.createNamedQuery("Route.departureTime");
      List<Date> queryResult =  query.getResultList();
      
      if (queryResult == null || queryResult.size() == 0) {
        return new ArrayList<Date>();
      }
      return queryResult;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<Route> searchRoutes(SearchBean searchData) throws DatabaseConnectionException, DatabaseResultException, InputParamException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      
      Query query = createNamedQuery(entityManager, searchData);
      
      List<Route> searchedRoutes = query.getResultList();
      if (searchedRoutes == null || searchedRoutes.size() == 0) {
        throw new DatabaseResultException("No data.", 204);
      }
      return isDirectTransport(searchData) ? searchedRoutes : searchDestiantion(searchedRoutes, searchData);
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  private Query createNamedQuery(EntityManager entityManager, SearchBean searchData) throws InputParamException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    Date currentTime = null;
    try {
      currentTime = simpleDateFormat.parse(searchData.getDepartureTime());
    } catch(ParseException exception) {
      throw new InputParamException("Wrong time format.", 400);
    }
    
    if (isDirectTransport(searchData)) {
      return entityManager.createNamedQuery("Route.searchRoutes")
          .setParameter("startDestination", searchData.getStartDestination())
          .setParameter("endDestination", searchData.getEndDestination())
          .setParameter("departureTime", simpleDateFormat.format(currentTime));
    }
    
    return entityManager.createNamedQuery("Route.searchRoutesWithTransference")
        .setParameter("startDestination", searchData.getStartDestination())
        .setParameter("departureTime", simpleDateFormat.format(currentTime));
  }
  
  
  private boolean isDirectTransport(SearchBean searchBean) {
    return searchBean.getTransportType().equals(DIRECT);
  }
  
  private List<Route> searchDestiantion(List<Route> searchedRoutes, SearchBean searchData) {
    Iterator<Route> iterator = searchedRoutes.iterator();
    while (iterator.hasNext()) {
      Route route = iterator.next();
      String description = route.getDescription();
      String searchedDestination = "\"startDestination\":\"" + searchData.getEndDestination() + "\"";
      if (description.contains(searchedDestination) == false) {
        iterator.remove();
      }
    }
    return searchedRoutes;
  }
}
