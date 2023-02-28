package com.example.welcometoesprit.controller;


import jakarta.servlet.http.HttpServletResponse;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class GenerateQRCode {

    @GetMapping("/admin/qrcode")
    @ResponseBody
    public void generateQRCode(String link, HttpServletResponse response) throws IOException {
        link="https://www.linkedin.com/in/mahdi-fersi-349577215/";
        ByteArrayOutputStream out = QRCode.from(link).to(ImageType.PNG).stream();
        response.setContentType("image/png");
        response.setContentLength(out.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"qrcode.png\"");
        response.getOutputStream().write(out.toByteArray());
        response.flushBuffer();
    }
    }

