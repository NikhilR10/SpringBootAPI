package com.mainspring.app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CycloneDx {
	
	private String bomFormat;
	private String specVersion;
	private String serialNumber;
	private Integer version;
	private Metadata metadata;
	private List<Components> components;
	private List<Dependencies> dependencies;
	private List<ExternalReferences> externalReferences;
	
	@Data
	public static class Metadata {
		private String timestamp;
		private List<Tools> tools;
		private List<Authors> authors;
		private Component component;
		
		@Data
		public static class Tools {
			private String vendor;
			private String name;
			private String version;
		}
		
		@Data
		public static class Authors {
			private String name;
			private String email;
		}
		
		@Data
		public static class Component {
			private String author;
			private String publisher;
			private String group;
			private String name;
			private String version;
			private String description;
			private List<Licenses> licenses; 
			private String purl;
			private String type;
			@JsonProperty("bom-ref")
			private String bomRef;
			
		}
	}
	
	@Data
	public static class Components {
		private String author;
		private String publisher;
		private String group;
		private String name;
		private String version;
		private String description;
		private String scope;
		private List<Hashes> hashes;
		private List<Licenses> licenses; 
		private String purl;
		private String type;
		@JsonProperty("bom-ref")
		private String bomRef;
		
		@Data
		public static class Hashes {
			private String alg;
			private String content;
		}
	}
	
	@Data
	public static class Licenses {
		private License license;
		
		@Data
		public static class License {
			private String id;
			private String name;
			private String url;
		}
	}
	
	@Data
	public static class Dependencies {
		private String ref;
		private List<String> dependsOn;
		
	}
	
	@Data
	public static class ExternalReferences {
		private String type;
		private String url;
		private String comment;
	}

}
