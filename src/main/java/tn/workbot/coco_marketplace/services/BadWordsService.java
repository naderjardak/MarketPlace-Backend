package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.BadWords;
import tn.workbot.coco_marketplace.repositories.BadWordsRepository;
import tn.workbot.coco_marketplace.services.interfaces.BadWordsInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BadWordsService implements BadWordsInterface {

    @Autowired
    private BadWordsRepository badWordRepository;

    @Override
    public void addNewBadWord(BadWords badWords) {
        badWordRepository.save(badWords);
    }

    @Override
    public String hideBadWords(String comment) {


            List<String> badWords = new ArrayList<>();


            Iterable<BadWords> badWordsIterable = badWordRepository.findAll();
            for (BadWords badWord : badWordsIterable) {
                badWords.add(badWord.getWord());
            }

            
            for (String badWord : badWords) {
                comment = comment.toLowerCase(Locale.ROOT).replaceAll("(?i)" + badWord, "*".repeat(badWord.length()));
            }

            return comment;
        }


    }
