package io.metaloom.facedetection.client;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import io.vertx.core.json.JsonObject;

public class FaceDetectionServerClientTest extends AbstractClientTest {

	@Test
	public void testDetect() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client  = client();
		String imageURL =  "https://images.pexels.com/photos/2379005/pexels-photo-2379005.jpeg";
		JsonObject json = client.detect(imageURL, null);
		System.out.println(json.encodePrettily());
	}
}
