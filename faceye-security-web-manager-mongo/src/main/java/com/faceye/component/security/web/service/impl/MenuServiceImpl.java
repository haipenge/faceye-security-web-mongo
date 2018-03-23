package com.faceye.component.security.web.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faceye.component.security.web.entity.Menu;
import com.faceye.component.security.web.entity.Resource;
import com.faceye.component.security.web.entity.Role;
import com.faceye.component.security.web.repository.mongo.MenuRepository;
import com.faceye.component.security.web.service.MenuService;
import com.faceye.component.security.web.service.ResourceService;
import com.faceye.component.security.web.service.RoleService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
 

@Service("web-menuServiceImpl")
@Transactional
public class MenuServiceImpl extends BaseMongoServiceImpl<Menu, Long, MenuRepository> implements MenuService {
	@Autowired
	@Qualifier("web-roleServiceImpl")
	private RoleService roleService = null;
	@Autowired
	@Qualifier("web-resourceServiceImpl")
	private ResourceService resourceService = null;

	@Autowired
	public MenuServiceImpl(MenuRepository dao) {
		super(dao);
	}

	/**
	 * 删除一个菜单
	 */
	@Override
	public void remove(Long id)  {
		Menu menu = this.get(id);
		this.remove(menu);
	}

	@Override
	public void remove(Menu entity)  {
		// Resource resource=entity.getResource();
		// this.resourceService.remove(resource);
		// entity.setRoles(new HashSet<Role>(0));
		// this.save(entity);
		// entity.getRoles().clear();
		// entity.getChildren().clear();
		Resource resource = this.resourceService.getResourceByMenuId(entity.getId());
		List<Role> roles = this.roleService.getAll();
		if (CollectionUtils.isNotEmpty(roles)) {
			for (Role role : roles) {
//				Long[] resourceIds = role.getResourceIds();
				List<Resource> resources=role.getResources();
				Long[] menuIds = role.getMenuIds();
				boolean isReSave = false;
				for(Resource r:resources){
					if(r.getId().compareTo(resource.getId())==0){
						resources.remove(r);
						isReSave=true;
						break;
					}
				}
				
				if (ArrayUtils.contains(menuIds, entity.getId())) {
					menuIds=ArrayUtils.removeElement(menuIds, entity.getId());
					isReSave = true;
				}
				if (isReSave) {
					role.setMenuIds(menuIds);
					role.setResources(resources);
					this.roleService.save(role);
				}
			}
		}
		if (resource != null) {
			this.resourceService.remove(resource);
		}
		this.dao.delete(entity);

	}

	/**
	 * 角色-菜单授权
	 */
	@Override
	public void saveAuthRoles(Long roleId, Long[] menuIds) {
		Role role = this.roleService.get(roleId);
//		Long[] currentRoleResourceIds = role.getResourceIds();
		List<Resource> resourcesOfRole=role.getResources();
		Long[] currentMenuIds = role.getMenuIds();
		// Set<Menu> menus = role.getMenus();
		// if (menus == null) {
		// menus = new HashSet<Menu>(0);
		// }
		if (menuIds == null || menuIds.length == 0) {
			if (currentMenuIds != null && currentMenuIds.length > 0) {
				for (Long currentMenuId : currentMenuIds) {
					Resource resource = this.resourceService.getResourceByMenuId(currentMenuId);
					if (resource != null) {
						for(Resource r : resourcesOfRole){
							if(r.getId().compareTo(resource.getId())==0){
								resourcesOfRole.remove(r);
								break;
							}
						}
//						currentRoleResourceIds=ArrayUtils.removeElement(currentRoleResourceIds, resource.getId());
					}
				}
			}
		} else {
			for (Long currentMenuId : currentMenuIds) {
				boolean isRemoved = true;
				for (Long newMenuId : menuIds) {
					if (newMenuId.longValue() == currentMenuId.longValue()) {
						isRemoved = false;
						break;
					}
				}
				if (isRemoved) {
					Resource resource = this.resourceService.getResourceByMenuId(currentMenuId);
					if (resource != null) {
						for(Resource r : resourcesOfRole){
							if(r.getId().compareTo(resource.getId())==0){
								resourcesOfRole.remove(r);
								break;
							}
						}
//						currentRoleResourceIds=	ArrayUtils.removeElement(currentRoleResourceIds, resource.getId());
					}
				}
			}
		}
//		role.setResourceIds(currentRoleResourceIds);
		role.setResources(resourcesOfRole);
		role.setMenuIds(menuIds);
		this.roleService.save(role);
	}

	@Override
	public void saveMenu(Map params) {
		Long id = MapUtils.getLong(params, "id");
		Menu menu = null;
		Resource resource = null;
		if (id != null) {
			menu = this.get(id);
		} else {
			menu = new Menu();
		}
		menu.setName(MapUtils.getString(params, "name"));
		menu.setType(MapUtils.getInteger(params, "type"));
		String url = MapUtils.getString(params, "url");
		Long parentId = MapUtils.getLong(params, "parentId");

		if (StringUtils.isNotEmpty(url)) {
			resource = this.resourceService.getResourceByUrl(url);
		}
		if (resource == null) {
			resource = new Resource();
			resource.setName(menu.getName());
			resource.setUrl(url);
			this.resourceService.save(resource);
		}
//		menu.setResourceId(resource.getId());
		menu.setResource(resource);
		if (parentId != null) {
			Menu parent = this.get(parentId);
			if (parent != null) {
				menu.setParentId(parent.getId());
			}
		}
		this.save(menu);
	}

	@Override
	public List<Menu> getChildren(Long parentId) {
		return this.dao.getMenusByParentId(parentId);
	}

	@Override
	public List<Menu> getRoots() {
		return dao.getMenusByParentIdIsNull();
	}

}
/**@generate-service-source@**/
