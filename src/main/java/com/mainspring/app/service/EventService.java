package com.mainspring.app.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainspring.app.model.Event;
import com.mainspring.app.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepo;
	
	@Value("#{'${scan.type.sast}'.split(',')}") 
	private List<String> sast;
	
	@Value("#{'${scan.type.sca}'.split(',')}")
	private List<String> sca;
	
	@Value("#{'${scan.type.iac}'.split(',')}")
	private List<String> iac;
	
	public ResponseEntity<Event> createEvent(Event event){
		if(event.getType().equalsIgnoreCase("PushEvent") || 
				event.getType().equalsIgnoreCase("ReleaseEvent") || 
				event.getType().equalsIgnoreCase("WatchEvent")) {
			eventRepo.save(event);
			Event responseEvent = eventRepo.findByRepoIdAndType(event.getRepoId(), event.getType());
			return new ResponseEntity<>(responseEvent, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<List<Event>> fetchEvents(){
		List<Event> events = eventRepo.findAll();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Event>> fetchByRepoId(Integer repoId) {
		List<Event> events = eventRepo.findByRepoId(repoId);
		return new ResponseEntity<>(events, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Event>> fetchByUserId(Integer userId) {
		List<Event> events = eventRepo.findByActorId(userId);
		return new ResponseEntity<>(events, HttpStatus.OK);
	}
	
	public ResponseEntity<Event> fetchById(Integer id){
		Optional<Event> event = eventRepo.findById(id);
		List<Event> events = new ArrayList<>();
		event.ifPresent(events::add);
		return new ResponseEntity<Event>(events.get(0), HttpStatus.OK);
	}
	
	public void test() throws Exception {
		Collection<Event> events = new ArrayList<>();
		Event event = new Event();
		event.setActorId(1);
		event.setId(1);
		event.setIsPublic(true);
		event.setRepoId(1);
		event.setType("java/openJdk");
		Event event2 = new Event();
		event2.setActorId(2);
		event2.setId(2);
		event2.setIsPublic(true);
		event2.setRepoId(2);
		event2.setType("java/closedJdk");
		Event event3 = new Event();
		event3.setActorId(3);
		event3.setId(3);
		event3.setIsPublic(true);
		event3.setRepoId(3);
		event3.setType("java/PushJdk");
		Event event4 = new Event();
		event4.setActorId(4);
		event4.setId(4);
		event4.setIsPublic(true);
		event4.setRepoId(4);
		event4.setType("java/openJdk");
		events.add(event);
		events.add(event2);
		events.add(event3);
		events.add(event4);
		Map<Object, List<Event>> testMap = events.stream()
				.collect(Collectors.groupingBy(type -> type.getType()));
		ObjectMapper mapper = new ObjectMapper();
		for(Map.Entry<Object, List<Event>> entry : testMap.entrySet()) {
			System.out.println(entry.getKey().toString());
			System.out.println(mapper.writeValueAsString(entry));
		}
		
	}
	
	public ResponseEntity<Resource> printScanners() throws Exception {
		File file = new File("C:\\Users\\Nikhil\\Downloads\\SBOM.json");
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-control", "No-Cache");
		headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM.toString());
		headers.add("Content-Disposition", "attachment;filename=SBOM.json");
		return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
	}

}
