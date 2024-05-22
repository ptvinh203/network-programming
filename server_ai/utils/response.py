from flask import jsonify


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
