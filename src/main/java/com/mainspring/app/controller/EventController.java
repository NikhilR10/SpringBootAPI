package com.mainspring.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainspring.app.model.Event;
import com.mainspring.app.service.EventService;

@RestController
public class EventController {
	
	@Autowired
	private EventService eventService;

	@PostMapping("/events")
	public ResponseEntity<Event> createEvent(@RequestBody Event event){
		return eventService.createEvent(event);
	}
	
	@GetMapping("/events")
	public ResponseEntity<List<Event>> fetchEvents(){
		return eventService.fetchEvents();
	}
	
	@GetMapping("/repos/{repoId}/events")
	public ResponseEntity<List<Event>> fetchByRepoId(@PathVariable Integer repoId){
		return eventService.fetchByRepoId(repoId);
	}
	
	@GetMapping("/repos/{userId}/events")
	public ResponseEntity<List<Event>> fetchByUserId(@PathVariable Integer userId){
		return eventService.fetchByUserId(userId);
	}
	
	@GetMapping("/events/{eventId}")
	public ResponseEntity<Event> fetchById(@PathVariable Integer eventId){
		return eventService.fetchById(eventId);
	}
	
	@GetMapping("/test")
	public String test() throws Exception {
		eventService.test();
		return "SUCCESS";
	}
	
	@GetMapping("/printScanners")
	public ResponseEntity<Resource> printScanners() throws Exception {
		return eventService.printScanners();
	}
	
}
