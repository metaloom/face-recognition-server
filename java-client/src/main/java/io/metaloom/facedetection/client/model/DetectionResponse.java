package io.metaloom.facedetection.client.model;

import java.util.List;

public class DetectionResponse {

	private List<FaceModel> faces;

	public List<FaceModel> getFaces() {
		return faces;
	}

	public void setFaces(List<FaceModel> faces) {
		this.faces = faces;
	}
}
