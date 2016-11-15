<%@ include file="/component/core/taglib/taglib.jsp"%>
<script type="text/javascript" src="<c:url value="/js/component/security/web/menu/menu.js"/>"></script>
<style>
.menu-checkbox {
	width: 25px;
}
</style>

<div class="page-head">
	<h2>
		<fmt:message key="security.web.user.auth.roles"></fmt:message>
	</h2>
</div>

<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<div class="content">Auth : ${role.name}</div>
			<form action="<c:url value="/security/web/menu/authMenus"/>" method="post" role="form" class="form-horizontal">
				<div classs="table-responsive">
					<input type="hidden" name="roleId" value="${role.id}">
					<table class="table table-bordered table-hover">
						<tr>
							<td></td>
							<td><fmt:message key='security.web.menu.name'></fmt:message></td>
							<td><fmt:message key="security.web.menu.url"></fmt:message>
						</tr>
						<c:forEach items="${roots}" var="root">
							<c:set var="isRootCheck" value="false" />
							<c:forEach items="${role.menuIds}" var="roleMenu">
								<c:if test="${root.id eq roleMenu}">
									<c:set var="isRootCheck" value="true" />
								</c:if>
							</c:forEach>
							<tr>
								<td class="menu-checkbox"><input type="checkbox" name="menuIds" value="${root.id}"
									<c:if test="${isRootCheck eq true}">checked</c:if>></td>
								<td>${root.name}</td>
							</tr>
							<c:forEach items="${menus}" var="menu">
								<c:if test="${root.id eq menu.parentId }">
									<c:set var="isChildCheck" value="false" />
									<c:forEach items="${role.menuIds}" var="roleMenu">
										<c:if test="${menu.id eq roleMenu}">
											<c:set var="isChildCheck" value="true" />
										</c:if>
									</c:forEach>
									<tr>
										<td></td>
										<td colspan="2">
											<table class="table table-bordered table-hover">
												<tr>
													<td class="menu-checkbox"><input type="checkbox" name="menuIds" value="${menu.id}"
														<c:if test="${isChildCheck eq true}">checked</c:if>></td>
													<td>${menu.name}</td>
												</tr>
											</table>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</c:forEach>
					</table>
				</div>
				<div class="content">
					<button type="submit" class="btn btn-primary">
						<fmt:message key="global.submit.save"></fmt:message>
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

