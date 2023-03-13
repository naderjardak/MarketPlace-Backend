package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Event;
import tn.workbot.coco_marketplace.entities.KeyWords;
import tn.workbot.coco_marketplace.entities.Product;

import java.util.List;

public interface EventInterface {
    public void addEvent(Event event);
    public Event findEventByTitle(String title);
    public Event findEventById(Long id);
    public List<Event> displayRunningEvents();
    public List<Event> displayAllEvents();
    public void updateEvent(Event event);
    public void addKeyWordToEvent(Long id, KeyWords keyWords);
    public void deleteEvent(Long id);
    public List<Product> displayProductForEvent(Long id);

}
