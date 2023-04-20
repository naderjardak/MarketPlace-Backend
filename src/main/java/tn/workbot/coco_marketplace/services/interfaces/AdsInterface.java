package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Ads;

import java.util.List;

public interface AdsInterface {
    public Ads assignAdsToProduct(Ads ads, Long idProduct);

    public Ads descativatedAds(Long idAds);

    public List<Ads> retrieveAllAds();

    public Ads retrieveAdsById(Long idAds);
}
