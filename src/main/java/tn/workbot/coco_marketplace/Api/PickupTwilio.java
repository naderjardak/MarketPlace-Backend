package tn.workbot.coco_marketplace.Api;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PickupTwilio {
    public static ResponseEntity<String> sendSMS(String msg) {

        Twilio.init("AC915cfd330fe7a8b2cacdd031af356e39","7f567885b19a9f20b7ceada26b115be3");
        Message.creator(new PhoneNumber("+21626662264"),
                new PhoneNumber("+13854062174"), "\n📞"+msg).create();

        return new ResponseEntity<String>(msg, HttpStatus.OK);
    }
}
