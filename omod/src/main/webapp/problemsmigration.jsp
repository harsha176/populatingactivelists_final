<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<h2><spring:message code="obsconverter.ProblemsMigrationTitle" /></h2>

This page lets you switch data from one question concept to another. 
<br/>
WARNING: You should be very careful with how you use this.  Changing data should ONLY be done in extreme circumstances.

<form method="POST">

	Select the Configured ProblemAdded ConceptId:
	<openmrs_tag:conceptField formFieldName="problemAddedConcept" />
	<br/>
	Select the configured ProblemResolved ConceptId:
	<openmrs_tag:conceptField formFieldName="problemRemovedConcept" />
	
	<input type="submit" />

</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>
