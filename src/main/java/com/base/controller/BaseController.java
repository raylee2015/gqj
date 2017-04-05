package com.base.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import com.base.admin.entity.User;

/**
 * 基础的控制器，主要用来放置一些公共方法
 * 
 * @author JMSCADMIN
 *
 */
public class BaseController {

	/**
	 * @Description 获取登陆用户
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2016年12月16日
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected User getSessionUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			request.getRequestDispatcher("/WEB-INF/login.jsp")
					.forward(request, response);
			return null;
		}
		return user;
	}

	/**
	 * @Description 下载文件
	 * @Author RayLee
	 * @Version 1.0
	 * @date 2017年4月5日
	 * @param request
	 * @param response
	 * @param fileList
	 *            文件列表
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void downLoadFile(HttpServletRequest request,
			HttpServletResponse response, ArrayList<String> fileList)
			throws ServletException, IOException {
		downLoadFile(response, fileList);
		for (String filePath : fileList) {
			delAllFile(filePath);
		}
	}

	/**
	 * 压缩包名字
	 * 
	 * @return
	 */
	private String getZipFilename() {
		return System.currentTimeMillis() + ".zip";
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 */
	private void delAllFile(String path) {
		File file = new File(path);
		file.delete();
	}

	/**
	 * 打包下载
	 * 
	 * @param response
	 */
	private void downLoadFile(HttpServletResponse response,
			ArrayList<String> filePathList)
			throws ServletException, IOException {
		// 头文件
		response.setCharacterEncoding("utf-8");
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + this.getZipFilename());
		// --拼装
		ArrayList<File> fileList = new ArrayList<File>();
		for (int i = 0; i < filePathList.size(); i++) {
			File file = new File(filePathList.get(i));
			fileList.add(file);
		}
		File[] files = fileList.toArray(new File[fileList.size()]);

		// 压缩文件
		// ZipOutputStream zos = new
		// ZipOutputStream(resp.getOutputStream());
		ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(
				response.getOutputStream());
		for (File item : files) {
			ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(item,
					item.getName());
			zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
			InputStream is = null;
			try {
				is = new BufferedInputStream(new FileInputStream(item));
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = is.read(buffer)) != -1) {
					// 把缓冲区的字节写入到ZipArchiveEntry
					zipArchiveOutputStream.write(buffer, 0, len);
				}
				zipArchiveOutputStream.closeArchiveEntry();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (is != null)
					is.close();
			}
		}

		zipArchiveOutputStream.flush();
		zipArchiveOutputStream.close();
	}

}
