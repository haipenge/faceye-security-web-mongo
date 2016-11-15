<%@ include file="/component/core/taglib/taglib.jsp"%>
<li><a href="#"><i class="fa fa-home"></i><span><fmt:message key="web.security.manager"></fmt:message></span></a>
						<ul class="sub-menu">
							<li class="<%=JspUtil.isActive(request, "/security/web/user")%>"><a href="<c:url value="/security/web/user/home"/>"><fmt:message
										key="security.web.user.manager"></fmt:message></a></li>
							<li class="<%=JspUtil.isActive(request, "/security/web/role")%>"><a href="<c:url value="/security/web/role/home"/>"><fmt:message
										key="security.web.role.manager"></fmt:message></a></li>
							<li class="<%=JspUtil.isActive(request, "/security/web/resource")%>"><a href="<c:url value="/security/web/resource/home"/>"><fmt:message
										key="security.web.resource.manager"></fmt:message></a></li>
							<li class="<%=JspUtil.isActive(request, "/security/web/menu")%>"><a href="/security/web/menu/home"><fmt:message
										key="security.web.menu.manager"></fmt:message></a></li>
						</ul></li>
