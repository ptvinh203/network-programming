package model.bo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

import model.bean.RequestBean;
import model.bean.UserBean;
import model.dto.RequestDto;
import repository.RequestRepository;
import utils.CommonConstant;

public class RequestBo {
    // Singleton
    private static RequestBo instance;

    private RequestBo() {
        requestRepository = RequestRepository.getInstance();
    }

    public static RequestBo getInstance() {
        if (instance == null)
            instance = new RequestBo();
        return instance;
    }

    // Dependency Injection
    private RequestRepository requestRepository;

    // Business Logic
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
                            .firstImage(firstFile.getPath())
                            .secondImage(secondFile.getPath())
                            .result(null)
                            .distance(null)
                            .build());
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
