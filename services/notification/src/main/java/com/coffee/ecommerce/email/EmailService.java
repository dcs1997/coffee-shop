package com.coffee.ecommerce.email;

import com.coffee.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String destinationEmail,
                                        String CustomerName,
                                        BigDecimal amount,
                                        String orderReference) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());

        messageHelper.setFrom("dhirajsalvi099@gmail.com");

        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();
        final String templateSubject = EmailTemplates.PAYMENT_CONFIRMATION.getSubject();

        Map<String, Object> map = new HashMap<>();
        map.put("customerName", CustomerName);
        map.put("amount", amount);
        map.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(map);
        messageHelper.setSubject(templateSubject);
        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO -> Email successfully sent to %s with template %s",
                    destinationEmail, templateName));

        }catch (MessagingException e){
            log.warn("Cannot send email to %s",destinationEmail);
        }
    }


    @Async
    public void sendOrderConfirmationEmail(String destinationEmail,
                                        String CustomerName,
                                        BigDecimal amount,
                                        String orderReference,
                                           List<Product> products) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());

        messageHelper.setFrom("dhirajsalvi099@gmail.com");

        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplate();
        final String templateSubject = EmailTemplates.ORDER_CONFIRMATION.getSubject();

        Map<String, Object> map = new HashMap<>();
        map.put("customerName", CustomerName);
        map.put("totalAmount", amount);
        map.put("orderReference", orderReference);
        map.put("products", products);

        Context context = new Context();
        context.setVariables(map);
        messageHelper.setSubject(templateSubject);
        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO -> Email successfully sent to %s with template %s",
                    destinationEmail, templateName));

        }catch (MessagingException e){
            log.warn("Cannot send email to %s",destinationEmail);
        }
    }
}
