package org.openmrs.module.populatingactivelists.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptSet;
import org.openmrs.Encounter;
import org.openmrs.Field;
import org.openmrs.Form;
import org.openmrs.FormField;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonName;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.web.controller.observation.ObsFormController;

public class ProblemMigrationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/*creating patient 1*/
		/*String patient_name="Bob";
		PersonName patient_1_name=new PersonName();
		patient_1_name.setGivenName(patient_name);
		
		
		Integer patient_id=1234;
		PatientIdentifier patient_1_id=new PatientIdentifier();
		patient_1_id.setId(patient_id);
		
		Patient patient_1=new Patient();
		patient_1.addName(patient_1_name);
		patient_1.addIdentifier(patient_1_id);
		//patient_1.add
		
		//PatientService patients=Context.getPatientService();
		//patients.createPatient(patient_1);
		
		
		//creating an observation
		
		String form_name="disease";
		
		Form form_1=new Form();
		form_1.setName(form_name);
		
		FormField problemAdded=new FormField();
		problemAdded.setForm(form_1);
		problemAdded.setField(new Field())
		//problemAdded
		
		form_1.addFormField(problemAdded);

        
		
		//Form form_1=new Form();
	//	form_1.
		
		
		
		Encounter encounter=new Encounter();
		encounter.setForm(form_1);
		
		
		Obs obs_1=new Obs();
		obs_1.setEncounter(encounter);
		
		ObsService obs=Context.getObsService();
		obs.getObservationsByPerson(patient_1);
		
		Concept concept=new Concept();
		
		Encounter encounter_patient_1=new Encounter();
	//	encounter_patient_1.
		
		Obs obs_1=new Obs();
		obs_1.setPatient(patient_1);
		obs_1.
		//obs_1.set
		//ObsService obs=Context.getObsService();
		//obs
		
		ObsFormController controller = new ObsFormController();
		controller.setApplicationContext(applicationContext);
		controller.setSuccessView("encounter.form");
		controller.setFormView("obs.form");

		// set up the request and do an initial "get" as if the user loaded the
		// page for the first time
		
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/admin/observations/.form");
		request.setSession(new MockHttpSession(null));
		HttpServletResponse response = new MockHttpServletResponse();
		controller.handleRequest(request, response);

		// set this to be a page submission
		request.setMethod("POST");

		// add all of the parameters that are expected
		// all but the relationship "3a" should match the stored data
		request.addParameter("person", "2");
		request.addParameter("encounter", "3");
		request.addParameter("location", "1");
		request.addParameter("obsDatetime", "05/05/2005");
		request.addParameter("concept", "4"); // CIVIL_STATUS (conceptid=4) concept
		request.addParameter("valueCoded", "5"); // conceptNameId=2458 for SINGLE concept
		request.addParameter("saveObs", "Save Obs"); // so that the form is processed

		// send the parameters to the controller
		controller.handleRequest(request, response);

		// make sure an obs was created
		List<Obs> obsForPatient = os.getObservationsByPerson(new Person(2));
		assertEquals(1, obsForPatient.size());
		assertEquals(3, obsForPatient.get(0).getEncounter().getId().intValue());
		assertEquals(1, obsForPatient.get(0).getLocation().getId().intValue());
		*/
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// clear obs table
		// Context.getObsService().
		// clear active list table
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testProblemMigrationForAConcept(){
		//Add an entry in obs table
		
		//Run Activelistmigration processor passing problem migration
		
		//expect an entry in probem list table.
	}
	
}
