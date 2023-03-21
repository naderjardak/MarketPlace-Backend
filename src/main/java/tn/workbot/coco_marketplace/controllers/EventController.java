package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Event;
import tn.workbot.coco_marketplace.entities.KeyWords;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.services.EventService;
import tn.workbot.coco_marketplace.services.interfaces.EventInterface;

import java.util.List;

@RestController

@RequestMapping("/Event")
public class EventController {

    @Autowired
    EventInterface eventInterface;

    @PostMapping("addEvent")
    @PreAuthorize("hasAuthority('MODERATOR') || hasAuthority('ADMIN')")
    public void addEvent(@RequestBody Event event){eventInterface.addEvent(event);}

    @GetMapping("findEventByTitle")
    public Event findEventByTitle(@RequestParam String title){return eventInterface.findEventByTitle(title);}

    @GetMapping("findEventById")
    public Event findEventById(@RequestParam Long id){return eventInterface.findEventById(id);}

    @GetMapping("displayRunningEvents")
    public List<Event> displayRunningEvents(){return eventInterface.displayRunningEvents();}

    @GetMapping("displayAllEvents")
    public List<Event> displayAllEvents(){return eventInterface.displayAllEvents();}

    @PostMapping("updateEvent")
    public void updateEvent(@RequestBody Event event){eventInterface.updateEvent(event);}

    @PostMapping("addKeywordToEvent")
    public void addKeyWordToEvent(@RequestParam Long id,@RequestBody KeyWords keyWords){eventInterface.addKeyWordToEvent(id,keyWords);}

    @DeleteMapping("deleteEvent")
    public void deleteEvent(@RequestParam Long id){eventInterface.deleteEvent(id);}

    @GetMapping("GetProductsForEvent")
    public List<Product> displayProductForEvent(@RequestParam Long id){return eventInterface.displayProductForEvent(id);}

}
