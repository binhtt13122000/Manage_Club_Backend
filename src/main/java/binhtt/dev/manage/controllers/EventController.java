package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/v1/api")
public class EventController {

    @Operation(description = "Get All Events", responses = {
            @ApiResponse(
                    description = "Get All Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Event.class))
                    )
            ),
            @ApiResponse(
                    description = "Access denied!",
                    responseCode = "403",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Access denied!",
                                    value = "Access denied!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
    })
    @GetMapping("/events")
    public ResponseEntity getAllEvents(
            @RequestParam Optional<String> eventName
            ){
        return null;
    }

    @GetMapping("/events/public")
    public ResponseEntity getPublicEvents(
            @RequestParam Optional<String> eventName
    ){
        return null;
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity getEvent(@PathVariable("eventId") String eventId){
        return null;
    }

    @PostMapping("/events")
    public ResponseEntity addEvent(@Valid @RequestBody Event event){
        return null;
    }

    @PutMapping("/events/{eventId}/update")
    public ResponseEntity updateEvent(@PathVariable("eventId") String eventId,@Valid @RequestBody Event event){
        return null;
    }

    @PutMapping("/events/{eventId}/delete")
    public ResponseEntity banEvent(@PathVariable String eventId){
        return null;
    }



}
