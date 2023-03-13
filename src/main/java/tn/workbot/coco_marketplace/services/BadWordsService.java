package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.BadWords;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.BadWordsRepository;
import tn.workbot.coco_marketplace.services.interfaces.BadWordsInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BadWordsService implements BadWordsInterface {

    @Autowired
    private BadWordsRepository badWordRepository;

    @Autowired
    private UserService userService;

    @Override
    public void addNewBadWord(BadWords badWords) {
        badWordRepository.save(badWords);
    }

    @Override
    public String hideBadWords(String comment) {


        String[] words = comment.split(" ");
        List<BadWords> badWordsList = badWordRepository.findAll();
        String modifiedComment = comment;
        boolean shouldBanUser = false;

        for (String word : words) {
            for (BadWords badWord : badWordsList) {
                if (word.toLowerCase().contains(badWord.getWord().toLowerCase())) {
                    modifiedComment = modifiedComment.replace(word, "*".repeat(word.length()));
                    shouldBanUser = true;
                }
            }
        }

        if (shouldBanUser) {
            User user = userService.GetById(1L);
            user.setBanned(true);
            userService.update(user);
        }

        return modifiedComment;
    }


    }
