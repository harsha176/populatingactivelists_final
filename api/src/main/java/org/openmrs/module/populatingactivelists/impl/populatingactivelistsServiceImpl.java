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

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.populatingactivelists.populatingactivelistsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.populatingactivelists.db.populatingactivelistsDAO;

public class populatingactivelistsServiceImpl extends BaseOpenmrsService implements populatingactivelistsService{
	
	/**
	 * default constructor
	 */
	private final Log log = LogFactory.getLog(this.getClass());
	public populatingactivelistsServiceImpl()
	{
		log.info("populatingactivelistsService");
	}
	
	
	private populatingactivelistsDAO dao;
	
	/**
	 * setter for populatingactivelistsDAO
	 */
	public void setpopulatingactivelistsDAO(populatingactivelistsDAO dao)
	{
		this.dao = dao;
	}
	
	/**
	 * getter for populatingactivelistsDAO
	 */
	public populatingactivelistsDAO getpopulatingactivelistsDAO()
	{
		return  dao;
	}	
	
	
	
}