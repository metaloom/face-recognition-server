# Face Recognition Server

A simple FastAPI face recognition server for the [insightface](https://github.com/deepinsight/insightface) library.

## Env

* **MODEL_ID**: buffalo_l

## Container

```bash
podman run \
    --device nvidia.com/gpu=all \
    --shm-size 1g \
    --name smolvlm-server \
    -p 8000:8000 \
    --rm \
    metaloom/face-recognition-server:latest
```

## Spec

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