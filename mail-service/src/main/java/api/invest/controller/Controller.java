package api.invest.controller;

import api.invest.dto.VerificationDto;
import api.invest.service.SenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final SenderService sender;

    @PostMapping("/send-verification")
    public void send(@RequestBody VerificationDto verificationDto) throws MessagingException, UnsupportedEncodingException {
        sender.sendVerification(verificationDto);
    }
}
