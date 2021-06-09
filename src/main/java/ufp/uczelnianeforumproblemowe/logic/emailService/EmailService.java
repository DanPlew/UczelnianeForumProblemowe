package ufp.uczelnianeforumproblemowe.logic.emailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements WysylanieEmailInterface{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(@Autowired JavaMailSender javaMailSender){
        this.mailSender = javaMailSender;
    }

    @Override
    @Async
    public void wyslij(String doKogo, String wiadomosc) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(wiadomosc, true);
            mimeMessageHelper.setTo(doKogo);
            mimeMessageHelper.setSubject("Potwierdz email");
            mimeMessageHelper.setFrom("hello@amigoscode.com");
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("Nie mozna wyslac maila", e);
            throw new IllegalStateException("Nie mozna wyslac maila");
        }
    }
}
