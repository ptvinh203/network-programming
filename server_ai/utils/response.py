from flask import jsonify


class AppResponse:
    @staticmethod
    def custom(data: dict = {}, message: str = "", status_code: int = 200):
        general_response = {
            "success": True,
            "message": message,
        }
        return jsonify(general_response.update(data)), status_code

    @staticmethod
    def success(data: dict = {}, message: str = ""):
        return jsonify({"success": True, "data": data, "message": message}), 200

    @staticmethod
    def bad_request(message: str):
        return jsonify({"success": False, "message": message}), 400

    @staticmethod
    def server_error(err: Exception):
        return jsonify({"success": False, "error": str(err)}), 500
