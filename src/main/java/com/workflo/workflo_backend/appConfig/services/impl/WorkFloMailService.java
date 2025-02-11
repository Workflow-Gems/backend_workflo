package com.workflo.workflo_backend.appConfig.services.impl;

import com.workflo.workflo_backend.appConfig.config.MailConfig;
import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.response.MailResponse;
import com.workflo.workflo_backend.appConfig.services.MailService;
import com.workflo.workflo_backend.exceptions.SendMailException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static java.net.URI.create;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Service
@AllArgsConstructor
public class WorkFloMailService implements MailService {
    private final MailConfig mailConfig;
    private final TemplateEngine templateEngine;
    @Override
    public MailResponse sendMail(MailRequest request, String template, Context context) throws SendMailException {
        final String content = templateEngine.process(template,context);
        request.setHtmlContent(content);
        try {
            HttpEntity<MailRequest> httpEntity = new RequestEntity<>(
                    request, mailConfig.httpHeaders(), POST, create("")
            );
            ResponseEntity<MailResponse> responseEntity =
                    mailConfig.restTemplate().postForEntity(mailConfig.getUrl(), httpEntity, MailResponse.class);
            MailResponse response = responseEntity.getBody();
            response.setStatusCode((long) responseEntity.getStatusCode().value());
            return response;
        }catch (HttpClientErrorException exception){
            throw new SendMailException(exception.getMessage());
        }
    }
}
