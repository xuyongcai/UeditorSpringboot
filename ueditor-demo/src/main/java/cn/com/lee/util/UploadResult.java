package cn.com.lee.util;

/**
 * 上传返回结果
 * @author: xiaochai
 * @create: 2019-05-17
 **/
public class UploadResult {

    private Boolean isSuccess = false;
    private String fileName;

    public UploadResult(){}

    public UploadResult(Boolean isSuccess, String fileName){
        this.isSuccess = isSuccess;
        this.fileName = fileName;
    }


    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
