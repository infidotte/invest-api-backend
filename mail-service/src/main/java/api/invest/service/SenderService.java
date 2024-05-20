package api.invest.service;

import api.invest.dto.VerificationDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class SenderService {
    private final JavaMailSender mailSender;
    @Value("${mail.credentials.name}")
    private String senderName;
    @Value("${mail.credentials.email}")
    private String senderAddress;

    public void sendVerification(VerificationDto dto) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>" + senderName;
        content = content.replace("[[name]]", dto.username());
        content = content.replace("[[URL]]", String.format("localhost:8762/verify?username=%s&code=%s", dto.username(), dto.code()));
        helper.setSubject("Please verify your registration");
        helper.setText(content);
        helper.setFrom(senderAddress, senderName);
        helper.setTo(dto.toAddress());
        mailSender.send(mimeMessage);
    }
}
