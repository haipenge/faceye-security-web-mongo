<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/security/web/menu/menu.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/security/web/menu/menu.js"/>"></script>

<div class="page-head">
	<h2>
		<fmt:message key="security.web.menu.manager"></fmt:message>
		<a class="btn btn-primary" href="<c:url value="/security/web/menu/input"/>"> <fmt:message key="security.web.menu.add"></fmt:message>
		</a>
	</h2>
</div>

<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<div classs="table-responsive">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><fmt:message key='security.web.menu.name'></fmt:message></td>
							<th><fmt:message key='security.web.menu.url'></fmt:message></th>
							<th><fmt:message key='security.web.menu.type'></fmt:message></th>
							<!--@generate-entity-jsp-property-desc@-->
							<th><fmt:message key="global.edit"></fmt:message></th>
							<th><fmt:message key="global.remove"></fmt:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="menu">
							<tr>
								<td>${menu.name}</td>
								<td>${menu.resource.url}</td>
								<td><c:if test="${menu.type eq '1'}">URL</c:if> <c:if test="${menu.type eq '2' }">Ajax</c:if></td>
								<!--@generate-entity-jsp-property-value@-->
								<td><a href="<c:url value="/security/web/menu/edit/${menu.id}"/>"> <fmt:message key="global.edit"></fmt:message>
								</a></td>
								<td><a href="<c:url value="/security/web/menu/remove/${menu.id}"/>"> <fmt:message key="global.remove"></fmt:message>
								</a></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<f:page page="${page}" url="/security/web/menu/home" params="<%=request.getParameterMap()%>" />
		</div>

	</div>
</div>