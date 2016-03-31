package com.shd.core.cmd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.shd.core.service.ShdCmdService;




@Component
public class OutputUpdateAccountCmd {


	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = 
            new ClassPathXmlApplicationContext("classpath:spring-core-mobile.xml");
		ShdCmdService shdCmdService = context.getBean(ShdCmdService.class);
		shdCmdService.outputUpdateAccount();
	}
}
