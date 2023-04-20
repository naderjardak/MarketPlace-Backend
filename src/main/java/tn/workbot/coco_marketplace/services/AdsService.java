package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Ads;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.repositories.AdsRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.services.interfaces.AdsInterface;

import java.util.List;

@Service
public class AdsService implements AdsInterface {
    @Autowired
    AdsRepository ar;
    @Autowired
    ProductRepository pr;
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
}
