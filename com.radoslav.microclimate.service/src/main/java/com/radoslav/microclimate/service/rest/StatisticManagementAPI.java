package com.radoslav.microclimate.service.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.radoslav.microclimate.service.annotations.EntityManagerBinding;
import com.radoslav.microclimate.service.beans.StatisticBean;
import com.radoslav.microclimate.service.dbaccessors.StatisticPersistenceHelper;
import com.radoslav.microclimate.service.entities.Statistic;
import com.radoslav.microclimate.service.exceptions.BadRequestException;
import com.radoslav.microclimate.service.exceptions.InternalServerErrorException;
import com.radoslav.microclimate.service.helpers.ValidationUtil;

@Path("statistics")
@EntityManagerBinding
public class StatisticManagementAPI {

  private static final Logger logger = LoggerFactory.getLogger(StatisticManagementAPI.class);
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Statistic> getStatisticData(@Context EntityManager entityManager, @QueryParam("") StatisticBean statistic) throws InternalServerErrorException {
    logger.debug("Trying to retriev searched statistic data.");
    
    StatisticPersistenceHelper persistenceHelper = new StatisticPersistenceHelper(entityManager);
    List<Statistic> result =  persistenceHelper.findStatisticData(statistic);
    
    logger.debug("Founded statistic data: [{}].", result);
    return result;
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addNewStatisticData(@Context EntityManager entityManager, StatisticBean statistic) throws Exception {
    ValidationUtil.validateStatisticInputData(statistic);
    
    logger.debug("Statistic information with the following properties: [{}] will be persisted.", statistic);
    
    StatisticPersistenceHelper persistenceHelper = new StatisticPersistenceHelper(entityManager);
    persistenceHelper.persistStatisticData(statistic);
    
    logger.debug("Statistic data was successfully persisted.");
    
    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateStatisticData(@Context EntityManager entityManager, @PathParam("id") long id, StatisticBean statistic) throws Exception {
    ValidationUtil.validateStatisticInputData(statistic);
    
    if (id <= 0) {
      throw new BadRequestException("\"id\" path parameter must be specified with numeric value, which is greater than zero.");
    }
    
    logger.debug("Statistic information with the following properties: [{}] will be updated.", statistic);
    
    StatisticPersistenceHelper persistenceHelper = new StatisticPersistenceHelper(entityManager);
    persistenceHelper.updateStatisticData(statistic, id);
    
    logger.debug("Statistic data was successfully updated.");
    
    return Response.status(Status.NO_CONTENT).build();
  }
  
  @DELETE
  @Path("/{id}")
  public Response deleteStatisticData(@Context EntityManager entityManager, @PathParam("id") long id) throws Exception {
    if (id <= 0) {
      throw new BadRequestException("\"id\" path parameter must be specified with numeric value, which is greater than zero.");
    }
    
    logger.debug("Statistic information with the following id: [{}] will be deleted.", id);
    
    StatisticPersistenceHelper persistenceHelper = new StatisticPersistenceHelper(entityManager);
    persistenceHelper.deleteStatisticData(id);
    
    logger.debug("Statistic data was successfully deleted.");
    
    return Response.status(Status.ACCEPTED).build();
  }
  
}
