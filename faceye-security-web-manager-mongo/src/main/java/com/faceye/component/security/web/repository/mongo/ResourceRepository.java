package com.faceye.component.security.web.repository.mongo;

import org.springframework.stereotype.Repository;

import com.faceye.component.security.web.entity.Resource;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Resource 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository("web-resourceRepository")
public interface ResourceRepository extends BaseMongoRepository<Resource,Long> {
	
	/**
	 * 根据URL地址取得Resource 对像
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月1日
	 */
	public Resource getResourceByUrl(String url);
	
	
	/**
	 * 根据菜单ID取得Resource
	 * @todo
	 * @param menuId
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月14日
	 */
	public Resource getResourceByMenuId(Long menuId);
	
}/**@generate-repository-source@**/
