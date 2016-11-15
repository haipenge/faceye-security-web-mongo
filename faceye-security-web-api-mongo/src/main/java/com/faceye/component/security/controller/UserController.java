package com.faceye.component.security.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.security.entity.Role;
import com.faceye.component.security.entity.User;
import com.faceye.component.security.service.RoleService;
import com.faceye.component.security.service.UserService;
import com.faceye.component.security.util.PasswordEncoder;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/security/user")
public class UserController extends BaseController<User, Long, UserService> {
	// 注册用户的角色ID，为注册用户分配一个默认角色
	@Value("#{property['faceye.manager.user.register.role.id']}")
	private String registerRoleId = "";

	@Autowired
	private RoleService roleService = null;

	@Autowired
	public UserController(UserService service) {
		super(service);
	}

	/**
	 * 首页
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		Map searchParams = HttpUtil.getRequestParams(request);
		Page<User> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		return "security.user.manager";
	}

	/**
	 * 转向编辑或新增页面
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		if (id != null) {
			User entity = this.service.get(id);
			model.addAttribute("user", entity);
		}
		return "security.user.update";
	}
	@RequestMapping("/profile/{username}")
	public String profile(@PathVariable("username")String username,Model model){
		if(StringUtils.isNotEmpty(username)){
			User entity=this.service.getUserByUsername(username);
			model.addAttribute("user", entity);
		}
		return "security.user.update";
	}

	/**
	 * 转向新增页面
	 * @todo
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月27日
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) {
		return "security.user.update";
	}

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(User entity, RedirectAttributes redirectAttributes) {
		String password = entity.getPassword();
		entity.setUsername(entity.getUsername().toLowerCase());
		entity.setEmail(entity.getEmail().toLowerCase());
		String encodingPassword = PasswordEncoder.encoder(password);
		if (entity.getId() == null) {
			entity.setPassword(encodingPassword);
			Role role = this.roleService.get(Long.parseLong(registerRoleId));
			List<Role> roles=new ArrayList<Role>();
			if(role!=null){
				roles.add(role);
				entity.setRoles(roles);
			}
			entity.setEnabled(true);
			this.service.save(entity);
		} else {
			User user = this.service.get(entity.getId());
			if (!password.equals(user.getPassword())) {
				user.setPassword(encodingPassword);
			}
			user.setEmail(entity.getEmail());
			this.service.save(user);
		}
		
		return "redirect:/security/user/home";
	}

	@RequestMapping("/doRegister")
	public String doRegister(User entity) {
		String password = entity.getPassword();
		entity.setUsername(entity.getUsername().toLowerCase());
		entity.setEmail(entity.getEmail().toLowerCase());
		String encodingPassword = PasswordEncoder.encoder(password);
		if (entity.getId() == null) {
			entity.setPassword(encodingPassword);
			Role role = this.roleService.get(Long.parseLong(registerRoleId));
			List<Role>roles =new ArrayList<Role>();
			roles.add(role);
			entity.setRoles(roles);
		} else {
			User user = this.service.get(entity.getId());
			if (!password.equals(user.getPassword())) {
				entity.setPassword(encodingPassword);
			}
		}
		entity.setEnabled(true);
		this.service.save(entity);
		return "redirect:/login";
	}

	/**
	 * 数据删除
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/remove/{id}")
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if (id != null) {
			this.service.remove(id);
		}
		return "redirect:/security/user/home";
	}

	/**
	 * 取得数据明细
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月26日
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		if (id != null) {
			User entity = this.service.get(id);
			model.addAttribute("user", entity);
		}
		return "security.user.detail";
	}

	/**
	 * 给用户授权
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月23日
	 */
	@RequestMapping(value = "/authRoles")
	public String authRoles(HttpServletRequest request, @RequestParam Long userId, @RequestParam Long[] roleIds) {
		this.service.saveUserAuthRoles(userId, roleIds);
		return "default.msg";
	}

	/**
	 * 准备为用户授于角色权限
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月27日
	 */
	@RequestMapping("/prepareAuthRoles/{id}")
	public String perpareAuthRoles(@PathVariable Long id, Model model) {
		User user = this.service.get(id);
		List<Role> roles = this.roleService.getAll();
		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		model.addAttribute("existRoles", user.getRoles());
		return "security.user.perepare.auth";
	}
	
	

	/**
	 * 判断用户注册时用户名与电子邮件是否可用
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月27日
	 */
	@RequestMapping("/isUserNameAndEmailExist")
	@ResponseBody
	public UsernameAndPasswordValidator isUsernameAndEmailExist(@RequestParam("username") String username,
			@RequestParam("email") String email) {
		UsernameAndPasswordValidator validator = new UsernameAndPasswordValidator();
		User user = this.service.getUserByUsername(username);
		if (user != null) {
			validator.setIsUsernameExist(Boolean.TRUE);
		}
		user = null;
		user = this.service.getUserByEmail(email);
		if (user != null) {
			validator.setIsEmailExist(Boolean.TRUE);
		}
		return validator;
	}
	@RequestMapping("/cookies")
	@ResponseBody
	public String cookies(HttpServletRequest request,HttpServletResponse response){
		String cookies="";
		Cookie cookie[] =request.getCookies();
		if(cookie!=null){
			for(Cookie c:cookie){
				String k=c.getName();
				String v =c.getValue();
				if(StringUtils.isNotEmpty(k)){
					cookies+=k;
				}
				if(StringUtils.isNotEmpty(v)){
					cookies+="|";
					cookies+=v;
				}
			}
		}
		return "{\'cookies\':\'"+cookies+"\'}";
	}

	/**
	 * 用户名与邮箱唯一性校验
	 * @author @haipenge 
	 * haipenge@gmail.com
	*  Create Date:2014年6月27日
	 */
	class UsernameAndPasswordValidator {
		private Boolean isUsernameExist = Boolean.FALSE;
		private Boolean isEmailExist = Boolean.FALSE;

		public Boolean getIsUsernameExist() {
			return isUsernameExist;
		}

		public void setIsUsernameExist(Boolean isUsernameExist) {
			this.isUsernameExist = isUsernameExist;
		}

		public Boolean getIsEmailExist() {
			return isEmailExist;
		}

		public void setIsEmailExist(Boolean isEmailExist) {
			this.isEmailExist = isEmailExist;
		}

	}

}
