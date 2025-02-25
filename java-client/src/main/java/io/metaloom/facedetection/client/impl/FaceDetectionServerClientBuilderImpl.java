package io.metaloom.facedetection.client.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.metaloom.facedetection.client.FaceDetectionServerClient;

public class FaceDetectionServerClientBuilderImpl implements FaceDetectionServerClient.Builder {

	private static final Logger logger = LoggerFactory.getLogger(FaceDetectionServerClientBuilderImpl.class);

	private String baseURL;

	@Override
	public FaceDetectionServerClient build() {
		Objects.requireNonNull(baseURL, "You must specify the baseURL for the client.");
		return new FaceDetectionServerClientImpl(this);
	}

	@Override
	public String baseURL() {
		return baseURL;
	}

	@Override
	public FaceDetectionServerClient.Builder setBaseURL(String baseURL) {
		if (baseURL != null && !baseURL.endsWith("/")) {
			baseURL += "/";
		}
		this.baseURL = baseURL;
		return this;
	}

}
