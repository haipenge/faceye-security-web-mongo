package com.faceye.test.component.security.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.AntPathMatcher;
import org.junit.Assert;
import org.springframework.util.PathMatcher;

@RunWith(JUnit4.class)
public class AntPathMatcherTestCase {
	private PathMatcher pathMatcher =null;
	@Before
	public void set(){
		pathMatcher = new AntPathMatcher();
	}
	@Test
	public void testMatcher(){
		String url="/order/order/home";
		String matcher="/order/order/home**";
		boolean res=pathMatcher.match(matcher, url);
		Assert.assertTrue(res);
	}

}
