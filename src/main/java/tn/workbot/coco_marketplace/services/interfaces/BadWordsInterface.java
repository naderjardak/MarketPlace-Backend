package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.BadWords;

public interface BadWordsInterface {

    public void addNewBadWord(BadWords badWords);
    public String hideBadWords(String comment);
}
