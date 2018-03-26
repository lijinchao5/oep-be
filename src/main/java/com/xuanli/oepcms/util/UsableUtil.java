/**
 * 
 */
package com.xuanli.oepcms.util;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.AreaUseEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.entity.UserSchoolEntity;
import com.xuanli.oepcms.mapper.AreaUseEntityMapper;
import com.xuanli.oepcms.mapper.SchoolEntityMapper;
import com.xuanli.oepcms.mapper.UserEntityMapper;

/**
 * @author lijinchao
 * @date 2018年3月26日 上午10:15:33
 */
@Service
public class UsableUtil {
	@Autowired
	AreaUseEntityMapper AreaUseDao;
	@Autowired
	SchoolEntityMapper schoolDao;
	@Autowired
	UserEntityMapper userDao;

	Date now = new Date();

	/**
	 * Title: getEndDateByAreaId 
	 * Description:  角色(roleId)为5/6/7
	 * @date 2018年3月26日 下午12:44:23
	 * @param areaId
	 * @return
	 */
	public boolean getEndDateByAreaId(String areaId) {
		if (StringUtil.isEmpty(areaId)) {
			return false;
		}
		AreaUseEntity areaUseEntity = AreaUseDao.getEndDateByAreaId(areaId);
		if (null != areaUseEntity) {
			Date endDate = areaUseEntity.getEnddate();
			if (null != endDate && endDate.getTime() > now.getTime()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Title: getEndDateBySchoolId 
	 * Description:  角色(roleId)为3/8(老师或学校教研员)
	 * @date 2018年3月26日 下午12:44:27
	 * @param userId
	 * @return
	 */
	public boolean getEndDateBySchoolId(Long userId) {
		List<UserSchoolEntity> UserSchoolEntities = schoolDao.getEndDateBySchoolId(userId);
		if (null != UserSchoolEntities && UserSchoolEntities.size() > 0) {
			Date endDate = UserSchoolEntities.get(0).getEnddate();
			if (null != endDate && endDate.getTime() > now.getTime()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Title: getEndDateByUserId 
	 * Description:  角色(roleId)为4(学生)
	 * @date 2018年3月26日 下午12:44:32
	 * @param userId
	 * @return
	 */
	public boolean getEndDateByUserId(Long userId) {
		UserEntity userEntity = userDao.getEndDateByUserId(userId);
		if (null != userEntity) {
			Date endDate = userEntity.getEnddate();
			if (null != endDate && endDate.getTime() > now.getTime()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
