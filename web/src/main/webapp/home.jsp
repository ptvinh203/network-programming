<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>DHV - Home</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <script>
      var preview_base_img = function (event) {
        var output = document.getElementById("base_img_preview");
        output.src = URL.createObjectURL(event.target.files[0]);
        output.onload = function () {
          URL.revokeObjectURL(output.src);
        };
      };
    </script>

    <script>
      var preview_compare_img = function (event) {
        var output = document.getElementById("compare_img_preview");
        output.src = URL.createObjectURL(event.target.files[0]);
        output.onload = function () {
          URL.revokeObjectURL(output.src);
        };
      };
    </script>
  </head>

  <body>
    <div class="min-h-screen w-full flex flex-col bg-gray-300 text-base ">
      <div
        class="mx-auto w-full max-w-7xl h-full p-10 bg-white flex flex-row rounded-md my-4"
      >
        <div
          class="flex-1 h-full gap-1 flex flex-col item-start border-r mr-6 pr-6"
        >
          <form
            action="/web/request"
            method="POST"
            enctype="multipart/form-data"
            class="w-full flex flex-col justify-between item-center gap-4"
          >
            <h3 class="text-2xl font-semibold">Upload images</h3>

            <div class="w-full">
              <div
                class="flex flex-col rounded-lg shadow-lg p-8 gap-4 item-start border border-gray-200"
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
              class="bg-indigo-500 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded"
              >
              Submit
            </button>
            </div>
          </form>

          <div id="preview" class="flex flex-col gap-4">
            <h3 class="text-2xl font-semibold">Preview</h3>
            <div class="w-full flex flex-col p-10 gap-4 item-start">

              <div class="flex flex-col item-center justify-between gap-3">
                <div class="flex-1 flex item-center justify-start">
                  <img
                    id="base_img_preview"
                    src="./static/no-image.png"
                    alt="Base image"
                    class="w-2/3 h-auto rounded-md"
                  />
                </div>

                <div class="w-8 h-auto m-auto rotate-90">
                  <img
                    src="./static/compare.png"
                    alt="Compare icon"
                    class="object-cover font-weight-bold"
                  />
                </div>

                <div class="flex-1 flex item-center justify-end">
                  <img
                    id="compare_img_preview"
                    src="./static/no-image.png"
                    alt="Compare image"
                    class="w-2/3 h-auto rounded-md"
                  />
                </div>
              </div>
             </div> 
          </div>
        </div>

        <div class="w-3/12 flex flex-col rounded-lg shadow-lg p-4 gap-4 item-start border border-gray-200">
          <div class="flex flex-row p-4">
            <h3 class="text-2xl font-semibold">History</h3>
            <a href="/web/home?logout=true">Log out</a>
          </div>
        
          <div class="px-4">
            <div>
              <span>Created at</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
