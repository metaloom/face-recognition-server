package io.metaloom.facedetection.client;

public abstract class AbstractClientTest {

	private static final String BASE_URL = "http://localhost:8000/api/v1";

	public FaceDetectionServerClient client() {
		FaceDetectionServerClient client = FaceDetectionServerClient.newBuilder()
			.setBaseURL(BASE_URL).build();
		return client;
	}
}
