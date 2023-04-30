package tn.workbot.coco_marketplace.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.entities.ClaimSav;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavStatusType;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ClaimSavInterface {
    List<ClaimSav> getAllClaims();
    ClaimSav getClaimById(Long id);
    void addClaim(ClaimSav claim,Long id );
    void updateClaim(ClaimSav claim);
    void deleteClaim(Long id);

    public void modifyClaimStatus(Long id, ClaimSavStatusType newStatus);

    public List<ClaimSav> getClaimsByTypeAndStatus(ClaimSavType type, ClaimSavStatusType status);

    public void storeFile(MultipartFile file) throws IOException;

    public List<Map<String,Integer>> statsClaim();
}
