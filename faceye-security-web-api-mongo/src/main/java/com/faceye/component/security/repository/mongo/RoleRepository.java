package com.faceye.component.security.repository.mongo;

import com.faceye.component.security.entity.Role;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Role 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
public interface RoleRepository extends BaseMongoRepository<Role,Long> {
	
	public Role getRoleByName(String name);
}/**@generate-repository-source@**/
