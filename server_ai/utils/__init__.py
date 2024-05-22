from datetime import datetime

from flask import Flask, jsonify
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy

from app import app_bp
from utils.environment import AppEnv

app = None
db = None


def create_app() -> tuple[Flask, SQLAlchemy]:
    # Initialize the Flask app
    app = Flask(__name__)
    app.config["SQLALCHEMY_DATABASE_URI"] = AppEnv.SQLALCHEMY_DATABASE_URI
    app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
    CORS(app)

    # Initialize the SQLAlchemy database
    db = SQLAlchemy(app)

    # Register the blueprint
    app.register_blueprint(app_bp)

    return app, db


def get_instance() -> tuple[Flask, SQLAlchemy]:
    global app, db

    if (app is None) or (db is None):
        app, db = create_app()

    return app, db


def get_timestamp():
    return datetime.now().strftime("%Y-%m-%d %H:%M")


class AppResponse:
    @staticmethod
    def success(data: dict = {}, message: str = ""):
        return jsonify({"success": True, "data": data, "message": message}), 200

    @staticmethod
    def bad_request(message: str):
        return jsonify({"success": False, "message": message}), 400

    @staticmethod
    def server_error(err: Exception):
        return jsonify({"success": False, "error": str(err)}), 500
