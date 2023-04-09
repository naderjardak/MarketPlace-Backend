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
@CrossOrigin(origins = "*")
@RequestMapping("/Event")
public class EventController {

    @Autowired
    EventInterface eventInterface;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("addEvent")
    //@PreAuthorize("hasAuthority('MODERATOR') || hasAuthority('ADMIN')")
    public void addEvent(@RequestBody Event event){eventInterface.addEvent(event);}

    @GetMapping("findEventByTitle")
    public Event findEventByTitle(@RequestParam String title){return eventInterface.findEventByTitle(title);}

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("findEventById")
    public Event findEventById(@RequestParam Long id){return eventInterface.findEventById(id);}

    @GetMapping("displayRunningEvents")
    public List<Event> displayRunningEvents(){return eventInterface.displayRunningEvents();}

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("displayAllEvents")
    public List<Event> displayAllEvents(){return eventInterface.displayAllEvents();}

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("updateEvent")
    public void updateEvent(@RequestBody Event event){eventInterface.updateEvent(event);}

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("addKeywordToEvent")
    public void addKeyWordToEvent(@RequestParam Long id,@RequestBody KeyWords keyWords){eventInterface.addKeyWordToEvent(id,keyWords);}

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("deleteEvent")
    public void deleteEvent(@RequestParam Long id){eventInterface.deleteEvent(id);}

    @GetMapping("GetProductsForEvent")
    public List<Product> displayProductForEvent(@RequestParam Long id){return eventInterface.displayProductForEvent(id);}

}
