package cn.com.lee.util;

import cn.com.lee.controller.UploadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件处理工具类
 */
public class BaseFileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFileUtil.class);

    /**
     *  上传文件到服务器
     * @param bt
     * @param rootPath  静态资源目录
     * @param filePath  文件目录
     * @param fileName  文件名称
     * @return
     */
    public static UploadResult writeFileToService(byte[] bt, String rootPath, String filePath, String fileName) {

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File targetFile = null;

        try {

            File dir = new File(rootPath,filePath);

            System.out.println(dir);

            // 判断文件目录是否存在
            if(!dir.exists()){
                // 设置可写权限
                dir.setWritable(true);
                // 创建目录
                dir.mkdirs();
            }

            //保存
            targetFile = new File(dir, fileName);
            fos = new FileOutputStream(targetFile);
            bos = new BufferedOutputStream(fos);
            bos.write(bt);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return new UploadResult(true, targetFile.getName());
    }

    public static UploadResult writeFileToService(MultipartFile multipartFile, String rootPath, String filePath){
        String fileName = multipartFile.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;

        File fileDir = new File(rootPath, filePath);
        if(!fileDir.exists()){
            // 设置可写权限
            fileDir.setWritable(true);
            // 创建目录
            fileDir.mkdirs();
        }

        File targetFile = new File(fileDir, uploadFileName);

        try {
            multipartFile.transferTo(targetFile);
            // 文件上传到文件夹成功了
        } catch (IOException e) {
            LOGGER.error("上传文件异常", e);
            return null;
        }

        return new UploadResult(true, targetFile.getName());
    }

}
