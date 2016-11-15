<%@ include file="/component/core/taglib/taglib.jsp"%>
<script type="text/javascript" src="<c:url value="/js/component/security/web/user/register.js"/>"></script>
<div class="page-head">
	<h2>
		<fmt:message key="security.web.user.register"></fmt:message>

	</h2>
</div>
<div class="cl-mcont">
	<div class="block-flat">
		<div class="content">
			<form action="<c:url value="/security/web/user/doRegister"/>" method="post" class="form-horizontal" role="form"
				id="user-register-form">
				<fieldset>
					<div class="form-group">
						<label class="col-md-4 control-label" for="username"> <fmt:message key="security.web.user.name"></fmt:message>
						</label>
						<div class="col-md-4">
							<input type="text" name="username" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="password"> <fmt:message key="security.web.user.password"></fmt:message>
						</label>
						<div class="col-md-4">
							<input type="password" name="password" class="form-control" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label" for="repassword"> <fmt:message key="security.web.user.repassword"></fmt:message>
						</label>
						<div class="col-md-4">
							<input type="password" name="repassword" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="email"> <fmt:message key="security.web.user.email"></fmt:message>
						</label>
						<div class="col-md-4">
							<input type="text" name="email" class="form-control" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-md-8 pull-right">
							<button type="button" id="register-button" class="btn btn-primary">
								<fmt:message key="global.submit"></fmt:message>
							</button>
							<button type="button" class="btn btn-warning">
								<fmt:message key="global.cancel"></fmt:message>
							</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>


