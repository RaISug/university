package com.radoslav.log.analyzer.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.radoslav.log.entries.LogEntry;

public class FileGenerator {

  public static OutputStream generateFileFromEntries(List<LogEntry> logEntries, OutputStream outputStream) {
    if (logEntries == null) {
      throw new RuntimeException("Log entries are not provided.");
    }
    
    StringBuilder builder = new StringBuilder();
    
    builder.append("<html>");
    builder.append("<head>");
    
    appendLinks(builder);
    appendScripts(builder);
    
    builder.append("</head>");
    builder.append("<body>");
    
    appendHeaders(builder);
    appendBody(builder, logEntries);
    appendFooter(builder);
    
    builder.append("</body>");
    builder.append("</html>");
    
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
    
    builder.append("<table border='1'>");
    
    appendTableHeaders(builder, logEntries.get(0));
    
    for (LogEntry logEntry : logEntries) {
      builder.append("<tr>");
      builder.append("<td>").append(logEntry.getDate()).append("</td>");
      builder.append("<td>").append(logEntry.getSeverity()).append("</td>");
      builder.append("<td>").append(logEntry.getMessage()).append("</td>");
      
      for (Object value : logEntry.getLogProperties().values()) {
        builder.append("<td>").append(value).append("</td>");
      }
      
      builder.append("</tr>");
    }
    
    builder.append("</table>");
  }
  
  private static void appendTableHeaders(StringBuilder builder, LogEntry logEntry) {
    builder.append("<tr>");
    builder.append("<th>Time</th>");
    builder.append("<th>Severity</th>");
    builder.append("<th>Message</th>");
    
    for (String key : logEntry.getLogProperties().keySet()) {
      builder.append("<th>").append(key).append("</th>");
    }
    
    builder.append("</tr>");
  }
  
  private static void appendFooter(StringBuilder builder) {
    
  }

  private static OutputStream writeGeneratedContentToOutputStream(OutputStream outputStream, StringBuilder builder) {
    ZipOutputStream zipStream = new ZipOutputStream(outputStream);
    
    ZipEntry zipEntry = new ZipEntry("test.html");
    
    try {
      zipStream.putNextEntry(zipEntry);
      
      byte[] content = builder.toString().getBytes();
      zipStream.write(content, 0, content.length);
      
      return zipStream;
    } catch (IOException e) {
      throw new RuntimeException("Failed to generate output stream");
    } finally {
      try {
        zipStream.closeEntry();
      } catch (IOException exception) {
        throw new RuntimeException("Failed to close entry stream`s");
      }
    }
  }

}
