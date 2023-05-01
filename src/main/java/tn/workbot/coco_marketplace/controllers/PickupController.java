package tn.workbot.coco_marketplace.controllers;

import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.Api.*;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.RequestStatus;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;
import tn.workbot.coco_marketplace.services.interfaces.PickupIService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController

@RequestMapping("Pickup")
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('SELLER') || hasAuthority('DELIVERYAGENCY') || hasAuthority('DELIVERYMEN')")
public class PickupController {
    @Autowired
    PickupIService pis;
    @Autowired
    PdfPickup pdf;
    @Autowired
    private OpenWeatherMapClient weatherClient;
    @Autowired
    DeliveryPreduction dp;
    @Autowired
    ScraperEssence sce;
    @Autowired
    ScraperTOpProduct scraper;


    @PostMapping("addPickup")
    public Pickup addPickup(@RequestBody Pickup pickup) {
        return pis.addPickup(pickup);
    }

    @DeleteMapping("RemovePickup")
    public void removePickup(@RequestParam Long id) {
        pis.removePickup(id);
    }

    @GetMapping("RetrievePickup")
    public Pickup RetrievePickup(@RequestParam Long id) {
        return pis.RetrievePickup(id);
    }

    @GetMapping("RetriveAllPickup")
    public List<Pickup> RetrievePickups() {
        return pis.RetrievePickups();
    }

    @PutMapping("UpdatePickup")
    public Pickup updatePickup(@RequestBody Pickup pickup, @RequestParam Long idPikup) {
        return pis.updatePickup(pickup, idPikup);
    }

    @PostMapping("AssignPickupByOder")
    public Pickup AssignPickupByOder(@RequestBody Pickup pickup, @RequestParam Long id) {
        return pis.AssignPickupByOder(pickup, id);
    }

    @GetMapping("RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen")
    public List<Pickup> RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen(@RequestParam Long id) {
        return pis.RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen(id);
    }

    @GetMapping("RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer")
    public List<Pickup> RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer() {
        return pis.RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer();
    }

    @GetMapping("RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat")
    public List<Pickup> RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat() {
        return pis.RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat();
    }

    @PostMapping("AssignPickupByStoreAndOrder")
    public Pickup AssignPickupByStoreAndOrder(@RequestBody Pickup pickup, @RequestParam Long id, @RequestParam Long IdSotre) {
        return pis.AssignPickupByStoreAndOrder(pickup, id, IdSotre);
    }

    @PutMapping("ModifyStatusOfPickupByDelivery")
    public Pickup ModifyStatusOfPickupByDelivery(@RequestParam String Status, @RequestParam Long idPickup) {
        return pis.ModifyStatusOfPickupByDelivery(Status, idPickup);
    }

    @PostMapping("calculateDeliveryTime")
    public Duration calculateDeliveryTime(@RequestParam Long idPickup) throws IOException, InterruptedException, ApiException {
        return pis.calculateDeliveryTime(idPickup);
    }

    @PostMapping("hetcountt")
    public int test(@RequestParam Long id) {
        return pis.test(id);
    }

    @GetMapping("trakingbybuyer")
    public Pickup trakingbybuyer(@RequestParam String codePickup) {
        return pis.trakingbybuyer(codePickup);
    }

    @GetMapping("trakingbyseller")
    public Pickup trakingbyseller(@RequestParam String codePickup) {
        return pis.trakingbyseller(codePickup);
    }

    @GetMapping("retrievePickupByDeliveryMenFreelancer")
    public List<Pickup> retrievePickupByDeliveryMenFreelancer() {
        return pis.retrievePickupByDeliveryMenFreelancer();
    }


    @GetMapping("retrievePickupByAgence")
    public List<Pickup> retrievePickupByAgence() {
        return pis.retrievePickupByAgence();
    }

    @GetMapping("retrievePickupByBranch")
    public List<Pickup> retrievePickupByBranch(Long idbranch) {
        return pis.retrievePickupByBranch(idbranch);
    }

    @GetMapping("retrieveOrderByseller")
    public List<Order> retrieveOrderByseller(@RequestParam Long idStore) {
        return pis.retrieveOrderByseller(idStore);
    }

    @GetMapping("retrievePickupBysellerAttent")
    public List<Pickup> retrievePickupBysellerAttent() {
        return pis.retrievePickupBysellerAttent();
    }

    @GetMapping("countPickupSellerPendingToday")
    public int countPickupSellerPendingToday() {
        return pis.countPickupSellerPendingToday();
    }

    @GetMapping("countPickupSelleronTheWayToday")
    public int countPickupSelleronTheWayToday() {
        return pis.countPickupSelleronTheWayToday();
    }

    @GetMapping("countPickupSellerDeliveredToday")
    public int countPickupSellerDeliveredToday() {
        return pis.countPickupSellerDeliveredToday();
    }

    @GetMapping("countPickupSellerReturnToday")
    public int countPickupSellerReturnToday() {
        return pis.countPickupSellerReturnToday();
    }

    @GetMapping("countPickupSellerRefundedToday")
    public int countPickupSellerRefundedToday() {
        return pis.countPickupSellerRefundedToday();
    }

    @GetMapping("countPickupDeliveryManFreelancerPendingToday")
    public int countPickupDeliveryManFreelancerPendingToday() {
        return pis.countPickupDeliveryManFreelancerPendingToday();
    }

    @GetMapping("countPickupAgencyToday")
    public int countPickupAgencyToday() {
        return pis.countPickupAgencyToday();
    }

    @GetMapping("countRequestRejectedDeliveryManFreelancerToday")
    public int countRequestRejectedDeliveryManFreelancerToday() {
        return pis.countRequestRejectedDeliveryManFreelancerToday();
    }

    @GetMapping("countRequestApprovedDeliveryManFreelancerToday")
    public int countRequestApprovedDeliveryManFreelancerToday() {
        return pis.countRequestApprovedDeliveryManFreelancerToday();
    }

    @GetMapping("countRequestRejectedAgencyToday")
    public int countRequestRejectedAgencyToday() {
        return pis.countRequestRejectedAgencyToday();
    }

    @GetMapping("countRequestApprovedAgencyToday")
    public int countRequestApprovedAgencyToday() {
        return pis.countRequestApprovedAgencyToday();
    }

    @GetMapping("SumPricePickupDeliveredByFreelancerToday")
    public Float SumPricePickupDeliveredByFreelancerToday() {
        return pis.SumPricePickupDeliveredByFreelancerToday();
    }

    @GetMapping("SumPricePickupDeliveredByAgencyToday")
    public Float SumPricePickupDeliveredByAgencyToday() {
        return pis.SumPricePickupDeliveredByAgencyToday();
    }

    ////////////Pdf
    @GetMapping("pdfPick")
    public void pdfPick(HttpServletResponse response, @RequestParam Long idPickup) throws IOException {
        pdf.pdfPickup(response, idPickup);
    }

    @GetMapping("RetrieveProductByPickup")
    public List<Product> RetrieveProductByPickup(@RequestParam Long idPickup) {
        return pis.RetrieveProductByPickup(idPickup);
    }

    @GetMapping("/weather/{city}")
    public double getWeather(@PathVariable String city) {
        return weatherClient.getWeather(city);
    }

    //////////////stat Administrator
    @GetMapping("countAgencyAdministrator")
    public int countAgencyAdministrator() {
        return pis.countAgencyAdministrator();
    }

    @GetMapping("countDeliveryFreelancerAdministrator")
    public int countDeliveryFreelancerAdministrator() {
        return pis.countDeliveryFreelancerAdministrator();
    }

    @GetMapping("countPickupDeliveredTodayAdministrator")
    public int countPickupDeliveredTodayAdministrator() {
        return pis.countPickupDeliveredTodayAdministrator();
    }

    @GetMapping("countOfPickupOnTheWayTodayAdministrator")
    public int countOfPickupOnTheWayTodayAdministrator() {
        return pis.countOfPickupOnTheWayTodayAdministrator();
    }

    @GetMapping("countOfPickupReturnedTodayAdministrato")
    public int countOfPickupReturnedTodayAdministrator() {
        return pis.countOfPickupReturnedTodayAdministrator();
    }

    @GetMapping("countOfPickupDeliveredweekAdministrator")
    public int countOfPickupDeliveredweekAdministrator() {
        return pis.countOfPickupDeliveredweekAdministrator();
    }

    @GetMapping("countOfPickupOnTheWayweekAdministrator")
    public int countOfPickupOnTheWayweekAdministrator() {
        return pis.countOfPickupOnTheWayweekAdministrator();
    }

    @GetMapping("countOfPickupReturnedweekAdministrator")
    public int countOfPickupReturnedweekAdministrator() {
        return pis.countOfPickupReturnedweekAdministrator();
    }

    @GetMapping("sumOfPickupDeliveredTodayAdministrator")
    public Float sumOfPickupDeliveredTodayAdministrator() {
        return pis.sumOfPickupDeliveredTodayAdministrator();
    }

    @GetMapping("sumOfPickupOnTheWayTodayAdministrator")
    public Float sumOfPickupOnTheWayTodayAdministrator() {
        return pis.sumOfPickupOnTheWayTodayAdministrator();
    }

    @GetMapping("sumOfPickupReturnedTodayAdministrator")
    public Float sumOfPickupReturnedTodayAdministrator() {
        return pis.sumOfPickupReturnedTodayAdministrator();
    }

    @GetMapping("sumOfPickupDeliveredweekAdministrator")
    public Float sumOfPickupDeliveredweekAdministrator() {
        return pis.sumOfPickupDeliveredweekAdministrator();
    }

    @GetMapping("sumOfPickupOnTheWayweekAdministrator")
    public Float sumOfPickupOnTheWayweekAdministrator() {
        return pis.sumOfPickupOnTheWayweekAdministrator();
    }

    @GetMapping("sumOfPickupReturnedweekAdministrator")
    public Float sumOfPickupReturnedweekAdministrator() {
        return pis.sumOfPickupReturnedweekAdministrator();
    }


    //////////////calculer co2 and update in user entity
    @PostMapping("UpdateTheCO2ConsoFreelancer")
    public User UpdateTheCO2ConsoFreelancer() throws IOException, InterruptedException, ApiException {
        return pis.UpdateTheCO2ConsoFreelancer();
    }

    /////////send mail if user fet el limite
    @GetMapping("LimiteCo2")
    public double LimiteCo2() throws IOException, InterruptedException, ApiException {
        return pis.LimiteCo2();
    }

    ///////////predict som
    @GetMapping("/predict/requedt")
    public ResponseEntity<Double> predict(@RequestParam int r) {
        // Predict the delivery sum using the linear regression model
        double predictedTime = dp.predict((float) r);
        return ResponseEntity.ok(predictedTime);
    }

    @GetMapping("/predictrequest")
    public ResponseEntity<String> addDelivery() {
        dp.addDelivery();
        return ResponseEntity.ok("Delivery added successfully");
    }

    //////////predict co2
    @GetMapping("/predictco2")
    public ResponseEntity<Double> predictco2(@RequestParam int gearage) {
        // Predict the CO2 using the linear regression model
        double predictedTime = dp.predictco2((float) gearage);
        return ResponseEntity.ok(predictedTime);
    }

    @GetMapping("/predictc")
    public ResponseEntity<String> addUser() {
        dp.addUser();
        return ResponseEntity.ok("user added successfully");
    }

    @GetMapping("RetrievePickupAgencyByRequestWithStatusRequestApproved")
    public List<Pickup> RetrievePickupAgencyByRequestWithStatusRequestApproved() {
        return pis.RetrievePickupAgencyByRequestWithStatusRequestApproved();
    }

    @GetMapping("RetrievePickupFreelancerByRequestWithStatusRequestApproved")
    public List<Pickup> RetrievePickupFreelancerByRequestWithStatusRequestApproved() {
        return pis.RetrievePickupFreelancerByRequestWithStatusRequestApproved();
    }

    @GetMapping("/scrape")
    public String scrapePage(@RequestParam String url) throws Exception {
        return sce.scrapePage(url);
    }

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<File> exportToXsl(@RequestParam String categoryUrl, @RequestParam String filename) {
        try {
            Map<String, Map<String, String>> products = scraper.getProducts(categoryUrl);
            scraper.exportToXsl(products, filename);
            File file = new File(filename);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("RetrieveStoreOfUser")
    public Set<Store> RetrieveStoreOfUser() {
        return pis.RetrieveStoreOfUser();
    }

    @GetMapping("GetOrderById")
    public Order GetOrderById(@RequestParam Long IdOrder) {
        return pis.GetOrderById(IdOrder);
    }

    @GetMapping("GetShippingByOrder")
    public Shipping getShippingByOrder(@RequestParam Long IdOrder) {
        return pis.getShippingByOrder(IdOrder);
    }

    @GetMapping("GetBuyerByOrder")
    public User getBuyerByOrder(@RequestParam Long IdOrder) {
        return pis.getBuyerByOrder(IdOrder);
    }

    @GetMapping("GetOrderByPickupId")
    public Order getOrderByPickupId(@RequestParam Long idPickup) {
        return pis.getOrderByPickupId(idPickup);
    }

    @GetMapping("GetShippingByPickupId")
    public Shipping getShippingByPickupId(@RequestParam Long idPickup) {
        return pis.getShippingByPickupId(idPickup);
    }

    @GetMapping("GetBuyerByPickupId")
    public User getBuyerByPickupId(@RequestParam Long idPickup) {
        return pis.getBuyerByPickupId(idPickup);
    }

    @GetMapping("getUserNOw")
    public User getUserNOw() {
        return pis.getUserNOw();
    }

    @GetMapping("countOrderBySellerNoPickup")
    public int countOrderBySellerNoPickup(@RequestParam Long idStore) {
        return pis.countOrderBySellerNoPickup(idStore);
    }

    @GetMapping("getProductSumANdListOfpRODUCTByOrder")
    public ResponseEntity<Map<Float, List<Product>>> getProduct(@RequestParam Long idOrder, @RequestParam Long idStore) {
        return pis.getProduct(idOrder, idStore);
    }

    @GetMapping("getListProductOfOrder")

    public List<Product> getListProductOfOrder(@RequestParam Long idOrder, @RequestParam Long idStore) {
        return pis.getListProductOfOrder(idOrder, idStore);
    }

    @GetMapping("getSumPriceProductOfOrder")
    public Float getSumPriceProductOfOrder(@RequestParam Long idOrder, @RequestParam Long idStore) {
        return pis.getSumPriceProductOfOrder(idOrder, idStore);
    }

    @GetMapping("getAllProductQuantity")
    public List<ProductQuantity> getAllProductQuantity() {
        return pis.getAllProductQuantity();
    }
    //stat Agency


    @GetMapping("countPickupDeliveredForAgency")
    public int countPickupDeliveredForAgency() {
        return pis.countPickupDeliveredForAgency();
    }

    @GetMapping("countPickupReturnedForAgency")
    public int countPickupReturnedForAgency() {
        return pis.countPickupReturnedForAgency();
    }

    @GetMapping("countPickupOnTheWayForAgency")
    public int countPickupOnTheWayForAgency() {
        return pis.countPickupOnTheWayForAgency();
    }

    @GetMapping("countPickupRefundedForAgency")
    public int countPickupRefundedForAgency() {
        return pis.countPickupRefundedForAgency();
    }

    @GetMapping("countPickupAssignedForAgency")
    public int countPickupAssignedForAgency() {
        return pis.countPickupAssignedForAgency();
    }

    @GetMapping("countPickupTakedForAgency")
    public int countPickupTakedForAgency() {
        return pis.countPickupTakedForAgency();
    }

    //stat freelancer
    @GetMapping("countPickupDeliveredForfreelancer")
    public int countPickupDeliveredForfreelancer() {
        return pis.countPickupDeliveredForfreelancer();
    }

    @GetMapping("countPickupReturnedForfreelancer")
    public int countPickupReturnedForfreelancer() {
        return pis.countPickupReturnedForfreelancer();
    }

    @GetMapping("countPickupOnTheWayForfreelancer")
    public int countPickupOnTheWayForfreelancer() {
        return pis.countPickupOnTheWayForfreelancer();
    }

    @GetMapping("countPickupRefundedForfreelancer")
    public int countPickupRefundedForfreelancer() {
        return pis.countPickupRefundedForfreelancer();
    }

    @GetMapping("countPickupAssignedForFreelancer")
    public int countPickupAssignedForFreelancer() {
        return pis.countPickupAssignedForFreelancer();
    }

    @GetMapping("countPickupTakedForFreelancer")
    public int countPickupTakedForFreelancer() {
        return pis.countPickupTakedForFreelancer();
    }

    ////
    @GetMapping("RetrievePickupInProgress")
    public List<Pickup> RetrievePickupInProgress() {
        return pis.RetrievePickupInProgress();
    }

    @GetMapping("countProductQuantityInOrderProduct")
    public int countProductQuantityInOrderProduct(@RequestParam Long idOrder, @RequestParam Long idProduct) {
        return pis.countProductQuantityInOrderProduct(idOrder, idProduct);
    }

    @GetMapping("countPickupAssignedSeller")
    public int countPickupAssignedSeller() {
        return pis.countPickupAssignedSeller();
    }

    @GetMapping("countPickupTakedSeller")
    public int countPickupTakedSeller() {
        return pis.countPickupTakedSeller();
    }

    @GetMapping("getStoreByPickup")
    public Store getStoreByPickup(@RequestParam Long idPickup) {
        return pis.getStoreByPickup(idPickup);
    }

    @GetMapping("SumOfPricePickupDeliveredToday")
    public Double SumOfPricePickupDeliveredToday() {
        return pis.SumOfPricePickupDeliveredToday();
    }

    @GetMapping("getNumberOfPickupByStatus")
    public Map<StatusPickupSeller, Integer> getNumberOfPickupByStatus() {
        return pis.getNumberOfPickupByStatus();
    }

    @GetMapping("getNumberOfPickupByStatusByMonthAndYearAndAll")
    public Map<StatusPickupSeller, Integer> getNumberOfPickupByStatusByMonthAndYearAndAll() {
        return pis.getNumberOfPickupByStatusByMonthAndYearAndAll();
    }

    @GetMapping("AllCo2User")
    public Double AllCo2User() {
        return pis.AllCo2User();
    }

    @GetMapping("getNumberPickupsInMonth")
    public Map<String, Integer> getNumberPickupsInMonth() {
        return pis.getNumberPickupsInMonth();
    }

    @GetMapping("getNumberRequestsInMonth")
    public Map<String, Integer> getNumberRequestsInMonth() {
        return pis.getNumberRequestsInMonth();
    }

    @GetMapping("RetrieveAllPickupsOfUser")
    public List<Pickup> RetrieveAllPickupsOfUser() {
        return pis.RetrieveAllPickupsOfUser();
    }

    @GetMapping("RetrieveAllPickupsOfSeller")
    public List<Pickup> RetrieveAllPickupsOfSeller() {
        return pis.RetrieveAllPickupsOfSeller();
    }
    @GetMapping("retrieveTheFreelancerOfPickup")
    public User retrieveTheFreelancerOfPickup(@RequestParam Long idPickup) {
        return pis.retrieveTheFreelancerOfPickup(idPickup);
    }
}

