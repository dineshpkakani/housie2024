package com.ecw.admin.controller;

import com.ecw.admin.dto.ResponseListObj;
import com.ecw.admin.entity.EventEntity;
import com.ecw.admin.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/event")
public class EventController {
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<ResponseListObj> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
      ResponseListObj responseListObj = eventService.findAllEvents(page,size);
        if(responseListObj.getTotalrecords()==0)
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(responseListObj, HttpStatus.OK);
    }
    @GetMapping(value = "/geteventname")
    public ResponseEntity<ResponseListObj> getAll( ) {
        ResponseListObj responseListObj = eventService.findAllEvents();

        return new ResponseEntity<>(responseListObj, HttpStatus.OK);
    }
    @GetMapping(value = "/get/name/{searchby}")
    public ResponseEntity<ResponseListObj> getByName(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @PathVariable String searchby) {

        ResponseListObj responseListObj= eventService.findByNameStartsWith(searchby,page,size);

        if(responseListObj.getTotalrecords()==0)
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(responseListObj, HttpStatus.OK);
    }
    @GetMapping(value = "/get/id/{id}")
    public ResponseEntity<ResponseListObj> getByEventId(@PathVariable long id) {
        ResponseListObj responseListObj= eventService.findByEventId(id);
        if(responseListObj.getLst().size()==0)
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(responseListObj, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveEvent(@RequestBody @Valid EventEntity e) {
        ResponseEntity<String> responseEntity=null;
        try {
               String dataSavedStatus= eventService.save(e);
                if("saved".equals(dataSavedStatus)) {
                    responseEntity= new ResponseEntity<>("{\"success\":\"Event saved successfully\"}", HttpStatus.CREATED);
                }
        }catch (Exception exp){
            responseEntity=new ResponseEntity<> ("{\"failed\":\"Event not saved \"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
           return responseEntity;
        }

    }
    @PutMapping
    public ResponseEntity<String> updateEvent(@RequestBody EventEntity e) {
        ResponseEntity<String> responseEntity=null;
        try {
            String dataSavedStatus= eventService.save(e);

            if("saved".equals(dataSavedStatus)) {
                responseEntity=new ResponseEntity<>("{\"success\":\"Event update successfully\"}", HttpStatus.CREATED);
            }
        }catch (Exception exp){
                responseEntity=new ResponseEntity<>("{\"failed\":\"Event updated failed\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            return responseEntity;
        }
    }

}
