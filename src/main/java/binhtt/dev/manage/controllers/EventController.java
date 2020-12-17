package binhtt.dev.manage.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("v1/api")
public class EventController {

    //get all event
    @GetMapping("/events")
    public ResponseEntity getAllEvents(){
        return null;
    }

    //get event by id
    @GetMapping("/events/{eventId}")
    public ResponseEntity getEvent(@PathVariable("eventId") String eventId){
        return null;
    }

    //

}
