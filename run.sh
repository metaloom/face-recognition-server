#!/bin/bash

podman run \
    --device nvidia.com/gpu=all \
    --shm-size 1g \
    --name face-detection-server \
    -p 8000:8000 \
    --rm \
    -v /opt/cache/huggingface:/root/.cache/huggingface \
    localhost/metaloom/face-detection-server:latest