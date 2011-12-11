package org.openmrs.module.populatingactivelists.impl;

import org.openmrs.activelist.ActiveListItem;

public class ActiveIssuesMap<T extends ActiveListItem> {
	/**
	 * This map maintain latest active issue for each person and concept id
	 * key is of the form <person_id>_<concept_id>
	 */
	//private HashMap<String, T> activeIssuesMap;
	//private static ActiveIssuesMap instance = null;
	//private T activeIssue;
	
	/**
	 * Making it as a singleton class
	 */
	private ActiveIssuesMap() {
		
	}
	
	/*public static synchronized ActiveIssuesMap getInstance() {
		if(instance == null) {
			
			initialize();
		}
	}*/

	/*private void initialize( ) {
		activeIssuesMap = new HashMap<String, ActiveListItem>();
		ActiveListService activeListTable = Context.getActiveListService();
		
		List<ActiveListItem> allActiveListItems = activeListTable
				.getActiveListItems(TgetActiveListClass(), null,
						activeIssue.getActiveListType());
		Collections.sort(allActiveListItems, new ActiveListComparator());
		for (ActiveListItem activeIssue : allActiveListItems) {
			activeIssuesMap.put(activeIssue.getPerson().getId() + "_"
					+ activeIssue;
		}
	}*/
	
}
