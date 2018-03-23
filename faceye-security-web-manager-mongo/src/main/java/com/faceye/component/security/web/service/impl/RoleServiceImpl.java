package com.faceye.component.security.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faceye.component.security.web.entity.Resource;
import com.faceye.component.security.web.entity.Role;
import com.faceye.component.security.web.entity.User;
import com.faceye.component.security.web.repository.mongo.ResourceRepository;
import com.faceye.component.security.web.repository.mongo.RoleRepository;
import com.faceye.component.security.web.repository.mongo.UserRepository;
import com.faceye.component.security.web.service.RoleService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
 

/**
 * 角色服务类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年6月27日
 */
@Service("web-roleServiceImpl")
@Transactional
public class RoleServiceImpl extends BaseMongoServiceImpl<Role, Long, RoleRepository> implements RoleService {

	@Autowired
	@Qualifier("web-resourceRepository")
	private ResourceRepository resourceRepository = null;
	@Autowired
	@Qualifier("web-userRepository")
	private UserRepository userRepository = null;

	@Autowired
	public RoleServiceImpl(RoleRepository dao) {
		super(dao);
	}

	@Override
	public void remove(Long id)  {
		Role role = this.get(id);
		this.remove(role);
	}

	@Override
	public void remove(Role entity)  {
		List<User> users = this.userRepository.findAll();
		if (CollectionUtils.isNotEmpty(users)) {
			for (User user : users) {
				List<Role> roles = user.getRoles();
				for (Role role : roles) {
					if (role.getId().compareTo(entity.getId()) == 0) {
						roles.remove(role);
						break;
					}
				}
				user.setRoles(roles);
				this.userRepository.save(user);
			}
		}
		dao.delete(entity);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		List<Role> allRoles = this.dao.findAll();
		if (null != allRoles && CollectionUtils.isNotEmpty(allRoles)) {
			for (Role role : allRoles) {
				ConfigAttribute ca = new SecurityConfig(role.getRoleAuth());
				allAttributes.add(ca);
			}
		}
		return allAttributes;
	}

	@Override
	public void saveRoleAuthResources(Long roleId, Long[] resourceIds) {
		Role role = this.get(roleId);
		List<Resource> resources=new ArrayList<Resource>(0);
		if (resourceIds != null && resourceIds.length > 0) {
			for (Long resourceId : resourceIds) {
				Resource resource = this.resourceRepository.findById(resourceId).get();
				resources.add(resource);
			}
		}
		role.setResources(resources);
		this.save(role);
	}

	@Override
	public Role getRoleByName(String name) {
		return dao.getRoleByName(name);
	}

}
/**@generate-service-source@**/
