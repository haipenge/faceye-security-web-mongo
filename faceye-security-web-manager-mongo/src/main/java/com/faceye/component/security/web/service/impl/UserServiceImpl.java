package com.faceye.component.security.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.faceye.component.security.web.entity.Role;
import com.faceye.component.security.web.entity.User;
import com.faceye.component.security.web.repository.mongo.RoleRepository;
import com.faceye.component.security.web.repository.mongo.UserRepository;
import com.faceye.component.security.web.service.UserService;
import com.faceye.component.security.web.util.PasswordEncoder;
import com.faceye.component.security.web.util.SecurityUtil;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.RandUtil;

@Service("web-userServiceImpl")
public class UserServiceImpl extends BaseMongoServiceImpl<User, Long, UserRepository> implements UserService, UserDetailsService {
	// 注册用户的角色ID，为注册用户分配一个默认角色
	@Value("#{property['faceye.manager.user.register.role.id']}")
	private String registerRoleId = "";
	@Autowired
	@Qualifier("web-roleRepository")
	private RoleRepository roleRepository = null;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	@Qualifier("rememberMeServices")
	private RememberMeServices rememberMeServices = null;

	@Autowired
	public UserServiceImpl(UserRepository dao) {
		super(dao);
	}

	/**
	 * 加载用户,支持邮件和用户名登陆
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		if (StringUtils.isNotEmpty(username)) {
			username=username.toLowerCase();
			if (StringUtils.indexOf(username, "@") != -1) {
				user = this.dao.getUserByEmail(username);
			} else {
				user = this.dao.getUserByUsername(username);
			}
		}
		return user;
	}

	@Override
	public void saveUserAuthRoles(Long userId, Long[] roleIds) {
		User user = this.dao.findOne(userId);
		List<Role> roles = new ArrayList<Role>();
		// user.getRoles().clear();
		if (roleIds != null && roleIds.length > 0) {
			for (Long roleId : roleIds) {
				Role role = this.roleRepository.findOne(roleId);
				roles.add(role);
			}
			user.setRoles(roles);
		}
		this.save(user);
	}

	@Override
	public User getUserByUsername(String username) {
		if (StringUtils.isNotEmpty(username)) {
			username = username.toLowerCase();
			return this.dao.getUserByUsername(username);
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		if (StringUtils.isNotEmpty(email)) {
			email = email.toLowerCase();
		}
		return this.dao.getUserByEmail(email);
	}

	@Override
	public User getCurrentLoginUser() {
		User user = null;
		String username = SecurityUtil.getCurrentUserName();
		
		if (StringUtils.isNotEmpty(username)) {
			logger.debug(">>FaceYe --> Current login user name is:"+username);
			user = this.getUserByUsername(username);
		}else{
			logger.debug(">>FaceYe --> can not got login user name.");
		}
		return user;
	}

	@Override
	public User weixinOAuth2AndAutoLogin(HttpServletRequest request, HttpServletResponse response, String openid) {
		logger.debug(">>FaceYe do weixin user auto login,role id is:" + this.registerRoleId);
		if (authenticationManager == null) {
			logger.debug(">>FaceYE authenticationManager is null.");
		}
		if (this.rememberMeServices == null) {
			logger.debug(">>FaceYe remember me is null.");
		} else {
			logger.debug(">>FaceYe remember me class is:" + this.rememberMeServices.getClass().getName());
		}
		String password = "xidlskdk20";
		User user = this.dao.getUserByWeixinOpenId(openid);
		if (user == null) {
			String username=RandUtil.randString().toLowerCase();
			logger.debug(">>FaceYe user with openid " + openid + " is not exist.");
			Role role = this.roleRepository.findOne(Long.parseLong(this.registerRoleId));
			List<Role> roles = new ArrayList<Role>(0);
			roles.add(role);
			user = new User();
			user.setRoles(roles);
			user.setEncryptPassword(com.faceye.feature.util.security.EncryptUtil.encrypt(password));
			user.setWeixinOpenId(openid);
			user.setUsername(username);
			String encodingPassword = PasswordEncoder.encoder(password,username);
			user.setPassword(encodingPassword);
			user.setEnabled(true);
			this.save(user);
		} else {
			logger.debug(">>FaceYe --> User is Exist now ,user name is :" + user.getUsername());
		}
		this.autoLgoin(user, request);
		return user;
	}

	/**
	 * 用户自动登陆
	 * @todo
	 * @param user
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年10月6日
	 */
	public void autoLgoin(User user, HttpServletRequest request) {
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,
//				com.faceye.feature.util.security.EncryptUtil.decrypt(user.getEncryptPassword()), user.getAuthorities());
		request.getSession();
//		request.getParameterMap().put("remember-me", new String []{"t","r","u","e"});
//		token.setDetails(new WebAuthenticationDetails(request));
//		Authentication authenticatedUser = authenticationManager.authenticate(token);
//		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		SecurityUtil.saveUserDetailsToContext(user, request);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());
		// request.getParameterMap().put("remember-me", true);
		// logger.debug(">>FaceYe do user remember me.");
		// this.rememberMeServices.
		// this.rememberMeServices.loginSuccess(request, response, authenticatedUser);
	}

	@Override
	public User getUserByWeixinOpenId(String openid) {
		return this.dao.getUserByWeixinOpenId(openid);
	}
}
/**@generate-service-source@**/
