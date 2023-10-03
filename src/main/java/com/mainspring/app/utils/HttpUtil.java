package com.mainspring.app.utils;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
public class HttpUtil {
	
	private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(30)).build();
	
	public String getApiResponse(HttpRequest request) {
		try {
			HttpResponse<String> httpResp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			int statusCode = httpResp.statusCode();
			if (statusCode >= 200 && statusCode <= 399) {
				return httpResp.body();
			} else {
				System.out.println("API call failed");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Exception while calling the API - " + e);
			return null;
		}
	}

}