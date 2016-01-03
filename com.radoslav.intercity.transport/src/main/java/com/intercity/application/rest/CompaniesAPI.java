package com.intercity.application.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.intercity.database.accessor.CompanyAccessor;
import com.intercity.database.entity.Company;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

@Path("transport/api/v1/companies")
public class CompaniesAPI {
  
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Company getCompany(@PathParam("id") int companyId) throws DatabaseConnectionException, DatabaseResultException {
    CompanyAccessor accessor = new CompanyAccessor();
    return accessor.getCompanyById(companyId);
  }
  
}
