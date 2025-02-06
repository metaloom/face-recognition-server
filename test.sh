#!/bin/bash

#Photo by Italo Melo from Pexels: https://www.pexels.com/photo/man-wearing-blue-crew-neck-t-shirt-2379005/
IMAGE_URL="https://images.pexels.com/photos/2379005/pexels-photo-2379005.jpeg"

# Via URL
curl -X POST "http://127.0.0.1:8000/detect" \
    -H "Content-Type: application/json" \
    -d "{
    \"image_url\": \"$IMAGE_URL\"
}"

exit 0

# Via Base64 Data
IMAGE_DATA=$(curl -s "$IMAGE_URL" | base64)
JSON_FILE="/tmp/payload.json"
echo "{
    \"prompt\": \"Describe the image\",
    \"image_data\": \"$IMAGE_DATA\"
}" > $JSON_FILE

curl -X POST "http://127.0.0.1:8000/detect" \
    -H "Content-Type: application/json" \
    -d @$JSON_FILE