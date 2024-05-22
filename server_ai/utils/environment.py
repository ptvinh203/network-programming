import os

from dotenv import find_dotenv, load_dotenv


class AppEnv:
    """
    Represents the application environment settings.

    Attributes:
        SQLALCHEMY_DATABASE_URI (str): The URI for the SQLAlchemy database.
    """

    load_dotenv(find_dotenv())

    FLASK_ENV = os.environ.get("FLASK_ENV", "development")

    SQLALCHEMY_DATABASE_URI = os.environ.get("SQLALCHEMY_DATABASE_URI", "")
