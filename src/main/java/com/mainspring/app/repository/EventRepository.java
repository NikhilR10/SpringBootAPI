package com.mainspring.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mainspring.app.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	
	Event findByRepoIdAndType(Integer repoId, String type);
	
	List<Event> findByRepoId(Integer repoId);
	
	List<Event> findByActorId(Integer userId);

}
