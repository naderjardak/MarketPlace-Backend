package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.entities.Event;
import tn.workbot.coco_marketplace.entities.KeyWords;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.services.EventService;
import tn.workbot.coco_marketplace.services.interfaces.EventInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("Event")

public class EventController {

    @Autowired
    EventInterface eventInterface;

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @PostMapping("addEvent")
    //@PreAuthorize("hasAuthority('MODERATOR') || hasAuthority('ADMIN')")
    public void addEvent(@RequestBody Event event){eventInterface.addEvent(event);}

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @GetMapping("findEventByTitle")
    public Event findEventByTitle(@RequestParam String title){return eventInterface.findEventByTitle(title);}

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @GetMapping("findEventById")
    public Event findEventById(@RequestParam Long id){return eventInterface.findEventById(id);}

    @GetMapping("displayRunningEvents")
    public List<Event> displayRunningEvents(){return eventInterface.displayRunningEvents();}

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("displayAllEvents")
    public List<Event> displayAllEvents(){return eventInterface.displayAllEvents();}

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @PostMapping("updateEvent")
    public void updateEvent(@RequestBody Event event){eventInterface.updateEvent(event);}

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @PostMapping("addKeywordToEvent")
    public void addKeyWordToEvent(@RequestParam Long id,@RequestBody KeyWords keyWords){eventInterface.addKeyWordToEvent(id,keyWords);}

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @DeleteMapping("deleteEvent")
    public void deleteEvent(@RequestParam Long id){eventInterface.deleteEvent(id);}

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        eventInterface.storeFile(file);
    }
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODIRATOR')")
    @DeleteMapping("deleteKeywordFromEvent")
    public void deleteKeywordFromEvent(@RequestParam Long eventId,@RequestParam Long keywordId){eventInterface.deleteKeywordFromEvent(eventId,keywordId);}

    public List<Product> displayProductForEvent(@RequestParam Long id){return eventInterface.displayProductForEvent(id);}

}
