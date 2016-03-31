package com.shd.core.cmd;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.shd.core.service.ShdCmdService;




@Component
public class ComposeADAccountCmd {


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ApplicationContext context = 
            new ClassPathXmlApplicationContext("classpath:spring-core.xml");
		ShdCmdService shdCmdService = context.getBean(ShdCmdService.class);
		shdCmdService.composeADAccount();
	}
}
