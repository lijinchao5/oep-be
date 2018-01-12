package com.xuanli.oepcms.util;

import java.util.Random;

public class RanNumUtil {
	
    public static String createRandomNum(int num) {  
        StringBuilder code = new StringBuilder();  
        Random random = new Random();  
        // 6位验证码  
        for (int i = 0; i < num; i++) {  
            code.append(String.valueOf(random.nextInt(10)));  
        }  
        System.out.println(code);
        return code.toString();
    }
//    public static void main(String[] args) {
//		RanNumUtil.createRandomNum(6);
//	}
}
