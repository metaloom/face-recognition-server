from io import BytesIO
import os
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from PIL import Image
from fastapi.responses import JSONResponse
import logging
import base64
import insightface
from PIL import Image
import requests
from io import BytesIO
from insightface.app import FaceAnalysis
import requests
import numpy as np
import cv2
from io import BytesIO

# Initialize logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()
modelid = os.getenv("MODEL_ID", "buffalo_l")

# Initialize the face analysis model
fa = FaceAnalysis(name=modelid)
fa.prepare(ctx_id=0, det_size=(640, 640))

class Request(BaseModel):
    image_url: str | None = None
    image_data: str | None = None

def download_image(url):
    response = requests.get(url)
    if response.status_code == 200:
        image = np.asarray(bytearray(response.content), dtype=np.uint8)
        return cv2.imdecode(image, cv2.IMREAD_COLOR)
    else:
        raise ValueError("Failed to download image")

def download_image2(url):
    response = requests.get(url)
    img = Image.open(BytesIO(response.content))

def get_face_embeddings(img):
       
    # Detect and extract faces
    faces = fa.get(img)
    
    if not faces:
        logger.info("No faces detected!")
        return None
    
    face_data = []
    for face in faces:
        face_data.append({
            "embedding": face.embedding.tolist(),
            "bbox": face.bbox.tolist()
        })

    return face_data


def decode_image(base64_image_data):
    logger.info("Loading image from base64 data")
    image_data = base64.b64decode(base64_image_data)
    return Image.open(BytesIO(image_data))

@app.post("/api/v1/detect")
async def detect(item: Request):

    if item.image_data and item.image_url:
        raise HTTPException(status_code=400, detail="image_url and image_data specified. Only provide one.")

    if item.image_data is None and item.image_url is None:
        raise HTTPException(status_code=400, detail="image_url or image_data must be specified.")

    image = None
    if item.image_data:
        image = decode_image(item.image_data)
    elif item.image_url:
        # Load the image via URL
        image = download_image(item.image_url)

    faces = get_face_embeddings(image)

    #if faces:
    #    embeddings = [face.embedding.tolist() for face in faces]
    #else:
    #    embeddings = []

    msg = {"faces": faces}
    return JSONResponse(content=msg, status_code=200)



