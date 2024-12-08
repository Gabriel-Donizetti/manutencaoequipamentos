package com.web2.manutencaoequipamentos.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public boolean sendEmail(String nomeDestinatario, String destinatario, String assunto, String mensagem) {
        try {
            String htmlContent = getHtmlContent(nomeDestinatario, mensagem);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(destinatario);
            helper.setFrom(remetente);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

            logger.info("Email HTML enviado com sucesso para {}", destinatario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getHtmlContent(String nome, String mensagem) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f5f5f5; margin: 0; padding: 0; }\n" +
                "        .container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); padding: 20px; }\n"+
                "        .header { background-color: #d3d3d3; padding: 10px 20px; border-top-left-radius: 8px; border-top-right-radius: 8px; text-align: center; font-size: 20px; color: #333333; font-weight: bold; }\n" +
                "        .content { font-size: 16px; color: #555555; padding: 20px; line-height: 1.6; text-align: left; }\n"+
                "        .footer { font-size: 14px; color: #777777; padding: 10px 0; text-align: center; border-top: 1px solid #e0e0e0; margin-top: 20px; border-radius: 4px; }\n"+
                "        .button { display: inline-block; background-color: #555555; color: #ffffff; text-decoration: none; padding: 10px 20px; margin: 20px 0; border-radius: 5px; transition: background-color 0.2s ease; box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1); }\n"+
                "        .button:hover { background-color: #777777; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">Bem-Vindo ao Sistema</div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Olá,</p>\n" +
                "    <p>" + nome + "</p>\n" +
                "            <p>Obrigado por se registrar em nosso sistema! Seu registro foi realizado com sucesso.</p>\n"+
                "    <p>Sua senha é: " + mensagem + "</p>\n" +
                "            <p>Caso você tenha qualquer dúvida, sinta-se à vontade para entrar em contato com nossa equipe.</p>\n"+
                "        </div>\n" +
                "        <div class=\"footer\">&copy; 2024 Manutenção de Equipamentos - Todos os direitos reservados.</div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
