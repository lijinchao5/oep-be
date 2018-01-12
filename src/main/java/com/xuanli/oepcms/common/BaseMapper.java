package com.xuanli.oepcms.common;

public interface BaseMapper<T> {
	int insert(T entity);
	int update(T entity);
}
