package tomo.ma.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:env.properties")
public class SmsService {

    @Value("${twilio.account_sid}")
    private static  String ACCOUNT_SID;
    @Value("$twilio.auth_token")
    private static  String AUTH_TOKEN ;
    @Value("$twilio.from_phone")
    private static  String FROM_PHONE_NUMBER ; // numéro Twilio

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),        // destinataire
                    new PhoneNumber(FROM_PHONE_NUMBER),    // expéditeur (Twilio)
                    messageBody
            ).create();

            System.out.println("SMS envoyé avec SID : " + message.getSid());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du SMS : " + e.getMessage());
        }
    }
}
