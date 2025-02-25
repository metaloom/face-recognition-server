# Face Recognition Server

A simple FastAPI face recognition server for the [insightface](https://github.com/deepinsight/insightface) library.

## Env

* **MODEL_ID**: buffalo_l

## Container

```bash
podman run \
    --device nvidia.com/gpu=all \
    --shm-size 1g \
    --name face-recognition-server \
    -p 8000:8000 \
    --rm \
    metaloom/face-recognition-server:latest
```

## Java Client


```bash
String BASE_URL = "http://localhost:8000/api/v1";

FaceDetectionServerClient client = FaceDetectionServerClient.newBuilder()
  .setBaseURL(BASE_URL).build();

FaceDetectionServerClient client = client();
JsonObject json = client.detect(MULTIFACE_IMAGE_URL, null);
```



## Spec

`POST /api/v1/detect`

```json
{
  "image_url": "https://huggingface.co/datasets/huggingface/documentation-images/resolve/main/SmolVLM.png",
  "image_data": "dGVzd…"
}
```

## Build

```bash
./build.sh
```

## Test

```bash
./test.sh
```

## Development

```bash
pip3 install -r requirements.txt

uvicorn main:app --reload
```