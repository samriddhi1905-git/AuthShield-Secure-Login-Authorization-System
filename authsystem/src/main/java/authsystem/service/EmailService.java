package authsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

     public void sendApprovalEmail(
        String toEmail,
        String token,
        String ipAddress,
        String browser,
        String loginTime,
        String location){

        String approveLink =
                "http://localhost:8080/auth/approve/" + token;

        String rejectLink =
                "http://localhost:8080/auth/reject/" + token;

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Login Approval Required");

            String htmlContent =
                    "<h2>New Login Attempt Detected</h2>"

                    + "<p><b>Email:</b> " + toEmail + "</p>"

                    + "<p><b>Login Time:</b> "
                    + loginTime + "</p>"

                    + "<p><b>Location:</b> "
+ location + "</p>"

                    + "<p><b>IP Address:</b> "
                    + ipAddress + "</p>"

                    + "<p><b>Browser:</b><br>"
                    + browser + "</p>"

                    + "<br><br>"

                    + "<a href='" + approveLink + "' "
                    + "style='background-color:green;"
                    + "color:white;padding:10px 20px;"
                    + "text-decoration:none;border-radius:5px;'>"
                    + "Approve Login</a>"

                    + "&nbsp;&nbsp;"

                    + "<a href='" + rejectLink + "' "
                    + "style='background-color:red;"
                    + "color:white;padding:10px 20px;"
                    + "text-decoration:none;border-radius:5px;'>"
                    + "Reject Login</a>";

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}