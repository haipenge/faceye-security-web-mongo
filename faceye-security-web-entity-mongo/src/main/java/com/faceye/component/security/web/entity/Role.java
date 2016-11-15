package com.faceye.component.security.web.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Role ORM 实体
 * 数据库表:security_role
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */

@Document(collection = "security_web_role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926119711730830203L;
	@Id
	private Long id = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 说明:色名
	 * 属性名: name
	 * 类型: String
	 * 数据库字段:name
	 * @author haipenge
	 */
	@Column(name = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 角色可以访问的资源链接
	 */
	@DBRef
	private List<Resource> resources=new ArrayList<Resource>(0);
	
//	private Long[] resourceIds=null;
//	
//	public Long[] getResourceIds() {
//		return resourceIds;
//	}
//
//	public void setResourceIds(Long[] resourceIds) {
//		this.resourceIds = resourceIds;
//	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public String getRoleAuth() {
		return "ROLE_" + id;
	}

	/**
	 * 可用菜单
	 */
	private Long[] menuIds = null;

	public Long[] getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Long[] menuIds) {
		this.menuIds = menuIds;
	}

}
/**@generate-entity-source@**/

