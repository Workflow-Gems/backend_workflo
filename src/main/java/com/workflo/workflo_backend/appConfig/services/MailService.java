package com.workflo.workflo_backend.appConfig.services;

import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.response.MailResponse;
import com.workflo.workflo_backend.exceptions.SendMailException;

public interface MailService {
    MailResponse sendMail(MailRequest request) throws SendMailException;
}
