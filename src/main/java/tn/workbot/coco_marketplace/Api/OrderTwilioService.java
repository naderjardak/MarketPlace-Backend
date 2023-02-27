package tn.workbot.coco_marketplace.Api;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderTwilioService {

    public static ResponseEntity<String> sendSMS(String msg) {

        Twilio.init("AC4ad18a2d3b9e37a630727e7818a0920d","ea8c0f247f12d453f340d28b72f86ad5");
        Message.creator(new PhoneNumber("+21690446128"),
                new PhoneNumber("+18585195482"), "\n📞"+msg).create();

        return new ResponseEntity<String>(msg, HttpStatus.OK);
    }
}