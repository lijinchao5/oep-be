package com.xuanli.oepcms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserClasEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.ExcelUtil;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

@RestController
@RequestMapping(value = "/user/")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "insert.do")
	public RestResult<String> saveUser(UserEntity user) {
		try {
			try {
				user.setCreateId(getCurrentUser().getId().intValue() + "");
			} catch (Exception e) {
				logger.error("保存用户-->设置创建人失败!");
			}
			user.setPassword(PasswordUtil.generate(user.getPassword()));
			int result = userService.saveUser(user);
			if (result > 0) {
				return ok("增加用户成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加用户失败.");
			}
		} catch (Exception e) {
			logger.error("增加用户失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加用户失败.");
		}
	}

	/**
	 * @Description: TODO 教师注册
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午1:38:00
	 */
	@RequestMapping(value = "teacher_regist.do")
	public RestResult<String> teacher_regist(String schoolId, String mobile, String randomStr, String password, String mobileRandomStr) {

		if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(getRandomNum())) {

			if (StringUtil.isEmpty(schoolId)) {
				return failed(ExceptionCode.USERINFO_ERROR_CODE, "校区ID不能为空.");
			}
			if (StringUtil.isEmpty(mobile)) {
				return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码不能为空.");
			}
			if (StringUtil.isEmpty(mobileRandomStr)) {
				return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机验证码不能为空.");
			}
			if (StringUtil.isEmpty(password)) {
				return failed(ExceptionCode.USERINFO_ERROR_CODE, "密码不能为空.");
			}
			logger.debug("对比手机短信验证码:" + mobileRandomStr + "===" + SessionUtil.getMobileMessageRandomNum(request));
			if (!mobileRandomStr.equalsIgnoreCase(SessionUtil.getMobileMessageRandomNum(request))) {
				return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机短信验证码错误.");
			}
			String result = userService.teacherRegist(schoolId, mobile, password);
			if (result.equals("0")) {
				return ok("注册成功.");
			} else if (result.equals("1")) {
				return failed(ExceptionCode.USERINFO_ERROR_CODE, "校区ID错误.");
			} else if (result.equals("2")) {
				return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码已经注册.");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
			}
		} else {
			return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "验证码错误.");
		}
	}

	/**
	 * @Description: TODO 学生注册
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午1:38:08
	 */
	@RequestMapping(value = "student_regist.do")
	public RestResult<String> student_regist(String classId, String mobile, String randomStr, String password, String mobileRandomStr) {
		if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(getRandomNum())) {
			if (StringUtil.isEmpty(classId)) {
				return failed(ExceptionCode.USERINFO_ERROR_CODE, "班级ID不能为空.");
			}
			if (StringUtil.isEmpty(mobile)) {
				return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码不能为空.");
			}
			if (StringUtil.isEmpty(mobileRandomStr)) {
				return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机验证码不能为空.");
			}
			if (StringUtil.isEmpty(password)) {
				return failed(ExceptionCode.USERINFO_ERROR_CODE, "密码不能为空.");
			}
			logger.debug("对比手机短信验证码:" + mobileRandomStr + "===" + SessionUtil.getMobileMessageRandomNum(request));
			if (!mobileRandomStr.equalsIgnoreCase(SessionUtil.getMobileMessageRandomNum(request))) {
				return failed(ExceptionCode.MOBILE_MESSAGE_ERROR_CODE, "手机短信验证码错误.");
			}
			String result = userService.studentRegist(classId, mobile, password);
			if (result.equals("0")) {
				return ok("注册成功.");
			} else if (result.equals("1")) {
				return failed(ExceptionCode.USERINFO_ERROR_CODE, "班级ID错误.");
			} else if (result.equals("2")) {
				return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码已经注册.");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
			}
		} else {
			return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "验证码错误.");
		}
	}

	/**
	 * @Description: TODO 获取该班级中用户信息---->分页
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午1:45:14
	 */
	@RequestMapping(value = "findStudentByPage.do")
	public RestResult<PageBean> findStudentByPage(UserEntity userEntity, Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		// 保证这个班是这个老师创建的
		userEntity.setClasCreateId(getCurrentUser().getId());
		userService.findStudentByPage(userEntity, pageBean);
		return ok(pageBean);
	}

	/**
	 * @Description: TODO 删除该班级中的这个学生
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午2:34:20
	 */
	@RequestMapping(value = "deleteStudent.do")
	public RestResult<String> deleteStudent(UserClasEntity userClasEntity, String clasId) {
		try {
			userService.deleteStudent(userClasEntity);
			return ok("操作成功.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除班级学生出现错误.");
			return failed(ExceptionCode.DELETE_STUDENT_ERROR, "删除班级学生出现错误.");
		}
	}

	/**
	 * @Description: TODO 重置学生密码
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午2:34:20
	 */
	@RequestMapping(value = "resetStudentPassword.do")
	public RestResult<String> resetStudentPassword(UserEntity userEntity) {
		try {
			userService.resetStudentPassword(userEntity);
			return ok("操作成功.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重置学生密码出现错误.");
			return failed(ExceptionCode.REST_STUDENT_PASSWORD_ERROR, "重置学生密码出现错误.");
		}
	}
	/**
	 * @Description:  TODO 批量添加班级学生
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月17日 上午11:29:57
	 */
	@RequestMapping(value = "addClasStudentBatch.do")
	public RestResult<String> addClasStudentBatch(int size, Long clasId) {
		try {
			if (size>100) {
				return failed(ExceptionCode.ADD_BATCH_SIZE_ERROR, "批量添加数量不能超过100!");
			}
			Long userId = getCurrentUser().getId();
			int successSize = userService.addClasStudentBatch(size, clasId, userId);
			if (successSize==-1) {
				return failed(ExceptionCode.NOT_AGING_ADD_ERROR, "已经批量添加过了,不能再次批量添加");
			}
			return ok(successSize + "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量添加出现错误.");
			return failed(ExceptionCode.ADD_BATCH_ERROR, "批量添加出现错误.");
		}
	}
	/**
	 * 导出班级学生
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月17日 上午11:02:35
	 */
	@RequestMapping(value = "exportClasStudent.do")
	public void partExport(HttpServletResponse response, Long clasId) {
		List<UserEntity> userEntities = userService.exportNameNum(clasId);
		Map<String, String> headMap = new HashMap<String, String>();// 获取属性-列头
		headMap.put("nameNum", "编号");
		String title = "班级学生导出";
		ExcelUtil.downloadExcelFile(title, headMap,  JSONArray.parseArray(JSON.toJSONString(userEntities)), response);
	}
}
