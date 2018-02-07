/**
 * @fileName:  FileController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月7日 上午11:02:17
 */
package com.xuanli.oepcms.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuanli.oepcms.util.AliOSSUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@RestController()
@RequestMapping(value = "/file/")
public class FileController extends BaseController {
	@Autowired
	AliOSSUtil aliOSSUtil;

	@RequestMapping(value = "download.do", method = RequestMethod.GET)
	public void download(String type, String id) {
		String filename = UUID.randomUUID().toString().replace("-", "");
		if (type.equals("mp3")) {
			filename = filename + ".mp3";
			response.setContentType("audio/mpeg");
		} else {
			filename = filename + ".jpg";
			response.setContentType("image/jpeg");
		}
		InputStream inputStream = aliOSSUtil.downloadFile(id);

		OutputStream outputStream;
		try {
			outputStream = response.getOutputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("下载文件出现错误!");
		}
	}
	
	
	
	
	
	@RequestMapping(value = "uploadAudio.do", method = RequestMethod.POST)
	public RestResult<String> uploadAudio(@RequestParam("audiofile") MultipartFile file) {
		
		try {
			if (null!=file && !file.isEmpty()) {
				
				file.transferTo(new File("e:\\"+UUID.randomUUID().toString()+".mp3"));
				logger.debug("文件上传成功");
			}else {
				logger.debug("文件是空的");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return okNoResult("1234");
		
	}

	
}
