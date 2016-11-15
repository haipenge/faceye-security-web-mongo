<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page language="java" import="java.util.*,com.faceye.feature.util.*,com.faceye.feature.util.host.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('input[name="j_username"]').focus();
	});
</script>
<div class="card">
  <div class="card-header">
    用户登陆
  </div>
  <div class="card-block">
<form class="form-horizontal" action="<c:url value="/j_spring_security_check"/>" method="POST" role="form">
					<div class="form-group">
						
						
							<input type="text" name="j_username" class="form-control"
								value="<c:if test="${not empty param.loginFailure}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>"
								class="security" placeholder="<fmt:message key="security.web.user.name"></fmt:message>" />
						
					</div>
					<div class="form-group">
						
						
							<input type="password" name="j_password" class="form-control"
								placeholder="<fmt:message  key="security.web.user.password"></fmt:message>">
						
					</div>
					<div class="form-group">
						
							<!--  _spring_security_remember_me-->
							<div class="checkbox">
								<label> <input type="checkbox" class="icheck" name="remember-me" value="true" checked /> <fmt:message
										key="security.web.user.remember.me"></fmt:message>
								</label>
							</div>
						
					</div>
					<div class="form-group">
						
							<button type="submit" class="btn btn-primary btn-block">
								<fmt:message key="security.web.user.login"></fmt:message>
							</button>
							<!-- 
						<button type="submit" class="btn btn-primary">Registrer</button>
						<button class="btn btn-default">Cancel</button>
						-->
						
					</div>
				</form>
			</div>
</div>