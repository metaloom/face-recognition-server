package io.metaloom.facedetection.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetectionRequest {

	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("image_data")
	private String imageData;

	public void setImageUrl(String imageURL) {
		this.imageUrl = imageURL;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
}
