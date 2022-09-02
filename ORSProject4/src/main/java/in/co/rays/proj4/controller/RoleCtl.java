package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.BaseBean;
import in.co.rays.proj4.Bean.RoleBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DatabaseException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Role functionality Controller which can Performs operation for add, update and get
 * Role
 *
 * @author Sachin Mandloi
 */

@WebServlet(name="RoleCtl",urlPatterns={"/ctl/RoleCtl"})
public class RoleCtl extends BaseCtl{
	  private static final int serialVersionUID = 1;

	    private static Logger log = Logger.getLogger(RoleCtl.class);

	    @Override
	    protected boolean validate(HttpServletRequest request) {

	        log.debug("RoleCtl Method validate Started");

	        boolean pass = true;

	        if (DataValidator.isNull(request.getParameter("name"))) {
	            request.setAttribute("name",
	                    PropertyReader.getValue("error.require", "Name"));
	            pass = false;
	        }
	        else if (!DataValidator.isName(request.getParameter("name"))) {
				request.setAttribute("name", PropertyReader.getValue("Enter the valid Name"));
				 pass = false ;
			}

	        if (DataValidator.isNull(request.getParameter("description"))) {
	            request.setAttribute("description",
	                    PropertyReader.getValue("error.require", "Description"));
	            pass = false;
	        }

	        log.debug("RoleCtl Method validate Ended");

	        return pass;
	    }

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {

	        log.debug("RoleCtl Method populatebean Started");

	        RoleBean bean = new RoleBean();

	        bean.setId(DataUtility.getInt(request.getParameter("id")));

	        bean.setName(DataUtility.getString(request.getParameter("name")));
	        bean.setDescription(DataUtility.getString(request
	                .getParameter("description")));

	        populateDTO(bean, request);

	        log.debug("RoleCtl Method populatebean Ended");

	        return bean;
	    }

	    /**
	     * Contains Display logics
	     */
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        log.debug("RoleCtl Method doGet Started");

	        //System.out.println("In do get");

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get model
	        RoleModel model = new RoleModel();

	        int id = DataUtility.getInt(request.getParameter("id"));
	        if (id > 0 || op != null) {
	            RoleBean bean;
	            try {
	                bean = model.findByPK(id);
	                ServletUtility.setBean(bean, request);
	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }
	        ServletUtility.forward(getView(), request, response);
	        log.debug("RoleCtl Method doGetEnded");
	    }

	    /**
	     * Contains Submit logics
	     */
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        log.debug("RoleCtl Method doGet Started");

	        System.out.println("In do get");

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get model
	        RoleModel model = new RoleModel();

	        int id = DataUtility.getInt(request.getParameter("id"));

	        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op))  {

	            RoleBean bean = (RoleBean) populateBean(request);

	            try {
	                if (id > 0) {
	                    model.update(bean);
	              

	                ServletUtility.setBean(bean, request);
	                ServletUtility.setSuccessMessage("Data is successfully saved",
	                        request);
	                }
	                else {
	                    long pk = model.add(bean);
	                    bean.setId(pk);

	                    ServletUtility.setBean(bean, request);
	                    ServletUtility.setSuccessMessage("Data is successfully saved",
	                            request);
	                }

	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } catch (DuplicateRecordException e) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.setErrorMessage("Role already exists", request);
	            } catch (DatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        } else if (OP_DELETE.equalsIgnoreCase(op)) {

	            RoleBean bean = (RoleBean) populateBean(request);
	            try {
	                model.delete(bean);
	                ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request,
	                        response);
	                return;
	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } catch (DatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

	            ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
	            return;

	        }

	        ServletUtility.forward(getView(), request, response);

	        log.debug("RoleCtl Method doPOst Ended");
	    }

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_VIEW;
	}

}