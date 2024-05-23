import os
from uuid import uuid4

from deepface import DeepFace
from flask import Blueprint, request
from utils.response import AppResponse

app_bp = Blueprint("app", __name__, url_prefix="/api/v1")
STORAGE_DIR = os.path.join(os.getcwd(), "storage")


def app_process(base_img, compare_img):
    if not os.path.exists(STORAGE_DIR):
        os.mkdir(STORAGE_DIR)

    # Save images
    base_img_dir = os.path.join(STORAGE_DIR, f"{uuid4().hex}.png")
    base_img.save(base_img_dir)

    compare_img_dir = os.path.join(STORAGE_DIR, f"{uuid4().hex}.png")
    compare_img.save(compare_img_dir)

    # Verify images
    result = DeepFace.verify(
        base_img_dir,
        compare_img_dir,
        enforce_detection=False,
        model_name="Facenet512",
        detector_backend="yolov8",
        distance_metric="euclidean_l2",
    )

    return {
        "verified": result.get("verified", False),
        "distance": round(result.get("distance", 0), 3),
    }


@app_bp.route("", methods=["POST"])
def predict():
    base_img = request.files.get("base_img")
    compare_img = request.files.get("compare_img")

    try:
        if not (base_img and compare_img):
            return AppResponse.bad_request("Missing required fields")

        return AppResponse.success(
            data=app_process(base_img, compare_img),
            message="Processed task successfully.",
        )
    except Exception as e:
        return AppResponse.server_error(e)
