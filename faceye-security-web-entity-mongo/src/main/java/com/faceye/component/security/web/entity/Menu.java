package com.faceye.component.security.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Menu ORM 实体
 * 数据库表:security_menu
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */

@Document(collection = "security_web_menu")
public class Menu implements Serializable {

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
	 * 对应的Resource Id
	 */
	@DBRef
	private Resource resource=null;
	


	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	    * 说明:链接类型
	    * 属性名: type
	    * 1->URL(跳转),2->Ajax(异步加载
	    * 类型: Integer
	    * 数据库字段:type
	    * @author haipenge
	    * 
	    */
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 父节点ID
	 */
	private Long parentId = null;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
/**@generate-entity-source@**/

