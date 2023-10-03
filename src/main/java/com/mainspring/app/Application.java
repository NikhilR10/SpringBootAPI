package com.mainspring.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainspring.app.model.CycloneDx;
import com.mainspring.app.model.CycloneDx.Metadata.Authors;
import com.mainspring.app.model.CycloneDx.Metadata.Tools;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		
//		String sbom = "";
//		ObjectMapper mapper = new ObjectMapper();
//		CycloneDx cyclonedx = mapper.readValue(sbom, CycloneDx.class);
//		mapper.setSerializationInclusion(Include.NON_NULL);
//		Authors authors = new Authors();
//		authors.setName("Securin Inc.");
//		authors.setEmail("securin.io");
//		cyclonedx.getMetadata().setAuthors(List.of(authors));
//		Tools tools = cyclonedx.getMetadata().getTools().get(0);
//		tools.setName("Securin-SBOM");
//		cyclonedx.getMetadata().setTools(List.of(tools));
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cyclonedx));
	
	}

}