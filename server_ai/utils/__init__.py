import os
from datetime import datetime

from app import app_bp
from flask import Flask, send_from_directory
from flask_cors import CORS

app = None
STORAGE_DIR = os.path.join(os.getcwd(), "storage")


def create_app() -> Flask:
    # Initialize the Flask app
    app = Flask(__name__)
    CORS(app)

    # General API
    @app.route("/<path:filename>", methods=["GET"])
    def send_image(filename):
        if filename == "no-image.png":
            return send_from_directory(os.getcwd(), filename)

        return send_from_directory(STORAGE_DIR, filename)

    # Register the blueprint
    app.register_blueprint(app_bp)

    return app


def get_instance() -> Flask:
    global app

    if app is None:
        app = create_app()

    return app


def get_timestamp():
    return datetime.now().strftime("%Y-%m-%d %H:%M")
