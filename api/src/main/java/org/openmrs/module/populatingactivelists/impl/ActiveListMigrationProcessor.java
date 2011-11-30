/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.populatingactivelists.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.activelist.ActiveListItem;
import org.openmrs.api.ActiveListService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.springframework.ui.ModelMap;

/**
 * This class is a Comparator used to compare two active list items. 
 * 
 * This is used when migrating observations from obs table to active list tables 
 * 
 *
 */
class ActiveListComparator implements Comparator<ActiveListItem> {
	/**
	 * This method implements compares active list items based on their start date of their observation.
	 */
	//@Override
	public int compare(ActiveListItem o1, ActiveListItem o2) {
		return o1.getStartDate().compareTo(o2.getStartDate());
	}
}

/**
 * This class performs actual task of migration. It uses Updatable class to delegate operations required for migration. Every migration from obs table requires obs table to be implemented.
 * @author harsha
 *
 */
public abstract class ActiveListMigrationProcessor {

	/**
	 * This map maintain latest active issue for each person and concept id
	 * key is of the form <person_id>_<concept_id>
	 */
	private HashMap<String, ActiveListItem> activeIssuesMap;
	
	/**
	 * Updatable implementation
	 */
	private Migratable updatable;

	public void showThePage(ModelMap map) {

	}

	public int afterPageSubmission(Integer activeListToBeAddedConceptId,
			Integer activeListToBeResolvedConceptId,
			Migratable updatable) {

		this.updatable = updatable;

		// Get concepts for given conceptIDs
		Concept activeIssueAddConcept = Context.getConceptService().getConcept(
				activeListToBeAddedConceptId);
		Concept activeIssueRemoveConcept = Context.getConceptService().getConcept(
				activeListToBeResolvedConceptId);

		// Obtain observations for all patients for the above concepts from obs
		// table.
		ObsService obsTable = Context.getObsService();
		List<Obs> activeIssueAddObs = obsTable.getObservationsByPersonAndConcept(null,
				activeIssueAddConcept);
		List<Obs> activeIssueRemoveObs = obsTable.getObservationsByPersonAndConcept(null,
				activeIssueRemoveConcept);
		
		// Get active problems issues
		ActiveListService activeListTable = Context.getActiveListService();

		List<ActiveListItem> allActiveListItems = activeListTable
				.getActiveListItems(updatable.getActiveListClass(), null,
						updatable.getActiveListType());
		Collections.sort(allActiveListItems, new ActiveListComparator());

		activeIssuesMap = new HashMap<String, ActiveListItem>();
		for (ActiveListItem activeIssue : allActiveListItems) {
			activeIssuesMap.put(activeIssue.getPerson().getId() + "_"
					+ updatable.getActiveConcept(activeIssue).getId(),
					activeIssue);
		}
		
		int count = 0;
		
		// Process PAObs
		count = count + migrateObsToActiveList(activeIssueAddObs);

		// Process PRObs
		count = count + migrateObsToActiveListRes(activeIssueRemoveObs);
		
		return count;

	}

	/**
	 * This method performs addition of observations to active issues.
	 * 
	 * @param obsList list of observations.
	 * @return
	 */
	private int migrateObsToActiveList(List<Obs> obsList) {
		int count = 0;
		
		ActiveListService activeListTable = Context.getActiveListService();
		for (Obs ob : obsList) {
			ActiveListItem lastIssueEnc = activeIssuesMap.get(ob.getPerson()
					.getId() + "_" + ob.getValueCoded().getId());
			// check if observation has to be added
			if (updatable.isActiveListItemAddable(ob, lastIssueEnc)) {
				ActiveListItem newIssue = updatable.updateAddActiveListItem(ob);
				activeListTable.saveActiveListItem(newIssue);
				// update item in active issues Map
				activeIssuesMap.put(newIssue.getPerson().getId() + "_"
						+ updatable.getActiveConcept(newIssue).getId(),
						newIssue);
				count++;
			}
		}
		return count;
	}

	/**
	 * This method performs update of an active list item and mark it removable. 
	 *  
	 * @param obsList
	 * @return
	 */
	private int migrateObsToActiveListRes(List<Obs> obsList) {
		int count = 0;
		for (Obs obs : obsList) {
			ActiveListService activeListTable = Context.getActiveListService();
			ActiveListItem lastIssueEnc = activeIssuesMap.get(obs.getPerson()
					.getId() + "_" + obs.getValueCoded().getId());
			if (updatable.isActiveListItemRemovable(obs, lastIssueEnc)) {
				activeListTable.saveActiveListItem(updatable
						.updateRemoveActiveListItem(lastIssueEnc, obs));
				count++;
			}
		}
		return count;
	}
}