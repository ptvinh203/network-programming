from utils import get_instance

if __name__ == "__main__":
    app, _ = get_instance()
    app.run(debug=True, port=8081, threaded=True)
