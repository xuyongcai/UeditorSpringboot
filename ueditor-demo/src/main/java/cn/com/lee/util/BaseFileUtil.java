package cn.com.lee.util;

import java.io.*;

/**
 * 文件处理工具类
 */
public class BaseFileUtil {

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

            if(!dir.exists()){  //判断文件目录是否存在
                dir.setWritable(true);  //设置可写权限
                dir.mkdirs();   //创建目录

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

    public static void main(String[] args) {
        String filePath = "ueditor/upload/file";

        File dir = new File(filePath);

        System.out.println(dir.exists());

        if(!dir.exists()){  //判断文件目录是否存在
            dir.setWritable(true);  //设置可写权限

            dir.mkdirs();   //创建目录
        }
    }


}
