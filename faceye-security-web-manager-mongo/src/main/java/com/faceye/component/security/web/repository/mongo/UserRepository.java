package com.faceye.component.security.web.repository.mongo;

import org.springframework.stereotype.Repository;

import com.faceye.component.security.web.entity.User;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * User 实体DAO
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月20日
 */
@Repository("web-userRepository")
public interface UserRepository extends BaseMongoRepository<User,Long> {
	/**
	 * 根据用户名查找用户
	 * @todo
	 * @param username
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月21日
	 */
	public User getUserByUsername(String username);
	
	
	/**
	 * 根据电子邮件查询用户
	 * @todo
	 * @param email
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年6月21日
	 */
	public User getUserByEmail(String email);
	
	/**
	 * 根据weixin open id 取得用户
	 * @todo
	 * @param weixinOpenId
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年10月4日
	 */
	public User getUserByWeixinOpenId(String weixinOpenId);
	
}/**@generate-repository-source@**/
