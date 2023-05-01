package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Ads;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.enmus.BudgetType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AdsInterface {
    public String assignAdsToProduct(Ads ads, Long idProduct);

    public Ads descativatedAds(Long idAds);

    public List<Ads> retrieveAllAds();

    public Ads retrieveAdsById(Long idAds);
    public List<Ads> retrieveAdsTOInterestedBuyerObSales();
    public List<Ads> retrieveAdsTOInterestedBuyerObTraffic();
    public Integer retrieveHMAwRWithAds(float adsPoints, String startDate, String expiredDate, BudgetType budgetType);
    public List<Product> getProductByUserSess();
    public List<Ads> getMyAds();
    public void deleteAds(Long idAds);
}
