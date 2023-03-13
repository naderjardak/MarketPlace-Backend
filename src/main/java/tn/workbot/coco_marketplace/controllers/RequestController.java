package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Request;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.services.interfaces.RequestInterface;

import java.util.List;

@RestController
@RequestMapping("RequestController")
public class RequestController   {
    @Autowired
    RequestInterface ri;
    @PostMapping("addRequest")
    public Request addRequest(@RequestBody Request request) {
        return ri.addRequest(request);
    }

    @DeleteMapping("deleteRequest")
    public void removeRequest(@RequestParam Long id) {
          ri.removeRequest(id);
    }

    @GetMapping("retrieveRequest")
    public Request RetrieveRequest(@RequestParam Long id) {
        return ri.RetrieveRequest(id);
    }

    @GetMapping("retriveRequests")
    public List<Request> RetrieveRequests() {
        return ri.RetrieveRequests();
    }

    @PutMapping("updateRequest")
    public Request updateRequest(@RequestBody Request request) {
        return ri.updateRequest(request);
    }

    @PostMapping("assignRequestDeliveryAgencyandPickup")
    public Request assignRequestDeliveryAgencyandPickup(@RequestBody Request request,@RequestParam Long idDeliveryAgency,@RequestParam Long idPickup,@RequestParam Long idDeliveryMenAgency) {
       return  ri.assignRequestDeliveryAgencyandDeliverymenandPickup(request, idDeliveryAgency,idPickup,idDeliveryMenAgency);
    }

    @PostMapping("assignRequestDeliveryMenFreelancerandPickup")
    public Request assignRequestDeliveryMenFreelancerandPickup(@RequestBody Request request,@RequestParam Long idDeliveryMenFreelancer,@RequestParam Long idPickup) {
        return ri.assignRequestDeliveryMenFreelancerandPickup(request, idDeliveryMenFreelancer, idPickup);
    }
    @PostMapping("assignRequesttoseller")
    public Request assignRequesttoseller(@RequestParam Long idRequest,@RequestParam Long idSeller,@RequestParam String status,@RequestParam Long idPickup) {
        return ri.assignRequesttoseller(idRequest, idSeller, status,idPickup);
    }
     @GetMapping("retrieveRequestDeliveryAgencycBySeller")
     public List<Request> retrieveRequestBySeller() {
         return ri.retrieveRequestBySeller();
     }
     @GetMapping("retrieveRequestByPickup")
     public List<Request> retrieveRequestByPickup(Long idPickup) {
         return ri.retrieveRequestByPickup(idPickup);
     }
    }
