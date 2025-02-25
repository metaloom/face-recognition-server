package io.metaloom.facedetection.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class FaceDetectionServerClientTest extends AbstractClientTest {

	public static final String SINGLE_FACE_IMAGE_URL = "https://images.pexels.com/photos/2379005/pexels-photo-2379005.jpeg";

	public static final String MULTIFACE_IMAGE_URL = "https://images.pexels.com/photos/3812743/pexels-photo-3812743.jpeg";

	public static final String NO_FACE_IMAGE_URL = "https://images.pexels.com/photos/627678/pexels-photo-627678.jpeg";

	@Test
	public void testMultiFace() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		JsonObject json = client.detect(MULTIFACE_IMAGE_URL, null);
		JsonArray faces = json.getJsonArray("faces");
		assertEquals(24, faces.size());
		System.out.println(faces.size());
		System.out.println(json.encodePrettily());
	}

	@Test
	public void testSingleFace() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		JsonObject json = client.detect(SINGLE_FACE_IMAGE_URL, null);
		JsonArray faces = json.getJsonArray("faces");
		assertEquals(1, faces.size());
		System.out.println(faces.size());
		System.out.println(json.encodePrettily());
	}

	@Test
	public void testNoFace() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		JsonObject json = client.detect(NO_FACE_IMAGE_URL, null);
		JsonArray faces = json.getJsonArray("faces");
		assertEquals(0, faces.size());
		System.out.println(faces.size());
		System.out.println(json.encodePrettily());
	}
}
