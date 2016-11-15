package com.faceye.component.security.web.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Id;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User ORM 实体
 * 数据库表:security_user
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月21日
 */
@Document(collection = "security_web_user")
public class User implements UserDetails {

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
	 * 说明:用户名
	 * 属性名: name
	 * 类型: String
	 * 数据库字段:name
	 * @author haipenge
	 */
	private String username;

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	/**
	 * 说明:密码
	 * 属性名: password
	 * 类型: String
	 * 数据库字段:password
	 * @author haipenge
	 */
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 说明:电子邮件
	 * 属性名: email
	 * 类型: String
	 * 数据库字段:email
	 * @author haipenge
	 */
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 说明:是否可用
	 * 属性名: enabled
	 * 类型: boolean
	 * 数据库字段:enabled
	 * @author haipenge
	 */
	private boolean enabled = true;

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 说明:是否过期
	 * 属性名: accountExpired
	 * 类型: boolean
	 * 数据库字段:account_expired
	 * @author haipenge
	 */
	private boolean accountExpired = false;

	public boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	/**
	 * 说明:是否锁定
	 * 属性名: accountLocked
	 * 类型: boolean
	 * 数据库字段:account_locked
	 * @author haipenge
	 */
	private boolean accountLocked;

	public boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	/**
	 * 说明:身份是否过期
	 * 属性名: credentialsExpired
	 * 类型: boolean
	 * 数据库字段:credentials_expired
	 * @author haipenge
	 */
	private boolean credentialsExpired;

	public boolean getCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Set<Role> roles = this.getRoles();
		// Long[] roleIds=this.getRoles();
		List<Role> roles = this.getRoles();
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if (CollectionUtils.isNotEmpty(roles)) {
			for (Role role : roles) {
				SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getId());
				grantedAuthorities.add(grantedAuthority);
			}
		}
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.getAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.getAccountLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.getCredentialsExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.getEnabled();
	}

	// //////////////////////////////////////////////////////////

	/**
	 * 说明:角色
	 * 属性名: roles
	 * 类型: Set<Role>
	 * 数据库字段:role_id
	 * @author haipenge
	 */
	@DBRef
	private List<Role> roles = null;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	// private Long [] roleIds= null;
	//
	// public Long[] getRoleIds() {
	// return roleIds;
	// }
	//
	// public void setRoleIds(Long[] roleIds) {
	// this.roleIds = roleIds;
	// }
	/**
	 * 微信openid
	 */
	private String weixinOpenId="";

	public String getWeixinOpenId() {
		return weixinOpenId;
	}

	public void setWeixinOpenId(String weixinOpenId) {
		this.weixinOpenId = weixinOpenId;
	}
	/**
	 * 加密密码
	 */
	private String encryptPassword="";

	public String getEncryptPassword() {
		return encryptPassword;
	}

	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}
	
	

}
/**@generate-entity-source@**/

