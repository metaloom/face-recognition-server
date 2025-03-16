package io.metaloom.facedetection.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

public final class ImageTestUtils {

	public static String toBase64JPG(BufferedImage image) throws IOException {
		if (image == null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream)) {
			saveJPG(base64OutputStream, image);
		}
		return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
	}

	public static void saveJPG(OutputStream os, BufferedImage image) throws IOException {
		ImageOutputStream out = ImageIO.createImageOutputStream(os);
		ImageWriteParam params = getImageWriteparams();

		Iterator<ImageWriter> jpgWriters = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = jpgWriters.next();
		writer.setOutput(out);
		writer.write(null, new IIOImage(image, null, null), params);
	}

	private static ImageWriteParam getImageWriteparams() {
		JPEGImageWriteParam params = new JPEGImageWriteParam(null);
		params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		params.setCompressionQuality(0.95f);
		return params;
	}
}
