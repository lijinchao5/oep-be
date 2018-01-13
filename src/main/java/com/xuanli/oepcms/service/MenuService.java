package com.xuanli.oepcms.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.mapper.MenuMapper;

public class MenuService {
	
	@Autowired
	MenuMapper menuMapper;
	
	public Set<String> listPerms(Integer userId){
		List<String> perms = menuMapper.listUserPerms(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StringUtils.isNotBlank(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}
}
