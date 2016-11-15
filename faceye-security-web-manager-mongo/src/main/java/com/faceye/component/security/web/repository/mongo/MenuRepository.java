package com.faceye.component.security.web.repository.mongo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.faceye.component.security.web.entity.Menu;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * Menu 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository("web-menuRepository")
public interface MenuRepository extends BaseMongoRepository<Menu,Long> {
	 
	/**
	 * 查询子节点
	 * @todo
	 * @param parentId
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月2日
	 */
	public List<Menu> getMenusByParentId(Long parentId);
	/**
	 * 查询根节点
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月2日
	 */
	public List<Menu> getMenusByParentIdIsNull();
	
	
//	public List<Menu> getMenusByIds(List<Long> ids);
	
	
	
}/**@generate-repository-source@**/
