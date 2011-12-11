<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<h2><spring:message code="obsconverter.title" /></h2>
<h3><spring:message code="ProblemsMigration.title" /></h3>
<h3><spring:message code="lol.title" /></h3>
This page lets you switch data from one question concept to another. 
<br/>
WARNING: You should be very careful with how you use this.  Changing data should ONLY be done in extreme circumstances.

<form method="POST">

	Pick the concept to replace:
	<openmrs_tag:conceptField formFieldName="oldConceptId" />
	<br/>
	Pick the concept that is replacing it:
	<openmrs_tag:conceptField formFieldName="newConceptId" />
	
	<input type="submit" />

</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>
