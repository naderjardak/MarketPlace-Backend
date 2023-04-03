package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Shipping;

import java.util.List;

public interface ShippingInterface {

    public List<Shipping> getAllShippingsByUserID(Long idU); // Returns a list of all shippings
    public Shipping getShippingById(Long id); // Returns a shipping with the given ID
    public Shipping createShipping(Shipping shipping); // Creates a new shipping with the provided details
    public Shipping updateShipping(Long id, Shipping shipping); // Updates an existing shipping with the provided details
    public Boolean deleteShipping(Long id); // Deletes a shipping with the given ID
    public List<Shipping> getAllUserShippings();
}
