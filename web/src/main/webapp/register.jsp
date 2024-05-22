<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>DHV - Register</title>
  </head>
  <script>
    function onLoad() {
      const queryString = window.location.search;
      const urlParams = new URLSearchParams(queryString);
      const message = urlParams.get("error-message");
      if (message) {
        document.getElementById("error-message").innerHTML = message;
      }
    }
  </script>
  <body onload="onLoad()"></body>
</html>
