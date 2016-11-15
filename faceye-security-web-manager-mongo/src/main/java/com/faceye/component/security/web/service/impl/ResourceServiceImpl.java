package com.faceye.component.security.web.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.faceye.component.security.web.entity.Resource;
import com.faceye.component.security.web.entity.Role;
import com.faceye.component.security.web.repository.mongo.ResourceRepository;
import com.faceye.component.security.web.service.ResourceService;
import com.faceye.component.security.web.service.RoleService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.ServiceException;

@Service("web-resourceServiceImpl")
public class ResourceServiceImpl extends BaseMongoServiceImpl<Resource, Long, ResourceRepository> implements ResourceService,
		FilterInvocationSecurityMetadataSource {
	private PathMatcher pathMatcher = new AntPathMatcher();
	@Autowired
	@Qualifier("web-roleServiceImpl")
	private RoleService roleService = null;

	@Autowired
	public ResourceServiceImpl(ResourceRepository dao) {
		super(dao);
	}

	@Override
	public void remove(Long id) throws ServiceException {
		Resource resource = this.get(id);
		List<Role> roles = this.roleService.getAll();
		if (CollectionUtils.isNotEmpty(roles) && resource != null) {
			for (Role role : roles) {
				List<Resource> resources = role.getResources();
				if (CollectionUtils.isNotEmpty(resources)) {
					for (Resource r : resources) {
						if (r != null) {
							if (r.getId().compareTo(resource.getId()) == 0) {
								resources.remove(r);
							}
						}
					}
				}
				role.setResources(resources);
				this.roleService.save(role);
			}
			this.dao.delete(resource);
		}

	}

	@Override
	public void remove(Resource entity) throws ServiceException {
		this.remove(entity.getId());
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
//		logger.debug(">>FaceYe -->Security-->,filter url is:" + url);
		List<Resource> resources = this.dao.findAll();
		if (CollectionUtils.isNotEmpty(resources)) {
			for (Resource r : resources) {
				String _url = r.getUrl();
				if (StringUtils.isNotEmpty(_url) && !_url.endsWith("\\*")) {
					_url += "**";
				}
				boolean isMatcher = pathMatcher.match(_url, url);
//				logger.debug(">>Matcher result is:" + isMatcher);
				if (isMatcher) {
					// return r.getAttributes();
					return this.getAttributesByResource(r);
				}
			}
		} else {
			logger.debug(">>FaceYe resource is empty.");
		}
		return null;
	}

	/**
	 * 取得访问某一资源需要的角色
	 * @todo
	 * @param resource
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月14日
	 */
	private Collection<ConfigAttribute> getAttributesByResource(Resource resource) {
		Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
		List<Role> roles = this.roleService.getAll();
		if (CollectionUtils.isNotEmpty(roles)) {
			for (Role role : roles) {
				List<Resource> resources = role.getResources();
				if (CollectionUtils.isNotEmpty(resources)) {
					for (Resource r : resources) {
						if (r != null) {
							if (r.getId().compareTo(resource.getId()) == 0) {
								ConfigAttribute ca = new SecurityConfig(role.getRoleAuth());
								logger.debug(">>FaceYe got ca :" + role.getRoleAuth());
								attributes.add(ca);
							}
						}
					}
				}
			}
		}
		return attributes;
	}

	// public Collection<ConfigAttribute> getAttributes() {
	// Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
	// if (null != this.getRoles()) {
	// Iterator it = this.getRoles().iterator();
	// while (it.hasNext()) {
	// Role role = (Role) it.next();
	// ConfigAttribute ca = new SecurityConfig(role.getRoleAuth());
	// attributes.add(ca);
	// }
	// }
	// return attributes;
	// }

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Collection<ConfigAttribute> allAttributes = null;
		allAttributes = this.roleService.getAllConfigAttributes();
		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public Resource getResourceByUrl(String url) {
		return this.dao.getResourceByUrl(url);
	}

	@Override
	public Resource getResourceByMenuId(Long menuId) {
		return this.dao.getResourceByMenuId(menuId);
	}

}
/**@generate-service-source@**/
