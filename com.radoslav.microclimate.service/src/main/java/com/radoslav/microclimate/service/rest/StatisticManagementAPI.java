package com.radoslav.microclimate.service.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
import com.radoslav.microclimate.service.exceptions.InternalServerErrorException;
import com.radoslav.microclimate.service.helpers.ValidationUtil;

@Path("statistics")
@EntityManagerBinding
public class StatisticManagementAPI {

  Logger logger = LoggerFactory.getLogger(StatisticManagementAPI.class);
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Statistic> getStatistics(@Context EntityManager entityManager) throws InternalServerErrorException {
    StatisticPersistenceHelper helper = new StatisticPersistenceHelper(entityManager);
    logger.debug("Trying to retrievw all statistic data.");
    List<Statistic> result = helper.getAll();
    logger.debug("Founded statistic data: [{}].", result);
    return result;
  }
  
  @POST
  public Response addNewStatisticData(@Context EntityManager entityManager, StatisticBean statistic) throws Exception {
    logger.debug("Statistic information with the following properties: [{}] will be proccessed.", statistic);
    
    String temperature = statistic.getTemperature();
    String rainfall = statistic.getRainfall();
    String humidity = statistic.getHumidity();
    String snowCover = statistic.getSnowCover();
    String windSpeed = statistic.getWindSpeed();
    String type = statistic.getType();
    
    ValidationUtil.validateThatParamIsNotEmpty(temperature, "temperature");
    ValidationUtil.validateThatParamIsNotEmpty(rainfall, "rainfall");
    ValidationUtil.validateThatParamIsNotEmpty(humidity, "humidity");
    ValidationUtil.validateThatParamIsNotEmpty(snowCover, "snowCover");
    ValidationUtil.validateThatParamIsNotEmpty(windSpeed, "windSpeed");
    ValidationUtil.validateThatParamIsNotEmpty(type, "type");
    
    ValidationUtil.validateThatParameterContainsFloatValue(temperature, "temperature");
    ValidationUtil.validateThatParameterContainsFloatValue(rainfall, "rainfall");
    ValidationUtil.validateThatParameterContainsFloatValue(humidity, "humidity");
    ValidationUtil.validateThatParameterContainsFloatValue(snowCover, "snowCover");
    ValidationUtil.validateThatParameterContainsFloatValue(windSpeed, "windSpeed");
    
    StatisticPersistenceHelper helper = new StatisticPersistenceHelper(entityManager);
    helper.persistStatisticData(statistic);
    
    logger.debug("Statistic data was successfully persisted.");
    
    return Response.status(Status.CREATED).build();
  }
  
}
