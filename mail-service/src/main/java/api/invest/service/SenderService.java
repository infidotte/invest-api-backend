package api.invest.service;

import api.invest.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromAddress;

    public void sendEmail(EmailDto dto) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("mailtrap@demomailtrap.com");
        helper.setTo(dto.toAddress());
        helper.setSubject(dto.subject());
        helper.setText(dto.content());
        mailSender.send(mimeMessage);
    }
}
