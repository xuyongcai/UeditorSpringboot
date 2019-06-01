package cn.com.lee.common.ueditor.upload;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.lee.common.ueditor.PathFormat;
import cn.com.lee.common.ueditor.define.AppInfo;
import cn.com.lee.common.ueditor.define.BaseState;
import cn.com.lee.common.ueditor.define.FileType;
import cn.com.lee.common.ueditor.define.State;



public class BinaryUploader {
    static Logger logger = LoggerFactory.getLogger(BinaryUploader.class);
    
	public static final State save(HttpServletRequest request, Map<String, Object> conf) {
		
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("upfile");

			//静态资源目录
            String rootPath = (String) conf.get("rootPath");

            String savePath = (String) conf.get("savePath");
            String localSavePathPrefix = (String) conf.get("localSavePathPrefix");
			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
			savePath = savePath + suffix;
			
			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			savePath = PathFormat.parse(savePath, originFileName);
			localSavePathPrefix = localSavePathPrefix + savePath; 
			String physicalPath = localSavePathPrefix;
			logger.info("BinaryUploader physicalPath:{},savePath:{}",localSavePathPrefix,savePath);
			InputStream is = file.getInputStream();
			
			// 在此处调用ftp的上传图片的方法将图片上传到文件服务器
			// 文件目录
			String filePath = physicalPath.substring(0, physicalPath.lastIndexOf("/"));
			// 文件名称
			String picName = physicalPath.substring(physicalPath.lastIndexOf("/")+1, physicalPath.length());
			State storageState = StorageManager.saveFileByInputStream(request, is, rootPath, filePath, picName, maxSize);
			
			is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (Exception e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		}
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
