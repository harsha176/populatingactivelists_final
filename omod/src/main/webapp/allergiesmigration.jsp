<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<h2><spring:message code="populatingactivelists.AllergiesMigrationTitle" /></h2>

<h3><spring:message code="populatingactivelists.switch" /></h3>
<br/>
<h3><spring:message code="populatingactivelists.warning" /></h3>
<form method="POST">

	Select the configured Allergies Added conceptId:
	<openmrs_tag:conceptField formFieldName="allergiesAddedConceptID" />
	<br/>
	Select the configured Allergies Removed conceptId:
	<openmrs_tag:conceptField formFieldName="allergiesRemovedConceptID" />
	
	<input type="submit" />

</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>