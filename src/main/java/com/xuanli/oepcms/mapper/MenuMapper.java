package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {
	List<String> listUserPerms(Integer id);
}
