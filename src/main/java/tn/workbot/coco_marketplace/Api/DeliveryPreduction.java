package tn.workbot.coco_marketplace.Api;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.PickupRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;

import java.util.List;

@Service
public class DeliveryPreduction {
    @Autowired
    PickupRepository pr;
    @Autowired
    UserrRepository ur;
    private SimpleRegression regressionModel;
    public DeliveryPreduction() {
        // Initialize the linear regression model
        regressionModel = new SimpleRegression();
    }
    /////////preduct sum with num request
    public void addDelivery() {
        // Add the delivery data to the linear regression model
        List<Pickup>pickups=pr.ListePickup();
        for (Pickup pickup:pickups) {
            regressionModel.addData(pickup.getNbRequest(),pickup.getSum());
        }

    }

    public double predict(Float r) {
        // Predict the delivery Sum using the linear regression model
        return  regressionModel.predict(r);
    }
    ///preduct co2 with ageCAR
    public  void addUser(){
        // Add the user data to the linear regression model
        List<User> userList= (List<User>) ur.findAll();
        for (User u:userList) {
            regressionModel.addData(u.getGearAge(),u.getCo2());
        }
    }
    public double predictco2(Float gearAge) {
        // Predict the CO2 in car using the linear regression model
        return  regressionModel.predict(gearAge);
    }

}
