package api.invest.controller;

import api.invest.dto.EmailDto;
import api.invest.service.SenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final SenderService sender;

    @PostMapping("/send")
    public void send(@RequestBody EmailDto email) throws MessagingException, UnsupportedEncodingException {
        sender.sendEmail(email);
    }
}
