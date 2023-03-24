package tn.workbot.coco_marketplace.controllers;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.services.interfaces.OrderInterface;
import tn.workbot.coco_marketplace.Api.OrderStatsPDFGenerator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orderStats")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")

public class OrderStatsController {
    @Autowired
    OrderInterface orderInterface;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("OrderStatsByStatusType")
    Map<String, Integer> statsByStatusType(){return orderInterface.statsByStatusType();}

    @GetMapping("OrderRankForUsersByStatusType")
    List<String> statsByStatusTypeOrdred(){return orderInterface.statsByStatusTypeOrdred();}

    @GetMapping("RankGouvernoratByOrdersNumber")
    List<Map<String,Integer>> GovernoratTopShipped() {return orderInterface.GovernoratTopShipped();}

    @GetMapping(value = "PDF_RankGouvernoratByOrdersNumber", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> PDF_RankGouvernoratByOrdersNumber() throws IOException {
        List<String> stats = orderRepository.PDF_RankGouvernoratByNbOrders();

        ByteArrayInputStream bis = OrderStatsPDFGenerator.RankGouvernoratByOrdersNumberPDFReport(stats);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "attachment; filename="+new Date(System.currentTimeMillis())+".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "PDF_OrderRankForUsersByStatusType", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> PDF_OrderRankForUsers() throws IOException {
        List<String> stats = orderInterface.statsByStatusTypeOrdred();

        ByteArrayInputStream bis = OrderStatsPDFGenerator.OrderRankForUsersPDFReport(stats);
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
