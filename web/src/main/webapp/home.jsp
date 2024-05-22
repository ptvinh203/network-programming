<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>DHV - Home</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <script>
      var preview_base_img = function (event) {
        var output = document.getElementById('base_img_preview');
        output.src = URL.createObjectURL(event.target.files[0]);
        output.onload = function () {
          URL.revokeObjectURL(output.src);
        };
      };
    </script>

    <script>
      var preview_compare_img = function (event) {
        var output = document.getElementById('compare_img_preview');
        output.src = URL.createObjectURL(event.target.files[0]);
        output.onload = function () {
          URL.revokeObjectURL(output.src);
        };
      };
    </script>
  </head>

  <body>
    <div class="min-h-screen w-full flex flex-col bg-gray-300 text-base">
      <div
        class="mx-auto w-full max-w-7xl h-full gap-6 p-10 bg-white flex flex-col rounded-md my-4"
      >
        <form
          action="/web/home"
          class="w-2/3 flex flex-col justify-between item-center gap-4"
        >
          <h3 class="text-2xl font-semibold">Upload images</h3>

          <div class="w-full">
            <div
              class="flex flex-col rounded-lg shadow-sm p-4 gap-4 item-start border"
            >
              <div class="flex flex-row item-center justify-between gap-4">
                <label for="base_img" class="font-semibold capitalize"
                  >Base Image</label
                >

                <input
                  type="file"
                  id="base_img"
                  name="base_img"
                  accept="image/*"
                  required
                  onchange="preview_base_img(event)"
                />
              </div>

              <div class="flex flex-row item-center justify-between gap-4">
                <label for="compare_img" class="font-semibold capitalize"
                  >Compare Image</label
                >

                <input
                  type="file"
                  id="compare_img"
                  name="compare_img"
                  accept="image/*"
                  required
                  onchange="preview_compare_img(event)"
                />
              </div>
            </div>
          </div>

          <div class="flex item-center justify-center">
            <button
              type="submit"
              class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-full border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100"
            >
              Submit
            </button>
          </div>
        </form>

        <div id="preview" class="flex flex-col gap-4">
          <h3 class="text-2xl font-semibold">Preview</h3>

          <div class="flex flex-row item-center justify-between gap-4">
            <div class="flex-1 m-auto">
              <img
                id="base_img_preview"
                src="./static/no-image.png"
                alt="Base image"
                class="w-full h-auto rounded-md"
              />
            </div>

            <div class="w-12 h-auto m-auto">
              <img
                src="./static/compare.png"
                alt="Compare icon"
                class="object-cover"
              />
            </div>

            <div class="flex-1 m-auto">
              <img
                id="compare_img_preview"
                src="./static/no-image.png"
                alt="Compare image"
                class="w-full h-auto rounded-md"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
