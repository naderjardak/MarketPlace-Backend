package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;
import tn.workbot.coco_marketplace.Api.OrderStatsPDFGenerator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order/stats")
public class OrderStatsController {
    @Autowired
    OrderInterface orderInterface;

    @GetMapping("OrderStatsByStatusType")
    Map<String, Integer> statsByStatusType(){return orderInterface.statsByStatusType();}

    @GetMapping("OrderRankForUsers")
    List<String> statsByStatusTypeOrdred(){return orderInterface.statsByStatusTypeOrdred();}

    @GetMapping("RankGouvernoratByOrdersNumber")
    List<String> GovernoratTopShipped(){return orderInterface.GovernoratTopShipped();}

    @GetMapping(value = "Stats", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> customerReport() throws IOException {
        List<String> stats = orderInterface.GovernoratTopShipped();

        ByteArrayInputStream bis = OrderStatsPDFGenerator.customerPDFReport(stats);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "attachment; filename="+new Date(System.currentTimeMillis())+".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
