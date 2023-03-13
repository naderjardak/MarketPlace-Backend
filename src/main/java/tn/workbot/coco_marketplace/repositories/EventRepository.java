package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
Event findByTitle(String title);
}
