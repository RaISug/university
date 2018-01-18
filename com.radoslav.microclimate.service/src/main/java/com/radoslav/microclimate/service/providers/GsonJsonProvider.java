package com.radoslav.microclimate.service.providers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.radoslav.microclimate.service.helpers.Constants;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GsonJsonProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

  private static final Logger logger = LoggerFactory.getLogger(GsonJsonProvider.class);
  
  private Gson getGson() {
    return new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

      public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
          return jsonElement == null ? null : new SimpleDateFormat(Constants.DATE_FORMAT).parse(jsonElement.getAsString());
        } catch (ParseException e) {
          throw new RuntimeException("Failed to parse the json element to java.util.Date type.");
        }
      }
    }).setPrettyPrinting().create();
  }

  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return true;
  }

  public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return 0;
  }

  public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
    String json = getGson().toJson(t);
    OutputStream output = new BufferedOutputStream(entityStream);
    output.write(json.getBytes(Charset.forName("utf-8")));
    output.flush();
  }

  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return true;
  }

  public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(entityStream, "UTF-8"));
    try {
      JsonElement json = new JsonParser().parse(reader);
      return getGson().fromJson(json, genericType);
    } catch (JsonParseException e) {
      logger.warn("Failed to parse request body. Null value will be returned.");
      return null;
    }
  }

}
