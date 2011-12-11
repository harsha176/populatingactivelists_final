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
import org.openmrs.module.populatingactivelists.web.controller.AllergiesMigrationController;
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

public class AllergyMigrationTest extends BaseModuleContextSensitiveTest{

	protected ConceptService conceptService = null;
	protected static final String INITIAL_CONCEPTS_XML = "AllergiesMigrationTestXML.xml";
	PatientService patient_service;
	ActiveListService active_list_service;
	ObsService obs_service;
	
	protected static final String GET_CONCEPTS_BY_SET_XML = "org/openmrs/api/include/ConceptServiceTest-getConceptsBySet.xml";
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
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		conceptService = Context.getConceptService();
		initializeInMemoryDatabase();
		executeDataSet(INITIAL_CONCEPTS_XML);
		authenticate();
		
		AllergiesMigrationController pmc = new AllergiesMigrationController();
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"admin/obsconverter/allergiesmigration.form");
		MockHttpSession mockHttpSession = new MockHttpSession(null);
		request.setSession(mockHttpSession);
		pmc.afterPageSubmission(6058, 6059, mockHttpSession);
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

	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/


	@Test
	public void testMultipleEncountersOfAllergiesBeforeResolution()
	{
		Patient p_2=patient_service.getPatient(2);
	    List<Allergy> allergy_p_2=active_list_service.getActiveListItems(Allergy.class,p_2,new ActiveListType(1));
	    Assert.assertTrue(allergy_p_2.size()==1);
	    List<ActiveListItem> alist_p_2=active_list_service.getActiveListItems(p_2, new ActiveListType(1));
		Assert.assertTrue(alist_p_2.size()==1);    
		List<Obs> aobs_p_2=obs_service.getObservationsByPerson(p_2) ;
	    Assert.assertEquals(aobs_p_2.get(3).getDateCreated().toString(), allergy_p_2.get(0).getDateCreated().toString());
	}
	
	
	/*public void testProblemAdditionafterResolution()
	{
		Patient p_3=patient_service.getPatient(8);
        List<ActiveListItem> alist_p_3=active_list_service.getActiveListItems(p_3,new ActiveListType(1));
        Assert.assertTrue(alist_p_3.size()==2);
        List<Obs> aobs_p_3=obs_service.getObservationsByPerson(p_3) ;
        Assert.assertEquals(aobs_p_3.get(3).getDateCreated().toString(), alist_p_3.get(0).getDateCreated().toString());
        Assert.assertEquals(aobs_p_3.get(4).getDateCreated().toString(), alist_p_3.get(0).getDateVoided().toString());
        Assert.assertEquals(aobs_p_3.get(5).getDateCreated().toString(), alist_p_3.get(1).getDateCreated().toString());
	}*/
	@Test
	public void testAllergiesMigration()
	{
		Patient p_4=patient_service.getPatient(6);
	       List<ActiveListItem> alist_p_4=active_list_service.getActiveListItems(p_4,new ActiveListType(1));
	        List<Allergy> allergies_p_4=active_list_service.getActiveListItems(Allergy.class,p_4,new ActiveListType(1));
	        Assert.assertTrue(alist_p_4.size()==1);
	        System.out.println(allergies_p_4.size());
	        Assert.assertTrue(allergies_p_4.size()==1);
	}
	
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         