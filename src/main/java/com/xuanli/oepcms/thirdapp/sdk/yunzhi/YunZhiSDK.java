/**
 * @fileName:  YunZhiSDK.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午4:21:40
 */
package com.xuanli.oepcms.thirdapp.sdk.yunzhi;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.JSGFBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.JSGFMapBean;
import com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean.JSGFWeiBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.ThirdAliOSSUtil;

/**
 * @author QiaoYu
 */
@SuppressWarnings("deprecation")
@Service
public class YunZhiSDK {
	@Autowired
	SystemConfig systemConfig;

	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;
	
	public String generatorStudentExamScore(String id, String text, String mode) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(systemConfig.YUN_ZHI_URL);
		MultipartEntity customMultiPartEntity = new MultipartEntity();
		try {
			HttpResponse response = null;
			if (StringUtil.isEmaile(mode)) {
				mode = "E";
			}
			// 句子的有流畅度等..
			customMultiPartEntity.addPart("mode", new StringBody(mode, Charset.forName("UTF-8")));
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			customMultiPartEntity.addPart("text", new StringBody(text, Charset.forName("UTF-8")));
			String uuid = UUID.randomUUID().toString().replace("-", "") + ".mp3";
			InputStream is = thirdAliOSSUtil.downloadFile(id);
			ContentBody fileBody = new InputStreamBody(is, uuid);
			customMultiPartEntity.addPart("voice", fileBody);
			httpPost.setEntity(customMultiPartEntity);
			httpPost.setHeader("appkey", systemConfig.YUN_ZHI_APPKEY);
			httpPost.setHeader("score-coefficient", "1.2");
			String uuid_str = UUID.randomUUID().toString();
			httpPost.setHeader("session-id", uuid_str);
			httpPost.setHeader("device-id", uuid_str);
			response = httpclient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				return result;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
			httpclient.close();
		}
	}
	public String generatorStudentExamScoreJSGF(String id,String pointResult,String currentResult, String mode) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(systemConfig.YUN_ZHI_URL);
		MultipartEntity customMultiPartEntity = new MultipartEntity();
		try {
			HttpResponse response = null;
			if (StringUtil.isEmaile(mode)) {
				mode = "A";
			}
			// 句子的有流畅度等..
			customMultiPartEntity.addPart("mode", new StringBody(mode, Charset.forName("UTF-8")));
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			customMultiPartEntity.addPart("text", new StringBody(getJSGF(pointResult,currentResult), Charset.forName("UTF-8")));
			String uuid = UUID.randomUUID().toString().replace("-", "") + ".mp3";
			InputStream is = thirdAliOSSUtil.downloadFile(id);
			ContentBody fileBody = new InputStreamBody(is, uuid);
			customMultiPartEntity.addPart("voice", fileBody);
			httpPost.setEntity(customMultiPartEntity);
			httpPost.setHeader("appkey", systemConfig.YUN_ZHI_APPKEY);
			httpPost.setHeader("score-coefficient", "1.2");
			String uuid_str = UUID.randomUUID().toString();
			httpPost.setHeader("session-id", uuid_str);
			httpPost.setHeader("device-id", uuid_str);
			response = httpclient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				return result;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
			httpclient.close();
		}
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 下午4:27:52
	 */
	public String generatorStudentScore(HomeworkScoreBean result) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(systemConfig.YUN_ZHI_URL);
		MultipartEntity customMultiPartEntity = new MultipartEntity();
		try {
			HttpResponse response = null;
			if (null != result.getHomeworkType() && result.getHomeworkType().intValue() == 1) {
				// 单词跟读 有音标判分
				customMultiPartEntity.addPart("mode", new StringBody("D", Charset.forName("UTF-8")));
			} else {
				// 句子的有流畅度等..
				customMultiPartEntity.addPart("mode", new StringBody("E", Charset.forName("UTF-8")));
			}
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			customMultiPartEntity.addPart("text", new StringBody(result.getStanderText(), Charset.forName("UTF-8")));
			// ContentBody fileBody = new FileBody(new File(result.getAudioPath()));
			String uuid = UUID.randomUUID().toString().replace("-", "") + ".mp3";
			InputStream is = thirdAliOSSUtil.downloadFile(result.getAudioPath());
			ContentBody fileBody = new InputStreamBody(is, uuid);
			customMultiPartEntity.addPart("voice", fileBody);
			httpPost.setEntity(customMultiPartEntity);
			httpPost.setHeader("appkey", systemConfig.YUN_ZHI_APPKEY);
			httpPost.setHeader("score-coefficient", "1.9");
			String uuid_str = UUID.randomUUID().toString();
			httpPost.setHeader("session-id", uuid_str);
			httpPost.setHeader("device-id", uuid_str);
			response = httpclient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				String text = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				return text;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
			httpclient.close();
		}
	}
	/**Title: generatorExerciseScore 
	 * Description:  
	 * @date 2018年3月13日 上午9:30:45
	 * @param result
	 * @return  
	 */
	public String generatorExerciseScore(String text,String file) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(systemConfig.YUN_ZHI_URL);
		MultipartEntity customMultiPartEntity = new MultipartEntity();
		try {
			HttpResponse response = null;
				// 单词跟读 有音标判分
				customMultiPartEntity.addPart("mode", new StringBody("D", Charset.forName("UTF-8")));
				// 句子的有流畅度等..
				customMultiPartEntity.addPart("mode", new StringBody("E", Charset.forName("UTF-8")));
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			customMultiPartEntity.addPart("text", new StringBody(text, Charset.forName("UTF-8")));
			// ContentBody fileBody = new FileBody(new File(result.getAudioPath()));
			String uuid = UUID.randomUUID().toString().replace("-", "") + ".mp3";
			InputStream is = thirdAliOSSUtil.downloadFile(file);
			ContentBody fileBody = new InputStreamBody(is, uuid);
			customMultiPartEntity.addPart("voice", fileBody);
			httpPost.setEntity(customMultiPartEntity);
			httpPost.setHeader("appkey", systemConfig.YUN_ZHI_APPKEY);
			httpPost.setHeader("score-coefficient", "1.9");
			String uuid_str = UUID.randomUUID().toString();
			httpPost.setHeader("session-id", uuid_str);
			httpPost.setHeader("device-id", uuid_str);
			response = httpclient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				String text1 = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				return text1;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
			httpclient.close();
		}
	}
	public static String getJSGF(String pointResult, String currentResult) {
		if (StringUtil.isEmpty(currentResult)) {
			System.out.println("句式为空,不能评分!");
			return null;
		}
		String[] pointResults = pointResult.split("@@");
		int pointSize = pointResults.length;
		Object[] objects = new Object[pointSize];
		for (int i = 0; i < pointSize; i++) {
			String[] ps = pointResults[i].split("\\|\\|");
			int psSize = ps.length;
			Object[] object2s = new Object[psSize];
			for (int j = 0; j < psSize; j++) {
				JSGFMapBean jsgfMapBean = new JSGFMapBean();
				jsgfMapBean.setWeight(0.5);
				jsgfMapBean.setKey(ps[j]);
				object2s[j] = jsgfMapBean;
			}
			objects[i] = object2s;
		}
		JSGFWeiBean jsgfWeiBean = new JSGFWeiBean();
		jsgfWeiBean.setWeight_struct(objects);
		
		currentResult = currentResult.replaceAll("\\|\\|", "|");
		
		JSGFBean jsgfBean = new JSGFBean(JSON.toJSONString(jsgfWeiBean), currentResult);
		System.out.println(JSON.toJSONString(jsgfBean));
		return JSON.toJSONString(jsgfBean);
	}
}
