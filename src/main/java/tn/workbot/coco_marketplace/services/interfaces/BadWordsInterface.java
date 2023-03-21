package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.BadWords;
import tn.workbot.coco_marketplace.entities.ClaimSav;

import java.util.List;

public interface BadWordsInterface {

    public void addNewBadWord(BadWords badWords);
    void updateBadWords(BadWords badWords);
    public String hideBadWords(String comment);

    public void deleteBadWords(Long id);

    public List<BadWords> getAllBadWords();
}
