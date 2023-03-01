package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.SupplierRequest;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.SupplierRequestStatus;
import tn.workbot.coco_marketplace.repositories.SupplierRequestRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.SupplierRequestInterface;

import java.util.List;

@Service
public class SupplierRequestService implements SupplierRequestInterface {

    @Autowired
    SupplierRequestRepository supplierRequestRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserrRepository userrRepository;

    @Override
    public SupplierRequest create(SupplierRequest s, Long productId) {
        User user=userrRepository.findById(1L).get();
        Product product = productService.getById(productId);
        s.setProduct(product);
        s.setRequestStatus(SupplierRequestStatus.WAITING_FOR_VALIDATION);
        s.setSupplier(user);
        return supplierRequestRepository.save(s);
    }

    @Override
    public SupplierRequest update(SupplierRequest s) {
        return supplierRequestRepository.save(s);
    }

    @Override
    public List<SupplierRequest> retrieveAll() {
        return supplierRequestRepository.findAll();
    }

    @Override
    public SupplierRequest getById(Long id) {
        if (supplierRequestRepository.findById(id).isPresent()) {
            return supplierRequestRepository.findById(id).get();
        }
        return new SupplierRequest();
    }

    @Override
    public void deleteById(Long id) throws Exception {
        SupplierRequest request=supplierRequestRepository.findById(id).get();
        if(request.getRequestStatus().equals(SupplierRequestStatus.WAITING_FOR_VALIDATION))
            supplierRequestRepository.delete(request);
        else throw new Exception("Error, you request is already treated, you can't delete it");
    }

    @Override
    public List<Product> retriveProductsOutOfStock() {
        return productService.retrieveAll().stream().filter(p->p.getQuantity()==0).toList();
    }

    @Override
    public List<SupplierRequest> retriveRequestsByProduct(Long idProduct) {
        return productService.getById(idProduct).getSupplierRequests()
                .stream().filter(s -> s.getRequestStatus().equals(SupplierRequestStatus.WAITING_FOR_VALIDATION)).toList();

    }

    @Override
    public void accpetRequestBySeller(Long supplierRequestId) {
        if (supplierRequestRepository.findById(supplierRequestId).isPresent()) {
            SupplierRequest supplierRequest = supplierRequestRepository.findById(supplierRequestId).get();
            List<SupplierRequest> supplierRequests = supplierRequest.getProduct().getSupplierRequests();
            for (SupplierRequest s : supplierRequests) {
                if (s.equals(supplierRequest)) {
                    s.setRequestStatus(SupplierRequestStatus.ACCEPTED);
                } else {
                    s.setRequestStatus(SupplierRequestStatus.REJECTED);
                }
                this.update(s);
            }
        }

    }
}
