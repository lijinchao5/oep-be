package com.xuanli.oepcms.common;

public interface BaseDao<T> {
	int insert(T entity);
	int update(T entity);
}
