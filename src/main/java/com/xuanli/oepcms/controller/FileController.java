/**
 * @fileName:  FileController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月7日 上午11:02:17
 */
package com.xuanli.oepcms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.util.AliOSSUtil;

/**
 * @author QiaoYu
 */
@RestController()
@RequestMapping(value = "/file/")
public class FileController extends BaseController {
	@Autowired
	AliOSSUtil aliOSSUtil;
	
	@RequestMapping(value = "download.do", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(String type, String id) {
		String filename = UUID.randomUUID().toString().replace("-", "");
		HttpHeaders headers = new HttpHeaders();
		if (type.equals("mp3")) {
			filename = filename + ".mp3";
			// response.setContentType("audio/mpeg");
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		} else {
			filename = filename + ".jpg";
			// response.setContentType("image/jpeg");
			headers.setContentType(MediaType.IMAGE_JPEG);
		}
		InputStream is = aliOSSUtil.downloadFile(id);

		byte[] body;
		try {
			body = new byte[is.available()];
			is.read(body);
			headers.setContentDispositionFormData("attachment;filename", filename);
			HttpStatus statusCode = HttpStatus.OK;
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
			return entity;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
