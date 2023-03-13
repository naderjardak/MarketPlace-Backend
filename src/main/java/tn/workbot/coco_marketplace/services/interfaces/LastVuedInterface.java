package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Product;

import java.util.List;

public interface LastVuedInterface {

    public void createNewVueOrAddNb(Long id);
    public List<Product> afficherLastVued();
    public void autoClearLastVued();
}
