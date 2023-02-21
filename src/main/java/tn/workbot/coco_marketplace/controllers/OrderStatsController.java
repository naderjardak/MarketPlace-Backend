package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order/stats")
public class OrderStatsController {
    @Autowired
    OrderInterface orderInterface;

    @GetMapping("OrderStatsByStatusType")
    Map<String, Integer> statsByStatusType(){return orderInterface.statsByStatusType();}

    @GetMapping("OrderRankForUser")
    List<String> statsByStatusTypeOrdred(){return orderInterface.statsByStatusTypeOrdred();}

}
