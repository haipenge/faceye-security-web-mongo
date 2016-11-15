<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/security/web/resource/resource.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/security/web/resource/resource.js"/>"></script>
<div class="panel panel-primary">
	<div class="panel-heading">
		<div class="panel-title">
			<fmt:message key="security.web.resource.detail"></fmt:message>
		</div>
	</div>
	<div class="panel-body">
			<fieldset>
								<div class="form-group">
					<label class="col-md-4 control-label" for="resource">
					  <fmt:message key="security.web.resource.name"></fmt:message>
					 </label>
					<div class="col-md-4">
						${resource.name}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="resource">
					  <fmt:message key="security.web.resource.url"></fmt:message>
					 </label>
					<div class="col-md-4">
						${resource.url}
					</div>
				</div>
								<div class="form-group">
					<label class="col-md-4 control-label" for="resource">
					  <fmt:message key="security.web.resource.roles"></fmt:message>
					 </label>
					<div class="col-md-4">
						${resource.roles}
					</div>
				</div>
				<!--@generate-entity-jsp-property-detail@-->
				
				
				
			</fieldset>
	</div>
</div>