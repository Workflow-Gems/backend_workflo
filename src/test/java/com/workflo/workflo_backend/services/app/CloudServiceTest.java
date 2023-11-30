package com.workflo.workflo_backend.services.app;


import com.workflo.workflo_backend.appConfig.services.CloudService;
import com.workflo.workflo_backend.exceptions.CloudUploadException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CloudServiceTest {


    @Autowired
    private CloudService cloudService;


    private static final String location = "C:\\\\Users\\\\leuma\\\\IdeaProjects\\\\Workflo-backend_project\\\\src\\\\main\\\\resources\\\\static\\\\Snapchat-1585738964.jpg";
    @Test
    public void uploadToCloud() throws CloudUploadException {
        MultipartFile file = createMultipart();
        String response = cloudService.upload(file);
        assertThat(response).isNotNull();
    }
    public static MultipartFile createMultipart() throws CloudUploadException{
        Path path = Path.of(location);
        try {
            InputStream inputStream = Files.newInputStream(path);
            return new MockMultipartFile(path.getFileName().toString(), inputStream);
        }catch (IOException e){
            throw new CloudUploadException(e.getMessage());
        }
    }
}
