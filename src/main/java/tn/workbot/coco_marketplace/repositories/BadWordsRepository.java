package tn.workbot.coco_marketplace.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.BadWords;

@Repository
public interface BadWordsRepository extends JpaRepository<BadWords, Long> {
}
