package cn.com.lee.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.lee.common.ueditor.ActionEnter;
import org.springframework.web.context.ServletContextAware;

/**
 * 用于处理关于ueditor插件相关的请求
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/ueditor")
public class UeditorController implements ServletContextAware {

	private String rootPath;

	@RequestMapping(value = "/exec")
	@ResponseBody
	public String exec(HttpServletRequest request) throws UnsupportedEncodingException, JSONException {

		return new ActionEnter(request, rootPath).exec();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		rootPath = servletContext.getRealPath("/");
	}
}
