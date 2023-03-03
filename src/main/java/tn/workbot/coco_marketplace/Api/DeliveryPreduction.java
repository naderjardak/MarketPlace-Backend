package tn.workbot.coco_marketplace.Api;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.repositories.PickupRepository;

import java.util.List;

@Service
public class DeliveryPreduction {
    @Autowired
    PickupRepository pr;
    private SimpleRegression regressionModel;
    public DeliveryPreduction() {
        // Initialize the linear regression model
        regressionModel = new SimpleRegression();
    }

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

}
