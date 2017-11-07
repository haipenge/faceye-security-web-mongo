package com.faceye.component.security.web.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class PasswordEncoder {

	public static String encoder(String password,String salt) {
		String res = "";
		Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		passwordEncoder.setEncodeHashAsBase64(false);
		res = passwordEncoder.encodePassword(password, salt);
		return res;
	}
	public static void main(String asrg[]) throws Exception{
		System.out.println(PasswordEncoder.encoder("haipenge","haipenge"));
	}
	
}
