/*L
 *  Copyright Washington University in St. Louis
 *  Copyright SemanticBits
 *  Copyright Persistent Systems
 *  Copyright Krishagni
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/catissue-print-service/LICENSE.txt for details.
 */

package edu.wustl.catissuecore.webservice.util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.servlet.ServletConfig;

/**
 * Servlet that will run on JBoss start up to Initiailize SpecimenLabelRuleHandler
 * @author vijay_pande
 *
 */
public class  SpecimenLabelRuleHandlerServlet extends HttpServlet
{
	/**
	 * method to initialize SpecimenLabelRuleHandler
	 */
	public void init(ServletConfig config) throws ServletException
	{
		SpecimenLabelRuleHandler.init();
	} 
}
