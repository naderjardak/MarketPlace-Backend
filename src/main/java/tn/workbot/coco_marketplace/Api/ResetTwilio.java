package tn.workbot.coco_marketplace.Api;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResetTwilio {
    public static ResponseEntity<String> sendSMS(String msg) {

        Twilio.init("AC940d0fd64dbce61e4d601e262513da82","46c4dd577f5940480ae0f7273023579e");
        Message.creator(new PhoneNumber("+21658665254"),
                new PhoneNumber("+15074362613"), "\n📞"+msg).create();

        return new ResponseEntity<String>(msg, HttpStatus.OK);
    }
}
