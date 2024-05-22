<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>DHV - Home</title>

    <script src="https://cdn.tailwindcss.com"></script>
  </head>
  <body>
    <div
      class="min-h-screen bg-zinc-100 flex flex-col justify-start py-6 sm:px-6 lg:px-8"
    >
      <h1 class="text-center uppercase font-bold text-2xl mb-6">Home</h1>

      <main>
        <form action="" class="w-full flex flex-col gap-6 items-center">
          <div class="w-full flex flex-row gap-4">
            <div class="w-[50%] flex flex-col">
              <div
                class="flex flex-row justify-between gap-4 rounded-t-md border border-black/70 p-4"
              >
                <label class="font-semibold text-xl capitalize"
                  >Base image</label
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

              <div
                class="w-full rounded-b-md border border-black/70 border-t-0 p-4"
              >
                <img id="base_img_preview" class="object-cover rounded-md" />
              </div>

              <script>
                var preview_base_img = function (event) {
                  var output = document.getElementById('base_img_preview');
                  output.src = URL.createObjectURL(event.target.files[0]);
                  output.onload = function () {
                    URL.revokeObjectURL(output.src);
                  };
                };
              </script>
            </div>

            <div class="w-[50%] flex flex-col">
              <div
                class="flex flex-row justify-between gap-4 rounded-t-md border border-black/70 p-4"
              >
                <label class="font-semibold text-xl capitalize"
                  >Compare image</label
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

              <div
                class="w-full rounded-b-md border border-black/70 border-t-0 p-4"
              >
                <img id="compare_img_preview" class="object-cover rounded-md" />
              </div>

              <script>
                var preview_compare_img = function (event) {
                  var output = document.getElementById('compare_img_preview');
                  output.src = URL.createObjectURL(event.target.files[0]);
                  output.onload = function () {
                    URL.revokeObjectURL(output.src);
                  };
                };
              </script>
            </div>
          </div>

          <div>
            <button
              class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
            >
              Submit
            </button>
          </div>
        </form>
      </main>
    </div>
  </body>
</html>
