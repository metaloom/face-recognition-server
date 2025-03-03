package io.metaloom.facedetection.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import io.metaloom.facedetection.client.model.DetectionResponse;
import io.metaloom.facedetection.client.model.FaceModel;

public class FaceDetectionServerClientTest extends AbstractClientTest {

	public static final String SINGLE_FACE_IMAGE_URL = "https://images.pexels.com/photos/2379005/pexels-photo-2379005.jpeg";

	public static final String MULTIFACE_IMAGE_URL = "https://images.pexels.com/photos/3812743/pexels-photo-3812743.jpeg";

	public static final String NO_FACE_IMAGE_URL = "https://images.pexels.com/photos/627678/pexels-photo-627678.jpeg";

	@Test
	public void testMultiFaceURL() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		DetectionResponse response = client.detect(MULTIFACE_IMAGE_URL, null);
		List<FaceModel> faces = response.getFaces();
		assertEquals(24, faces.size());
		System.out.println(faces.size());
	}

	@Test
	public void testMultiFaceBase64() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		String data = FileUtils.readFileToString(new File("src/test/resources/pexels-photo-3812743.jpeg.base64"), Charset.defaultCharset());
		DetectionResponse response = client.detect(null, data);
		List<FaceModel> faces = response.getFaces();
		assertEquals(24, faces.size());
		System.out.println(faces.size());
	}

	@Test
	public void testSingleFace() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		DetectionResponse response = client.detect(SINGLE_FACE_IMAGE_URL, null);
		List<FaceModel> faces = response.getFaces();
		assertEquals(1, faces.size());
		System.out.println(faces.size());
	}

	@Test
	public void testNoFace() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		DetectionResponse response = client.detect(NO_FACE_IMAGE_URL, null);
		List<FaceModel> faces = response.getFaces();
		assertEquals(0, faces.size());
		System.out.println(faces.size());
	}
}
