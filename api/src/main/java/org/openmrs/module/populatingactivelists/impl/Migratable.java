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

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.activelist.ActiveListItem;
import org.openmrs.activelist.ActiveListType;

/**
 * This interface abstracts the notion of a migrating an observation to an
 * active list table. It captures all the information about migration of an
 * observation such as
 * 
 * a. when to migrate a particular observation b. what specific information
 * about observation to be added to active list issue. c. what concept is
 * used in specific active list table.
 * 
 * At present there are only two types of active issues: Problems and Allergies.
 * 
 * Any new active list type that needs to be migrated has to implement this
 * interface.
 * 
 * @see ProblemsMigrationController
 * @see ActiveListMigrationProcessor
 * 
 * @author harsha
 * 
 */
public interface Migratable<T extends ActiveListItem> {
	/**
	 * This method should return if the current observation has to be added to
	 * its corresponding active list table given lastEncountered issue for same
	 * person and same concept.
	 * 
	 * @param current_obs an observation item from observations list(Obs table)
	 * @param lastEncountered an active issue observed for the same person and concept in
	 * @return true if it should be migrated else false
	 */
	boolean isActiveListItemAddable(Obs current_obs,
			ActiveListItem lastEncountered);

	/**
	 * This method should return if the current observation has to be removed to
	 * its corresponding active list table given lastEncountered issue for same
	 * person and same concept.
	 * 
	 * @param current_obs an observation item from observations list(Obs table)
	 * @param lastEncountered an active issue observed for the same person and concept in
	 * @return true if it should be migrated else false
	 * 
	 */
	boolean isActiveListItemRemovable(Obs current_obs,
			ActiveListItem lastEncountered);

	/**
	 * This method should return a new active list item populated with specific information from current observation.
	 * 
	 * @param currentObs Observation to be migrated to corresponding table.
	 * @return new Active list item
	 */
	ActiveListItem updateAddActiveListItem(Obs currentObs);

	/**
	 * This method should update a previously encountered active list item with current observartion findings.
	 * 
	 * @param toBeUpdated last encountered activelist item.
	 * @param currentObs current observation
	 * @return updated active list item
	 */
	ActiveListItem updateRemoveActiveListItem(ActiveListItem toBeUpdated,
			Obs currentObs);

	/**
	 * This method should return the concept of the issue.
	 * 
	 * @param activeListItem
	 * @return concept of the issue
	 */
	Concept getActiveConcept(ActiveListItem activeListItem);

	/**
	 *  This method should return the type of active list.
	 * 
	 * @return ActiveListType
	 */
	ActiveListType getActiveListType();

	/**
	 * This method should return its active issue class
	 * @return ActiveListClass
	 */
	Class getActiveListClass();
}