package com.radoslav.log.analyzer.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.radoslav.log.analyzer.constants.HtmlConstants;
import com.radoslav.log.entries.LogEntry;

public class FileGenerator {
  
  public static OutputStream generateFileFromEntries(List<LogEntry> logEntries, OutputStream outputStream) {
    if (logEntries == null || logEntries.size() == 0) {
      throw new RuntimeException("Log entries are not provided.");
    }
    
    StringBuilder builder = new StringBuilder();
    
    builder.append(HtmlConstants.HTML_START_TAG);
    builder.append(HtmlConstants.HEAD_START_TAG);
    
    appendLinks(builder);
    appendScripts(builder);
    
    builder.append(HtmlConstants.HEAD_END_TAG);
    builder.append(HtmlConstants.BODY_START_TAG);
    
    appendHeaders(builder);
    appendBody(builder, logEntries);
    appendFooter(builder);
    
    builder.append(HtmlConstants.BODY_END_TAG);
    builder.append(HtmlConstants.HTML_END_TAG);
    
    return writeGeneratedContentToOutputStream(outputStream, builder);
  }

  private static void appendLinks(StringBuilder builder) {
    
  }
  
  private static void appendScripts(StringBuilder builder) {
    
  }

  private static void appendHeaders(StringBuilder builder) {

  }

  private static void appendBody(StringBuilder builder, List<LogEntry> logEntries) {
    if (logEntries.size() == 0) {
      return;
    }
    
    builder.append(HtmlConstants.TABLE_START_TAG);
    
    appendTableHeaders(builder, logEntries.get(0));
    
    for (LogEntry logEntry : logEntries) {
      builder.append(HtmlConstants.TABLE_ROW_START_TAG);

      builder.append(HtmlConstants.TABLE_DATA_START_TAG).append(logEntry.getDate()).append(HtmlConstants.TABLE_DATA_END_TAG);
      builder.append(HtmlConstants.TABLE_DATA_START_TAG).append(logEntry.getSeverity()).append(HtmlConstants.TABLE_DATA_END_TAG);
      builder.append(HtmlConstants.TABLE_DATA_START_TAG).append(logEntry.getMessage()).append(HtmlConstants.TABLE_DATA_END_TAG);
      
      for (Object value : logEntry.getLogProperties().values()) {
        builder.append(HtmlConstants.TABLE_DATA_START_TAG).append(value).append(HtmlConstants.TABLE_DATA_END_TAG);
      }
      
      builder.append(HtmlConstants.TABLE_ROW_END_TAG);
    }
    
    builder.append(HtmlConstants.TABLE_END_TAG);
  }
  
  private static void appendTableHeaders(StringBuilder builder, LogEntry logEntry) {
    builder.append(HtmlConstants.TABLE_ROW_START_TAG);

    builder.append(HtmlConstants.TABLE_HEADER_START_TAG).append("Time").append(HtmlConstants.TABLE_HEADER_END_TAG);
    builder.append(HtmlConstants.TABLE_HEADER_START_TAG).append("Severity").append(HtmlConstants.TABLE_HEADER_END_TAG);
    builder.append(HtmlConstants.TABLE_HEADER_START_TAG).append("Message").append(HtmlConstants.TABLE_HEADER_END_TAG);
    
    for (String key : logEntry.getLogProperties().keySet()) {
      builder.append(HtmlConstants.TABLE_HEADER_START_TAG).append(key).append(HtmlConstants.TABLE_HEADER_END_TAG);
    }
    
    builder.append(HtmlConstants.TABLE_ROW_END_TAG);
  }
  
  private static void appendFooter(StringBuilder builder) {
    
  }

  private static OutputStream writeGeneratedContentToOutputStream(OutputStream outputStream, StringBuilder builder) {
    ZipEntry zipEntry = new ZipEntry("analyze.html");

    try (ZipOutputStream zipStream = new ZipOutputStream(outputStream)) {
      zipStream.putNextEntry(zipEntry);

      byte[] content = builder.toString().getBytes(StandardCharsets.UTF_8);
      zipStream.write(content, 0, content.length);

      zipStream.closeEntry();

      return zipStream;
    } catch (IOException e) {
      throw new RuntimeException("Failed to generate output stream");
    }
  }

}
