from flask import Flask, jsonify
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy

from utils.environment import AppEnv

app = None
db = None


def create_app() -> tuple[Flask, SQLAlchemy]:
    app = Flask(__name__)
    app.config["SQLALCHEMY_DATABASE_URI"] = AppEnv.SQLALCHEMY_DATABASE_URI
    app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
    CORS(app)

    db = SQLAlchemy(app)

    return app, db


def get_instance() -> tuple[Flask, SQLAlchemy]:
    global app, db

    if (app is None) or (db is None):
        app, db = create_app()

    return app, db


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
