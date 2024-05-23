package model.bo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.bean.RequestBean;
import model.bean.UserBean;
import model.dto.RequestDto;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import repository.RequestRepository;
import utils.CommonConstant;
import java.util.Map;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RequestBo {
    // Singleton
    private static RequestBo instance;

    private RequestBo() {
        requestRepository = RequestRepository.getInstance();
        client = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.MILLISECONDS)
                .build();
    }

    public static RequestBo getInstance() {
        if (instance == null)
            instance = new RequestBo();
        return instance;
    }

    // Dependency Injection
    private final RequestRepository requestRepository;
    private final OkHttpClient client;

    // Business Logic
    @SuppressWarnings({ "deprecation", "unchecked" })
    public RequestDto create(String userId, Part firstImage, Part secondImage) throws Exception {
        OutputStream firstImageOut = null;
        InputStream firstImageIn = null;
        OutputStream secondImageOut = null;
        InputStream secondImageIn = null;
        try {
            // Save images
            File firstFile = new File(CommonConstant.IMAGE_STORAGE_FOLDER + File.separator + getFileName(firstImage));
            firstImageOut = new FileOutputStream(firstFile);
            firstImageIn = firstImage.getInputStream();
            File secondFile = new File(CommonConstant.IMAGE_STORAGE_FOLDER + File.separator + getFileName(secondImage));
            secondImageOut = new FileOutputStream(secondFile);
            secondImageIn = secondImage.getInputStream();
            int read = 0;
            final byte[] firstBytes = new byte[1024];
            final byte[] secondBytes = new byte[1024];
            while ((read = firstImageIn.read(firstBytes)) != -1) {
                firstImageOut.write(firstBytes, 0, read);
            }
            while ((read = secondImageIn.read(secondBytes)) != -1) {
                secondImageOut.write(secondBytes, 0, read);
            }

            // Create request
            RequestBean result = requestRepository.create(
                    RequestBean.builder()
                            .user(UserBean.builder().id(userId).build())
                            .firstImage("no-image.png")
                            .secondImage("no-image.png")
                            .result(null)
                            .distance(null)
                            .build());

            // Send request to server ai
            new Thread(() -> {
                try {
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("base_img", "base_img",
                                    RequestBody.create(MediaType.parse("image/*"), firstFile))
                            .addFormDataPart("compare_img", "compare_img",
                                    RequestBody.create(MediaType.parse("image/*"), secondFile))
                            .build();
                    Request request = new Request.Builder()
                            .url(CommonConstant.SERVER_AI_URL)
                            .post(requestBody)
                            .addHeader("Content-Type", "multipart/form-data")
                            .build();
                    Response execute = client.newCall(request).execute();

                    // Read the response body once
                    String responseBody = execute.body().string();
                    System.out.println("Response: " + responseBody);

                    // Convert to map
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> response = mapper.readValue(responseBody, Map.class);
                    result.setFirstImage((String) response.get("base_img"));
                    result.setSecondImage((String) response.get("compare_img"));
                    result.setDistance((Double) response.get("distance"));
                    result.setResult((Boolean) response.get("verified"));
                    requestRepository.update(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            return new RequestDto().fromBean(result);
        } catch (FileNotFoundException e) {
            throw new Exception(
                    "You either did not specify a file to upload or are trying to upload a file to a protected or nonexistent location.");
        } catch (Exception e) {
            throw e;
        } finally {
            if (firstImageOut != null)
                firstImageOut.close();
            if (firstImageIn != null)
                firstImageIn.close();
            if (secondImageOut != null)
                secondImageOut.close();
            if (secondImageIn != null)
                secondImageIn.close();
        }
    }

    public RequestDto update(RequestDto requestDto) throws Exception {
        try {
            RequestBean result = requestRepository.update(
                    requestDto.toBean());
            return new RequestDto().fromBean(result);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<RequestDto> getAllByUserId(String userId) throws Exception {
        try {
            return requestRepository.getAllByUserId(userId).stream().map(e -> new RequestDto().fromBean(e)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public RequestDto getById(String id) throws Exception {
        try {
            RequestBean result = requestRepository.findById(id);
            return new RequestDto().fromBean(result);
        } catch (Exception e) {
            throw new Exception("Request not found!");
        }
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
