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

import io.metaloom.facedetection.client.FaceDetectionServerClient;
import io.vertx.core.json.JsonObject;

public class FaceDetectionServerClientImpl implements FaceDetectionServerClient {

	private final String baseURL;

	protected FaceDetectionServerClientImpl(FaceDetectionServerClient.Builder builder) {
		this.baseURL = builder.baseURL();
	}

	@Override
	public JsonObject detect(String imageURL, String imageData) throws URISyntaxException, IOException, InterruptedException {
		JsonObject requestJson = new JsonObject();
		requestJson.put("image_url", imageURL);
		requestJson.put("image_data", imageData);
		return invokeRequest(requestJson, "detect");
	}

	private JsonObject invokeGetRequest(String path) throws URISyntaxException, IOException, InterruptedException {
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

		return new JsonObject(response.body());
	}

	private JsonObject invokeRequest(JsonObject requestJson, String path) throws URISyntaxException, IOException, InterruptedException {
		String fullURL = baseURL + path;
		HttpRequest request = HttpRequest.newBuilder()
			.uri(new URI(fullURL))
			.headers("Content-Type", "application/json")
			.headers("Accept", "application/json")
			.POST(BodyPublishers.ofString(requestJson.encode()))
			.build();

		return invokeRequest(request, path);
	}

	private JsonObject invokeRequest(HttpRequest request, String path) throws URISyntaxException, IOException, InterruptedException {
		HttpResponse<String> response = HttpClient.newBuilder()
			.connectTimeout(Duration.ofMinutes(5))
			.build()
			.send(request, BodyHandlers.ofString());
		int code = response.statusCode();
		if (code < 200 || code > 299) {
			System.err.println(response.body());
			throw new IOException("Failed to handle request. Got code " + code + " from server.");
		}

		return new JsonObject(response.body());
	}

}
