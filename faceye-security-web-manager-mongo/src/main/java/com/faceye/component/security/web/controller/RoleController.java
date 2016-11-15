package com.faceye.component.security.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.security.web.entity.Resource;
import com.faceye.component.security.web.entity.Role;
import com.faceye.component.security.web.service.ResourceService;
import com.faceye.component.security.web.service.RoleService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;

@Controller("web-roleController")
@Scope("prototype")
@RequestMapping("/security/web/role")
public class RoleController extends BaseController<Role, Long, RoleService> {

	@Autowired
	private ResourceService resourceService=null;
	@Autowired
	public RoleController(RoleService service) {
		super(service);
	}

	/**
	 * 首页
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		Map searchParams=HttpUtil.getRequestParams(request);
		Page<Role> page = this.service.getPage(searchParams, this.getPage(searchParams), this.getSize(searchParams));
		model.addAttribute("page", page);
		return "security.web.role.manager";
	}

	/**
	 * 转向编辑或新增页面
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,Model model) {
		if(id!=null){
			Role entity=this.service.get(id);
			model.addAttribute("role", entity);
		}
		return "security.web.role.update";
	}
	
	/**
	 * 转向新增页面
	 * @todo
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月27日
	 */
	@RequestMapping(value="/input")
	public String input(Model model){
		return "security.web.role.update";
	}
	

	/**
	 * 数据保存
	 */
	@RequestMapping("/save")
	public String save(Role entity, RedirectAttributes redirectAttributes) {
		Role role=null;
		if(entity!=null && entity.getId()!=null){
			 role=this.service.get(entity.getId());
			 role.setName(entity.getName());
		}else{
			role=entity;
		}
		this.service.save(role);
		return "redirect:/security/web/role/home";
	}

	/**
	 * 数据删除
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年5月24日
	 */
	@RequestMapping("/remove/{id}")
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if(id!=null){
			this.service.remove(id);
		}
		return "redirect:/security/web/role/home";
	}
	/**
	 * 取得数据明细
	 * @todo
	 * @param id
	 * @param model
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年5月26日
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id,Model model){
		if(id!=null){
			Role entity=this.service.get(id);
			model.addAttribute("role", entity);
		}
		return "security.web.role.detail";
	}

	@RequestMapping("/authResources")
	public String authResources(@RequestParam("roleId") Long roleId,@RequestParam("resourceIds")Long[] resourceIds){
		this.service.saveRoleAuthResources(roleId, resourceIds);
		return "redirect:/security/web/role/home";
	}
	
	@RequestMapping("/prepareAuthResources/{id}")
	public String prepareAuthResources(@PathVariable("id") Long id,Model model){
		Role role=this.service.get(id);
        List<Resource> resources=this.resourceService.getAll();
        model.addAttribute("role", role);
        model.addAttribute("resources", resources);
		return "security.web.role.auth";
	}
	
	

}
