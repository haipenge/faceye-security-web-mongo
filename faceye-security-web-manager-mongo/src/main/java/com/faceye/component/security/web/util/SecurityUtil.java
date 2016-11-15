package com.faceye.component.security.web.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


/**
 * 安全认证工作类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月5日
 */
public class SecurityUtil {
	/**
	 * 取得当前登录用户名
	 * 需要HttpSessionEventPublisher支持
	 * @return
	 */
	public static String getCurrentUserName() {
		// String username = null;
		// if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
		// Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// if (null != obj) {
		// if (obj instanceof UserDetails) {
		// username = ((UserDetails) obj).getUsername();
		// } else {
		// username = obj.toString();
		// }
		// }
		// }
		// return username;

		Authentication authentication = getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}
		return authentication.getName();
	}

	/**
	 * 取得当前的登陆用户
	 * @todo
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年10月6日
	 */
	@SuppressWarnings("unchecked")
	public static <T extends User> T getCurrentUser() {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			return null;
		}

		return (T) principal;
	}

	/**
	 * 取得当前用户登录IP, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserIp() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return "";
		}
		Object details = authentication.getDetails();
		if (!(details instanceof WebAuthenticationDetails)) {
			return "";
		}
		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
		return webDetails.getRemoteAddress();
	}

	/**
	 * 将UserDetails保存到Security Context.
	 * 
	 * @param userDetails 已初始化好的用户信息.
	 * @param request 用于获取用户IP地址信息,可为Null.
	 */
	public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
				userDetails.getPassword(), userDetails.getAuthorities());
		if (request != null) {
			authentication.setDetails(new WebAuthenticationDetails(request));
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return null;
		}
		return context.getAuthentication();
	}
}
