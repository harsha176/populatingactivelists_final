/**
 * 
 */
package org.openmrs.module.populatinglists.controller.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptName;
import org.openmrs.Encounter;
import org.openmrs.Field;
import org.openmrs.FieldAnswer;
import org.openmrs.Form;
import org.openmrs.FormField;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonName;
import org.openmrs.activelist.ActiveListItem;
import org.openmrs.activelist.ActiveListType;
import org.openmrs.activelist.Allergy;
import org.openmrs.activelist.Problem;
import org.openmrs.api.ActiveListService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.populatingactivelists.web.controller.ProblemsMigrationController;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author ravi
 * 
 */
public class ProblemsMigrationTest extends BaseModuleContextSensitiveTest {

	protected ConceptService conceptService = null;
	protected static final String INITIAL_CONCEPTS_XML = "ProblemMigrationTestXML.xml";
	PatientService patient_service;
	ActiveListService active_list_service;
	ObsService obs_service;

	// private static ObsService obsSer

	// protected static final String GET_CONCEPTS_BY_SET_XML =
	// "org/openmrs/api/include/ConceptServiceTest-getConceptsBySet.xml";
	/**
	 * @throws java.lang.Exception
	 */
	@Override
	public Boolean useInMemoryDatabase() {
		return true;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * This method initializes spring context for executing test cases. It
	 * mainly initializes patient, active list and observation service objects
	 * which are used in all the test cases.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		conceptService = Context.getConceptService();
		initializeInMemoryDatabase();
		executeDataSet(INITIAL_CONCEPTS_XML);
		authenticate();

		ProblemsMigrationController pmc = new ProblemsMigrationController();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"admin/obsconverter/problemsmigration.form");
		MockHttpSession mockHttpSession = new MockHttpSession(null);
		request.setSession(mockHttpSession);
		pmc.afterPageSubmission(6042, 6057, mockHttpSession);

		patient_service = Context.getPatientService();
		active_list_service = Context.getActiveListService();
		obs_service = Context.getObsService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test checks if a problem is moved from observation table to Active
	 * list table.
	 * 
	 * The observation with problem added concept id (6042) is present in the
	 * populating active list test database and then
	 * 
	 * @throws Exception
	 */
	@Test
	public void testProblemMigrationToActiveList() throws Exception {
		
		Patient p_1 = patient_service.getPatient(7);

		List<Problem> items = Context.getActiveListService()
				.getActiveListItems(Problem.class, p_1, new ActiveListType(2));
		for (Problem item : items) {
			if (item.getProblem().getConceptId() == 123) {
				Assert.assertEquals("2008-08-18 14:11:13.0", item
						.getDateCreated().toString());
			}
		}
		Assert.assertTrue(items.size() > 0);

		
	}

	/**
	 * 
	 */
	@Test 
	public void testMultipleEncountersOfProblemBeforeResolution() {
		Patient p_2 = patient_service.getPatient(2);
		List<Problem> problem_items = active_list_service.getActiveListItems(
				Problem.class, p_2, new ActiveListType(2));
		Assert.assertTrue(problem_items.size() == 1);

		List<ActiveListItem> list_p_2 = active_list_service.getActiveListItems(
				p_2, new ActiveListType(2));
		Assert.assertTrue(list_p_2.size() == 1);

		List<Obs> obs_p_2 = obs_service.getObservationsByPerson(p_2);
		Assert.assertEquals(obs_p_2.get(0).getDateCreated().toString(),
				problem_items.get(0).getDateCreated().toString());
		
	}
	/**
	 * here a problem is added and Resolved and we check whether the Active list table is being properly populated
	 */
	@Test 
	public void testProblemAdditionAndResolution () {
	
		Patient p_4 = patient_service.getPatient(6);
		List<ActiveListItem> list_p_4 = active_list_service.getActiveListItems(
				p_4, new ActiveListType(2));
		List<Problem> problems_p_4 = active_list_service.getActiveListItems(
				Problem.class, p_4, new ActiveListType(2));
		Assert.assertTrue(list_p_4.size() == 1);
		Assert.assertTrue(problems_p_4.size() == 1);
		Assert.assertEquals(problems_p_4.get(0).getModifier().toString(),
				"Rule Out");
	}
	
	
	/*
	 * 6th testcase The patient has only two problem added concepts with the
	 * different value coded
	 */
	@Test 
	public void testTwoProblemAddedDifferentValueCoded() {
		Patient p_6 = patient_service.getPatient(9);
		List<Problem> problems_p_6 = active_list_service.getActiveListItems(
				Problem.class, p_6, new ActiveListType(2));
		Assert.assertTrue(problems_p_6.size() == 2);
	}
	
	
	/* 7th testcase this testcase tests the rule_out condition */
	@Test 
	public void testRuleOut() {
		Patient p_7 = patient_service.getPatient(502);
		List<Problem> problems_p_7 = active_list_service.getActiveListItems(
				Problem.class, p_7, new ActiveListType(2));
		List<ActiveListItem> list_p_7 = active_list_service.getActiveListItems(
				p_7, new ActiveListType(2));
		Assert.assertTrue(problems_p_7.size() == 1);
		Assert.assertEquals(problems_p_7.get(0).getModifier().toString(),
				"Rule Out");
	}
}
