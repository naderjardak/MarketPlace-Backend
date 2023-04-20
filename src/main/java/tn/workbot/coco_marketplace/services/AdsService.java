package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.Ads;
import tn.workbot.coco_marketplace.entities.LastVued;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.ObjectiveType;
import tn.workbot.coco_marketplace.repositories.AdsRepository;
import tn.workbot.coco_marketplace.repositories.LastVuedRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.services.interfaces.AdsInterface;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsService implements AdsInterface {
    @Autowired
    AdsRepository ar;
    @Autowired
    ProductRepository pr;
    @Autowired
    SessionService ss;
    @Autowired
    LastVuedRepository lvr;
    @Override
    public Ads assignAdsToProduct(Ads ads, Long idProduct) {
        Product product=pr.findById(idProduct).get();
        Ads ads1=ar.save(ads);
        ads1.setProduct(product);
        ads1.setEnabled(true);
        return ar.save(ads1);
    }

    @Override
    public Ads descativatedAds(Long idAds) {
        Ads ads=ar.findById(idAds).get();
        ads.setEnabled(false);
        ads.setProduct(ads.getProduct());
        return ar.save(ads);
    }

    @Override
    public List<Ads> retrieveAllAds() {
        return (List<Ads>) ar.findAll();
    }

    @Override
    public Ads retrieveAdsById(Long idAds) {
        Ads ads=ar.findById(idAds).get();
        return ads;
    }

    @Override
    public List<Ads> retrieveAdsTOInterestedBuyerObSales() {
        User u=ss.getUserBySession();
        List<Ads> adsList= (List<Ads>) ar.findAll();
        List<LastVued> lastVuedList=lvr.findAll();
        List<Ads> productList=new ArrayList<>();
        for (Ads a:adsList) {
            for (LastVued lv:lastVuedList) {
                if((lv.getUser().getId().equals(u.getId()))&&(a.getGender().equals(u.getGender()))&&(a.getEnabled().equals(true))&&(a.getObjectiveType().equals(ObjectiveType.SALES))){
                    if(lv.getProductVued().getProductCategory().getName().equals(a.getProduct().getProductCategory().getName())){
                          productList.add(a);
                    }
                }
            }
        }
        return productList;
    }

    @Override
    public List<Ads> retrieveAdsTOInterestedBuyerObTraffic() {
        User u=ss.getUserBySession();
        List<Ads> adsList= (List<Ads>) ar.findAll();
        List<LastVued> lastVuedList=lvr.findAll();
        List<Ads> productList=new ArrayList<>();
        for (Ads a:adsList) {
            for (LastVued lv:lastVuedList) {
                if((lv.getUser().getId().equals(u.getId()))&&(a.getGender().equals(u.getGender()))&&(a.getEnabled().equals(true))&&(a.getObjectiveType().equals(ObjectiveType.SALES))){
                    if(lv.getProductVued().getProductCategory().getName().equals(a.getProduct().getProductCategory().getName())){
                        productList.add(a);
                    }
                }
            }
        }
        return productList;
    }
}
