package io.metaloom.facedetection.client.model;

public class FaceModel {

	private FaceBox box;

	private float[] embedding;

	public FaceBox getBox() {
		return box;
	}

	public void setBox(FaceBox box) {
		this.box = box;
	}

	/**
	 * Set the embedding for the face.
	 * 
	 * @param faceEmbedding
	 * @return Fluent API
	 */
	public FaceModel setEmbedding(float[] embedding) {
		this.embedding = embedding;
		return this;
	}

	/**
	 * Return the embedding for the face.
	 * 
	 * @return
	 */
	public float[] getEmbedding() {
		return embedding;
	}
}
