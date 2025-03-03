package io.metaloom.facedetection.client.model;

public class FaceBox {

	int startX;
	int startY;

	int width;
	int height;

	public int getStartY() {
		return startY;
	}

	FaceBox setStartY(int startY) {
		this.startY = startY;
		return this;
	}

	public int getStartX() {
		return startX;
	}

	FaceBox setStartX(int startX) {
		this.startX = startX;
		return this;
	}

	public int getHeight() {
		return height;
	}

	FaceBox setHeight(int height) {
		this.height = height;
		return this;
	}

	public int getWidth() {
		return width;
	}

	FaceBox setWidth(int width) {
		this.width = width;
		return this;
	}
}
