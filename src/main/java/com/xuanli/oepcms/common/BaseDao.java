package com.xuanli.oepcms.common;

public interface BaseDao<T> {
	int insertUser(T entity);
	int updateUser(T entity);
}
