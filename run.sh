#!/bin/bash

podman run \
    --device nvidia.com/gpu=all \
    --shm-size 1g \
    --name face-detection-server \
    -p 8000:8000 \
    --rm \
    -v /extra/cache/huggingface:/root/.cache/huggingface \
    -v /extra/cache/insightface:/root/.insightface \
    docker.io/metaloom/face-recognition-server:latest
