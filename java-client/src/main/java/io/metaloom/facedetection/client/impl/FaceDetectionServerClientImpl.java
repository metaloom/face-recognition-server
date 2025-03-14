package io.metaloom.facedetection.client.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.metaloom.facedetection.client.FaceDetectionServerClient;
import io.metaloom.facedetection.client.model.DetectionRequest;
import io.metaloom.facedetection.client.model.DetectionResponse;

public class FaceDetectionServerClientImpl implements FaceDetectionServerClient {

	private static Logger logger = LoggerFactory.getLogger(FaceDetectionServerClientImpl.class);

	private final String baseURL;

	private ObjectMapper mapper = new ObjectMapper();

	protected FaceDetectionServerClientImpl(FaceDetectionServerClient.Builder builder) {
		this.baseURL = builder.baseURL();
	}

	@Override
	public DetectionResponse detectByImageData(String imageData) throws URISyntaxException, IOException, InterruptedException {
		return detect(null, imageData);
	}

	@Override
	public DetectionResponse detectByImageURL(String imageURL) throws URISyntaxException, IOException, InterruptedException {
		return detect(imageURL, null);
	}

	private DetectionResponse detect(String imageURL, String imageData) throws URISyntaxException, IOException, InterruptedException {
		DetectionRequest request = new DetectionRequest();
		request.setImageUrl(imageURL);
		request.setImageData(imageData);
		String json = invokeRequest(request, "detect");
		return mapper.readValue(json, DetectionResponse.class);
	}

	private String invokeGetRequest(String path) throws URISyntaxException, IOException, InterruptedException {
		String fullURL = baseURL + path;
		HttpRequest request = HttpRequest.newBuilder()
			.uri(new URI(fullURL))
			.headers("Accept", "application/json")
			.GET()
			.build();

		HttpResponse<String> response = HttpClient.newBuilder()
			.build()
			.send(request, BodyHandlers.ofString());
		int code = response.statusCode();
		if (code < 200 || code > 299) {
			throw new IOException("Failed to handle request. Got code " + code + " from server.");
		}

		return response.body();
	}

	private <T> String invokeRequest(T requestModel, String path) throws URISyntaxException, IOException, InterruptedException {
		String fullURL = baseURL + path;
		String json = mapper.writeValueAsString(requestModel);
		HttpRequest request = HttpRequest.newBuilder()
			.uri(new URI(fullURL))
			.headers("Content-Type", "application/json")
			.headers("Accept", "application/json")
			.POST(BodyPublishers.ofString(json))
			.build();

		return invokeRequest(request, path);
	}

	private String invokeRequest(HttpRequest request, String path) throws URISyntaxException, IOException, InterruptedException {
		HttpResponse<String> response = HttpClient.newBuilder()
			.connectTimeout(Duration.ofMinutes(5))
			.build()
			.send(request, BodyHandlers.ofString());
		int code = response.statusCode();
		if (code < 200 || code > 299) {
			logger.error("Failed to process request. Got response {}", response.body());
			throw new IOException("Failed to handle request. Got code " + code + " from server.");
		}

		return response.body();
	}

}
