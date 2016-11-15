package com.faceye.component.security.web.entity;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Resource ORM 实体
 * 数据库表:security_resource
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection = "security_web_resource")
public class Resource implements Serializable {
	
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
	 * 说明:名称
	 * 属性名: name
	 * 类型: String
	 * 数据库字段:name
	 * @author haipenge
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 说明:地址
	 * 属性名: url
	 * 类型: String
	 * 数据库字段:url
	 * @author haipenge
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	/**
	 * 当前URL对应的菜单 ,注，一对一
	 */
	private Long menuId = null;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
/**@generate-entity-source@**/

