package com.faceye.component.security.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.faceye.component.security.web.entity.User;
import com.faceye.feature.service.BaseService;

public interface UserService extends BaseService<User, Long> {

	/**
	 * 为用户授权
	 * @todo
	 * @param userId
	 * @param roleIds
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月23日
	 */
	public void saveUserAuthRoles(Long userId, Long[] roleIds);

	/**
	 * 根据用户名取得用户
	 * @todo
	 * @param username
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月27日
	 */
	public User getUserByUsername(String username);

	/**
	 * 根据电子邮件取得用户
	 * @todo
	 * @param email
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月27日
	 */
	public User getUserByEmail(String email);

	/**
	 * 取得当前登陆用户
	 * @todo
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年6月13日
	 */

	public User getCurrentLoginUser();
	
	/**
	 * 通过微信openid取得用户
	 * @todo
	 * @param openid
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年10月5日
	 */
	public User getUserByWeixinOpenId(String openid);
	
	/**
	 * 微信使用openid自动注册用户并登陆
	 * @todo
	 * @param openid
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年10月4日
	 */
	public User weixinOAuth2AndAutoLogin(HttpServletRequest request,HttpServletResponse response,String openid);

}
/**@generate-service-source@**/
