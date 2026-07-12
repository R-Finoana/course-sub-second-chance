package com.school.hei.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class PdfGenerationService {
  @SneakyThrows
  public File generateSubscriptionPdf(String userName, String courseName) {
    var tempFile = File.createTempFile("course-sub-", ".pdf");

    String htmlContent = generateHtmlContent(userName, courseName);
    try (OutputStream out = new FileOutputStream(tempFile)) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.withHtmlContent(htmlContent, null);
      builder.toStream(out);
      builder.run();
    }
    return tempFile;
  }

  public String generateHtmlContent(String userName, String courseName) {
    return """
<html>
<head>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .header { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
        .content { margin: 30px 0; }
        .footer { margin-top: 40px; font-size: 12px; color: #7f8c8d; border-top: 1px solid #bdc3c7; padding-top: 10px; }
        .subscription-details { background: #f8f9fa; padding: 15px; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Subscription confirmation</h1>
    </div>
    <div class="content">
        <p>Hello <strong>%s</strong>,</p>
        <p>Your subscription to <strong>%s</strong> was successfully confirmed.</p>
        <div class="subscription-details">
            <p><strong>Date :</strong> %s</p>
        </div>
    </div>
    <div class="footer">
        <p>Ce document est une preuve d'abonnement valide.</p>
        <p>Pour toute question, contactez-nous à support@school.com</p>
    </div>
</body>
</html>
"""
        .formatted(
            userName,
            courseName,
            java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
  }
}
