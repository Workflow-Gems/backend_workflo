package com.workflo.workflo_backend.services.app;


import com.workflo.workflo_backend.appConfig.dtos.request.To;
import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.response.MailResponse;
import com.workflo.workflo_backend.appConfig.services.MailService;
import com.workflo.workflo_backend.exceptions.SendMailException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;


    @Test
    public void sendMailTest() throws SendMailException {
        Context context = new Context();
        To to = new To("name","pijog53310@frandin.com");
        MailRequest request = new MailRequest();
        request.setHtmlContent("html");
        request.setSubject("subject");
        request.setTo(List.of(to));
        MailResponse response = mailService.sendMail(request, "", context);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isGreaterThanOrEqualTo(200);
    }
}
