package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.util.ServletUtility;

/**
 * Servlet implementation class ErrorCtl
 *  * @author Sachin Mandloi
 * 
 */

@WebServlet("/ErrorCtl")
public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ErrorCtl.class);
    /**
     * Contains Display logics
     */


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do get Method of Error Ctl start");
		System.out.println("------Error ctl-------->" );
		ServletUtility.forward(getView(), request, response);
		
		log.debug("Do get Method of Error Ctl End");
		
	}

    /**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		log.debug("Do Post Method of Error Ctl start");
		ServletUtility.forward(getView(), request, response);
		log.debug("Do Post Method of Error Ctl End");
	}

	@Override
	protected String getView() {
		return ORSView.ERROR_VIEW;
	}

}