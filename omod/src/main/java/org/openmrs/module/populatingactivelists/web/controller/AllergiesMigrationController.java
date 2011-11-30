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
package org.openmrs.module.populatingactivelists.web.controller;

import javax.servlet.http.HttpSession;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.activelist.ActiveListItem;
import org.openmrs.activelist.ActiveListType;
import org.openmrs.activelist.Allergy;
import org.openmrs.activelist.AllergySeverity;
import org.openmrs.module.populatingactivelists.impl.ActiveListMigrationProcessor;
import org.openmrs.module.populatingactivelists.impl.Migratable;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AllergiesMigrationController extends ActiveListMigrationProcessor
		implements Migratable<ActiveListItem> {

	@RequestMapping(value = "/module/obsconverter/allergiesmigration", method = RequestMethod.GET)
	public void showThePage(ModelMap map) {

	}

	@RequestMapping(value = "/module/obsconverter/allergiesmigration", method = RequestMethod.POST)
	public void afterPageSubmission(
			ModelMap map,
			@RequestParam("allergiesAddedConceptID") Integer allergiesAddedConcept,
			@RequestParam("allergiesRemovedConceptID") Integer allergiesRemovedConcept,
			Migratable updatable, HttpSession httpSession) {

		int count = super.afterPageSubmission(allergiesAddedConcept,
				allergiesRemovedConcept, this);
		httpSession
				.setAttribute(
						WebConstants.OPENMRS_MSG_ATTR,
						"All active observations are migrated to active problems list successfully. total migrated observations: "
								+ count);

	}

	//@Override
	public boolean isActiveListItemAddable(Obs current_obs,
			ActiveListItem lastEncountered) {
		if (lastEncountered == null) {
			return true;
		} else if (lastEncountered instanceof Allergy) {
			Allergy lastProblemEnc = (Allergy) lastEncountered;
			return (lastProblemEnc.getEndDate().before(current_obs
					.getDateCreated()));
		} else
			return false;
	}

	//@Override
	public boolean isActiveListItemRemovable(Obs current_obs,
			ActiveListItem lastEncountered) {
		if (lastEncountered instanceof Allergy) {
			Allergy lastProblemEnc = (Allergy) lastEncountered;
			return (lastProblemEnc != null
					&& lastEncountered.getDateCreated().before(
							current_obs.getDateCreated()));
		} else
			return false;
	}

	//@Override
	public ActiveListItem updateAddActiveListItem(Obs currentObs) {
		Allergy newAllergy = new Allergy();
		newAllergy.setPerson(currentObs.getPerson());
		newAllergy.setAllergen(currentObs.getValueCoded());
		newAllergy.setDateCreated(currentObs.getDateCreated());
		newAllergy.setSeverity(AllergySeverity.UNKNOWN);
		return newAllergy;
	}

	//@Override
	public ActiveListItem updateRemoveActiveListItem(
			ActiveListItem toBeUpdated, Obs currentObs) {
		if (toBeUpdated instanceof Allergy) {
			Allergy lastEnc = (Allergy) toBeUpdated;
			lastEnc.setDateVoided(currentObs.getDateCreated());
			lastEnc.setSeverity(AllergySeverity.UNKNOWN);
			return lastEnc;
		}
		return null;
	}

	//@Override
	public Concept getActiveConcept(ActiveListItem activeListItem) {
		if (activeListItem instanceof Allergy) {
			Allergy allergy = (Allergy) activeListItem;
			return allergy.getAllergen();
		}
		return null;
	}

	//@Override
	public ActiveListType getActiveListType() {
		return new ActiveListType(2);
	}

	//@Override
	public Class getActiveListClass() {
		return Allergy.class;
	}
}