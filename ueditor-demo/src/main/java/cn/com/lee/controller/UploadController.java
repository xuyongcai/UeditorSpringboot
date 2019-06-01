package cn.com.lee.controller;

import cn.com.lee.util.BaseFileUtil;
import cn.com.lee.util.DateTimeUtil;
import cn.com.lee.util.UploadResult;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 *
 * @author: xiaochai
 * @create: 2019-05-31
 **/
@RequestMapping(value = "/upload")
@RestController
public class UploadController implements ServletContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private String rootPath;

    @Value("${demo.fileUpload.fileSavePathPrefix}")
    private String fileSavePathPrefix;
    @Value("${demo.fileUpload.fileSavePath}")
    private String fileSavePath;
    @Value("${demo.fileUpload.fileMaxSize}")
    private String fileMaxSize;
    @Value("${demo.fileUpload.fileUrlPrefix}")
    private String fileUrlPrefix;

    @Override
    public void setServletContext(ServletContext servletContext) {
        rootPath = servletContext.getRealPath("/");
//		System.out.println(rootPath);
    }


    /**
     * 上传文件
     * 上传到项目的webapp
     *
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Object upload(@RequestPart("file") MultipartFile multipartFile) {

        try {
            if (null != multipartFile) {
                String localSavePath = fileSavePathPrefix + fileSavePath + "/"
                        + DateTimeUtil.getFormatDate(new Date(), DateTimeUtil.YEAR_MONTH_DATE_FORMAT);

                long size = multipartFile.getSize();
                if (size > Long.valueOf(fileMaxSize)){
                    return null;
                }

                UploadResult result = BaseFileUtil.writeFileToService(multipartFile, rootPath, localSavePath);

                if (result.getIsSuccess()) {
                    Map<String, Object> resultMap = new HashMap<>();

                    resultMap.put("size", size);
                    // 文件名
                    resultMap.put( "originalName", multipartFile.getOriginalFilename());
                    // 文件名
                    resultMap.put( "fileName", result.getFileName());
                    // 所属group
                    resultMap.put( "group", localSavePath.substring(localSavePath.lastIndexOf("/") + 1));
                    // 文件访问的url
                    resultMap.put( "url", fileUrlPrefix  + "/" + localSavePath + "/" + result.getFileName());

                    return JSONObject.valueToString(resultMap);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
