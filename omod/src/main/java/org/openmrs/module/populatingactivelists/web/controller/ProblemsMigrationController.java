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

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.activelist.ActiveListItem;
import org.openmrs.activelist.ActiveListType;
import org.openmrs.activelist.Problem;
import org.openmrs.activelist.ProblemModifier;
import org.openmrs.module.populatingactivelists.impl.ActiveListMigrationProcessor;
import org.openmrs.module.populatingactivelists.impl.Migratable;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProblemsMigrationController extends ActiveListMigrationProcessor
		implements Migratable<ActiveListItem> {
	List<Problem> allProblems;
	HashMap<String, Problem> problemsMap;
	File logFile;
	PrintWriter pw;

	@RequestMapping(value = "/module/obsconverter/problemsmigration", method = RequestMethod.GET)
	public void showThePage(ModelMap map) {
		super.showThePage(map);
	}

	@RequestMapping(value = "/module/obsconverter/problemsmigration", method = RequestMethod.POST)
	public void afterPageSubmission(
			@RequestParam("problemAddedConcept") Integer problemAddedConceptId,
			@RequestParam("problemRemovedConcept") Integer problemResolvedConceptId,
			HttpSession httpSession) {

		int count = super.afterPageSubmission(problemAddedConceptId,
				problemResolvedConceptId, this);
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
		} else if (lastEncountered instanceof Problem) {
			Problem lastProblemEnc = (Problem) lastEncountered;
			return (lastProblemEnc.getModifier() == ProblemModifier.RULE_OUT && lastProblemEnc
					.getEndDate().before(current_obs.getDateCreated()));
		} else
			return false;
	}

	//@Override
	public boolean isActiveListItemRemovable(Obs current_obs,
			ActiveListItem lastEncountered) {
		if (lastEncountered instanceof Problem) {
			Problem lastProblemEnc = (Problem) lastEncountered;
			return (lastProblemEnc != null
					&& lastProblemEnc.getModifier() == ProblemModifier.HISTORY_OF
					&& lastEncountered.getDateCreated().before(
							current_obs.getDateCreated()));
		} else
			return false;
	}

	//@Override
	public ActiveListItem updateAddActiveListItem(Obs ob) {
		Problem newProb = new Problem();
		newProb.setPerson(ob.getPerson());
		newProb.setProblem(ob.getValueCoded());
		newProb.setDateCreated(ob.getDateCreated());
		newProb.setModifier(ProblemModifier.HISTORY_OF);
		return newProb;
	}

	//@Override
	public ActiveListItem updateRemoveActiveListItem(
			ActiveListItem toBeUpdated, Obs obs) {
		if (toBeUpdated instanceof Problem) {
			Problem lastEnc = (Problem) toBeUpdated;
			lastEnc.setDateVoided(obs.getDateCreated());
			lastEnc.setModifier(ProblemModifier.RULE_OUT);
			return lastEnc;
		}
		return null;
	}

	//@Override
	public Concept getActiveConcept(ActiveListItem activeListItem) {
		if (activeListItem instanceof Problem) {
			Problem activeProblem = (Problem) activeListItem;
			return activeProblem.getProblem();
		}
		return null;
	}
	
	

	//@Override
	public ActiveListType getActiveListType() {
		return new ActiveListType(1);
	}

	//@Override
	public Class getActiveListClass() {
		return Problem.class;
	}
}
