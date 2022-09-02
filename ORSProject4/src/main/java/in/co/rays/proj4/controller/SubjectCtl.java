package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.SubjectBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DatabaseException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Subject functionality Controller. Performs operation for add, update, delete
 * and get Subject
 *
 * @author Sachin Mandloi
 */

@WebServlet (name = "SubjectCtl" , urlPatterns = {"/ctl/SubjectCtl"})
public class SubjectCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SubjectCtl.class);
	
	protected void preload(HttpServletRequest request){
		log.debug("method preload start");
		//System.out.println("preload is enter");
		 
		CourseModel cmodel = new CourseModel();
	//	List<CourseBean> cList = new ArrayList<CourseBean>();
		
		try {
		List cList = cmodel.list();
			request.setAttribute("CourseList", cList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		//System.out.println("preload out");
		log.debug("method preload end");
	}
	
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate Method of Subject Ctl start");
		boolean pass= true;
		
		if(DataValidator.isNull(request.getParameter("name"))){
			request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
			 pass = false;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("Enter the valid Subject Name"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("description"))){
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			 pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("coursename"))){
			request.setAttribute("coursename", PropertyReader.getValue("error.require", "Course Name"));
			 pass = false;
		}
		log.debug("validate Method of Subject Ctl  End");
		return pass;
	}
	
	protected SubjectBean populateBean(HttpServletRequest request){
		log.debug("Populate bean Method of Subject Ctl start");
		SubjectBean bean = new SubjectBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSubject_Name(DataUtility.getString(request.getParameter("name")));
		bean.setDiscription(DataUtility.getString(request.getParameter("description")));
		bean.setCourse_Id(DataUtility.getInt(request.getParameter("coursename")));
		
		populateDTO(bean, request);
		
		log.debug("PopulateBean Method of Subject Ctl End");
		return bean;
		
	}
    /**
     * Contains Display logics
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 log.debug("Do get Method of Subject Ctl start ");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		SubjectModel model = new SubjectModel();
		SubjectBean bean = null;
		long id =DataUtility.getLong(request.getParameter("id"));
		
		if(id > 0 || op != null){
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} 
				catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
				return;
				}
		}
		log.debug("Do get Method of Subject Ctl End");
		ServletUtility.forward(getView(), request, response);
	}
    /**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do post Method of Subject Ctl start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		SubjectModel model = new SubjectModel();		
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {	
			SubjectBean bean = (SubjectBean)populateBean(request);
		//	System.out.println("post in operation save  ");
		try{	
			if(id > 0){
				model.update(bean);
			}else{
				long pk = model.add(bean);
		//		bean.setId(pk);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage(" Subject is Succesfully Added ", request);
		}catch(ApplicationException e){
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		else if (OP_CANCEL.equalsIgnoreCase(op) ) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
		else if (OP_DELETE.equalsIgnoreCase(op)) {
			SubjectBean bean =  populateBean(request);
			try {
				model.delete(bean);
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return ; 
			}
		}
	
		ServletUtility.forward(getView(), request, response);
		log.debug("Do post Method of Subject Ctl End");
	}
	
	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}
}