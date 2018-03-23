package com.faceye.component.security.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.security.web.entity.Menu;
import com.faceye.component.security.web.entity.Resource;
import com.faceye.component.security.web.entity.Role;
import com.faceye.component.security.web.entity.User;
import com.faceye.component.security.web.service.MenuService;
import com.faceye.component.security.web.service.ResourceService;
import com.faceye.component.security.web.service.RoleService;
import com.faceye.component.security.web.service.SecurityInitService;
import com.faceye.component.security.web.service.UserService;
import com.faceye.component.security.web.util.PasswordEncoderUtil;

@Service("web-securityInitServiceImpl")
public class SecurityInitServiceImpl implements SecurityInitService {

	@Autowired
	@Qualifier("web-roleServiceImpl")
	private RoleService roleService = null;
	@Autowired
	@Qualifier("web-userServiceImpl")
	private UserService userService = null;
	@Autowired
	@Qualifier("web-resourceServiceImpl")
	private ResourceService resourceService = null;
	@Autowired
	@Qualifier("web-menuServiceImpl")
	private MenuService menuService = null;
	private String[] roleNames = new String[] { "管理员", "用户" };
	private String[] userNames = new String[] { "admin" };
	private String[] resourceUrls = new String[] { "/default/", "/security/", "/parse/", "/search/", "/index/", "/push/", "/spider/" };
	private String menus[][] = new String[][] { { "角色管理", "/security/web/role/home" }, { "用户管理", "/security/web/user/home" },
			{ "资源管理", "/security/web/resource/home" }, { "菜单管理", "/security/web/menu/home" } };

	@Override
	public boolean isInited() {
		boolean res = false;
		List<User> users = this.userService.getPage(null, 1, 10).getContent();
		List<Role> roles = this.roleService.getPage(null, 1, 10).getContent();
		List<Menu> menus = this.menuService.getPage(null, 1, 10).getContent();
		List<Resource> resources = this.resourceService.getPage(null, 1, 10).getContent();
		if (CollectionUtils.isNotEmpty(users) && CollectionUtils.isNotEmpty(roles) && CollectionUtils.isNotEmpty(menus)
				&& CollectionUtils.isNotEmpty(resources)) {
			res = true;
		}
		return res;
	}

	@Override
	public void init() {
		boolean isInit=this.isInited();
		if(!isInit){
		this.initRoles();
		this.initUsers();
		this.initResources();
		this.initMenus();
		}
	}

	/**
	 * 初始化角色
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月15日
	 */
	private void initRoles() {
		for (String name : roleNames) {
			Map searchParams = new HashMap();
			searchParams.put("EQ|name", name);
			Page<Role> roles = this.roleService.getPage(searchParams, 1, 10);
			if (roles == null || CollectionUtils.isEmpty(roles.getContent())) {
				Role role = new Role();
				role.setName(name);
				this.roleService.save(role);
			}
		}
	}

	/**
	 * 初始化用户
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月15日
	 */
	private void initUsers() {
		for (String username : userNames) {
			Map searchParams = new HashMap();
			searchParams.put("EQ|username", username);
			Page<User> users = this.userService.getPage(searchParams, 1, 5);
			if (users == null || CollectionUtils.isEmpty(users.getContent())) {
				String encodingPassword = PasswordEncoderUtil.encoder("admin");
				List<Role> roles = new ArrayList<Role>(0);
				User user = new User();
				user.setUsername("admin");
				user.setEnabled(true);
				user.setPassword(encodingPassword);
				Role role = this.roleService.getRoleByName(roleNames[0]);
				roles.add(role);
				user.setRoles(roles);
				this.userService.save(user);
			}
		}
	}

	/**
	 * 初始化链接资源
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月15日
	 */
	private void initResources() {
		for (String url : resourceUrls) {
			this.initResource(url);
		}
	}

	private Resource initResource(String url) {
		Resource resource = null;
		Map searchParams = new HashMap();
		searchParams.put("EQ|url", url);
		Page<Resource> resources = this.resourceService.getPage(searchParams, 1, 5);
		if (resources == null || CollectionUtils.isEmpty(resources.getContent())) {
			resource = new Resource();
			resource.setName(url);
			resource.setUrl(url);
			this.resourceService.save(resource);
			Role role = this.roleService.getRoleByName(roleNames[0]);
			List<Resource> resourcesOfRole = role.getResources();
			if (CollectionUtils.isNotEmpty(resourcesOfRole)) {
				resourcesOfRole.add(resource);
			} else {
				resourcesOfRole = new ArrayList<Resource>();
				resourcesOfRole.add(resource);
			}
			role.setResources(resourcesOfRole);
			this.roleService.save(role);
		} else {
			resource = resources.getContent().get(0);
		}
		return resource;
	}

	/**
	 * 初始化菜单
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月15日
	 */
	private void initMenus() {
		Long[] menuIds = null;
		Role role = null;
		Menu root = this.initRoot("系统管理");
		ArrayUtils.add(menuIds, root.getId());
		for (String[] menu : menus) {

			String name = menu[0];
			String url = menu[1];
			Map searchParams = new HashMap();
			searchParams.put("EQ|name", name);
			Page<Menu> page = this.menuService.getPage(searchParams, 1, 5);
			if (null == page || CollectionUtils.isEmpty(page.getContent())) {
				Resource resource = this.initResource(url);
				Menu m = new Menu();
				m.setName(name);
				m.setType(1);
				m.setParentId(root.getId());
				// m.setResourceId(resource.getId());
				m.setResource(resource);
				this.menuService.save(m);
				role = this.roleService.getRoleByName(roleNames[0]);
				menuIds = role.getMenuIds();
				if (!ArrayUtils.contains(menuIds, root.getId())) {
					menuIds = ArrayUtils.add(menuIds, root.getId());
				}
				menuIds = ArrayUtils.add(menuIds, m.getId());
				role.setMenuIds(menuIds);
				this.roleService.save(role);
			}
		}

	}

	/**
	 * 初始化根节点
	 * @todo
	 * @param name
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月15日
	 */
	private Menu initRoot(String name) {
		Menu menu = null;
		Map searchParams = new HashMap();
		searchParams.put("EQ|name", name);
		Page<Menu> page = this.menuService.getPage(searchParams, 1, 5);
		if (null == page || CollectionUtils.isEmpty(page.getContent())) {
			menu = new Menu();
			menu.setName(name);
			menu.setParentId(null);
			menu.setType(1);
			this.menuService.save(menu);
		} else {
			menu = page.getContent().get(0);
		}
		return menu;
	}

	@Override
	public void clear() {
		this.menuService.removeAll();
		this.roleService.removeAll();
		this.userService.removeAll();
		this.resourceService.removeAll();
	}

	@Override
	public void reInit() {
		this.clear();
		this.init();
	}

}
