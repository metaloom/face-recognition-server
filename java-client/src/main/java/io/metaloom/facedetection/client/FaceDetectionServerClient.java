package io.metaloom.facedetection.client;

import java.io.IOException;
import java.net.URISyntaxException;

import io.metaloom.facedetection.client.impl.FaceDetectionServerClientBuilderImpl;
import io.vertx.core.json.JsonObject;

public interface FaceDetectionServerClient {

	public static final String DEFAULT_BASEURL = "http://localhost:8000/api/v1";

	public static FaceDetectionServerClient newFaceDetectClient() {
		return newBuilder().setBaseURL(DEFAULT_BASEURL).build();
	}

	/**
	 * Create a new builder to construct a new client.
	 *
	 * @return
	 */
	public static Builder newBuilder() {
		return new FaceDetectionServerClientBuilderImpl();
	}

	public interface Builder {

		/**
		 * Construct a new client
		 *
		 * @return Constructed client
		 */
		FaceDetectionServerClient build();

		/**
		 * Return the base URL of the client.
		 *
		 * @return
		 */
		String baseURL();

		/**
		 * Set the base URL for the client.
		 *
		 * @param baseURL
		 * @retun Fluent API
		 */
		Builder setBaseURL(String baseURL);

	}

	JsonObject detect(String imageURL, String imageData) throws URISyntaxException, IOException, InterruptedException;
}
