package com.touke.trade.controller;

import java.util.HashMap;

public class TestController {

	public static void main(String[] args) {
		HashMap map = new HashMap();
		for(int i = 0 ; i < 20000 ;i++) {
			
			if(i == 50) {
				System.out.println("2222222222");
				map.put("a" + i, i);
			}
			else if(i == 100) {
				System.out.println("333333333333");
            	map.put("a" + i, i);
			}
			else if(i == 15000) {
				System.out.println("44444444");
            	map.put("a" + i, i);
			}else {
				map.put("a" + i, i);
			}
            
		}
	}
}
