<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/security/web/role/role.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/security/web/role/role.js"/>"></script>

<div class="page-head">
	<h2>
		<fmt:message key="security.web.role.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/security/web/role/input"/>"> <fmt:message key="security.web.role.add"></fmt:message>
		</a>
	</h2>
</div>
<div class="cl-mcont">
	<!-- 
	<div class="header">
		<h2>
			<fmt:message key="security.web.role.manager"></fmt:message>
		</h2>
		<a class="btn btn-default" href="<c:url value="/security/web/role/input"/>"> <fmt:message key="security.web.role.add"></fmt:message>
		</a>
	</div>
	 -->
	<div class="block-flat">
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><fmt:message key='security.web.role.name'></fmt:message></th>
							<th><fmt:message key="security.web.role.auth"></fmt:message></th>
							<th><fmt:message key="security.web.menu.auth"></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="role">
							<tr>
								<td>${role.name}</td>
								<td><a href="/security/web/role/prepareAuthResources/${role.id}"><fmt:message key="security.web.role.auth"></fmt:message></a></td>
								<td><a href="/security/web/menu/prepareAuthMenus/${role.id}"><fmt:message key="security.web.menu.auth"></fmt:message></a></td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/security/web/role/edit/${role.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/security/web/role/remove/${role.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/security/web/role/home" params="<%=request.getParameterMap()%>" />
		</div>
	</div>
</div>
