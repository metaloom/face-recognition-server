package io.metaloom.facedetection.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import io.metaloom.facedetection.client.model.DetectionResponse;
import io.metaloom.facedetection.client.model.FaceBox;
import io.metaloom.facedetection.client.model.FaceModel;

public class FaceDetectionServerClientTest extends AbstractClientTest {

	private static final String SINGLE_FACE_IMAGE_URL = "https://images.pexels.com/photos/2379005/pexels-photo-2379005.jpeg";

	private static final String MULTIFACE_IMAGE_URL = "https://images.pexels.com/photos/3812743/pexels-photo-3812743.jpeg";

	private static final String NO_FACE_IMAGE_URL = "https://images.pexels.com/photos/627678/pexels-photo-627678.jpeg";

	private static final String BASE_64_IMAGE_PATH = "src/test/resources/images/pexels-photo-3812743.jpeg.base64";

	private static final String JPG_IMAGE_PATH = "src/test/resources/images/pexels-photo-3812743.jpeg";

	@Test
	public void testMultiFaceURL() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		DetectionResponse response = client.detect(MULTIFACE_IMAGE_URL, null);
		assertResponse(response);

		BufferedImage img = ImageIO.read(new File(JPG_IMAGE_PATH));
		Graphics g = img.getGraphics();
		g.setColor(Color.RED);
		for (FaceModel face : response.getFaces()) {
			FaceBox box = face.getBox();
			int width = box.getWidth();
			int height = box.getHeight();
			g.drawRect(box.getStartX(), box.getStartY(), width, height);
		}
		g.dispose();
		show(img);
		System.in.read();

	}

	private void show(BufferedImage img) {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
	}

	@Test
	public void testMultiFaceBase64() throws URISyntaxException, IOException, InterruptedException {
		FaceDetectionServerClient client = client();
		String data = FileUtils.readFileToString(new File(BASE_64_IMAGE_PATH), Charset.defaultCharset());
		DetectionResponse response = client.detect(null, data);
		assertResponse(response);
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

	private void assertResponse(DetectionResponse response) {
		List<FaceModel> faces = response.getFaces();
		assertEquals(24, faces.size());
		FaceModel face = faces.get(0);
		assertNotNull(face.getBox());
		assertEquals(374, face.getBox().getHeight());
		assertEquals(267, face.getBox().getWidth());
		assertEquals(946, face.getBox().getStartX());
		assertEquals(2227, face.getBox().getStartY());
		assertEquals(512, face.getEmbedding().length);
	}
}
