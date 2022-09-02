package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.BaseBean;
import in.co.rays.proj4.Bean.CollegeBean;
import in.co.rays.proj4.Bean.CourseBean;
import in.co.rays.proj4.Bean.FacultyBean;
import in.co.rays.proj4.Bean.SubjectBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.FacultyModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Faculty functionality Controller which can Performs operation for add, update, delete
    and get  Faculty
 * @author Sachin Mandloi
 */

@WebServlet (name="FacultyCtl" , urlPatterns ={"/ctl/FacultyCtl"})
public class FacultyCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyCtl.class);
	
	protected void preload (HttpServletRequest request){
		
		//System.out.println("preload  in ");
		
		CourseModel cmodel = new CourseModel();
		CollegeModel comodel = new CollegeModel();
		SubjectModel smodel = new SubjectModel();
		
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<CollegeBean> colist = new ArrayList<CollegeBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		
		try {
			clist = cmodel.list();
			colist = comodel.list(0, 0);
			slist = smodel.list();
			
				request.setAttribute("CourseList", clist);
				request.setAttribute("CollegeList", colist);
				request.setAttribute("SubjectList", slist);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected boolean validate(HttpServletRequest request){
		
		//System.out.println("validate  in ");
		
		log.debug("Validate Method of Faculty Ctl Started");
		boolean pass = true;
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname", PropertyReader.getValue("error.require", "FirstName"));
			 pass = false;
			
		}else if (!DataValidator.isValidName(request.getParameter("firstname"))) {
			request.setAttribute("firstname", PropertyReader.getValue( "Enter the valid First name"));
			 pass = false;
		}
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		}else if (!DataValidator.isValidName(request.getParameter("lastname"))) {
			request.setAttribute("lastname", PropertyReader.getValue( "Enter the valid Last name"));
			 pass = false;
			
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require","Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("doj"))) {
			request.setAttribute("doj", PropertyReader.getValue("error.require","Date Of joining"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("doj"))) {
			request.setAttribute("doj", PropertyReader.getValue("error.date","Date Of joining"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("qualification"))){
			request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		}else if (!DataValidator.isEmail(request.getParameter("loginid"))) {
			request.setAttribute("loginid", PropertyReader.getValue("Enter the valid login id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		}else if (!DataValidator.isMobileNo(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("collegeid"))) {
			request.setAttribute("collegeid", PropertyReader.getValue("error.require", "CollegeName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("courseid"))) {
			request.setAttribute("courseid", PropertyReader.getValue("error.require", "CourseName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("subjectid"))) {
			request.setAttribute("subjectid", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}
		
		
		System.out.println("validate out ");
		log.debug("validate Ended");
		return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request){
		log.debug("populate bean faculty ctl started");
		System.out.println(" populate bean ctl  in ");
		FacultyBean bean = new FacultyBean();
	
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirst_Name(DataUtility.getString(request.getParameter("firstname")));
		bean.setLast_Name(DataUtility.getString(request.getParameter("lastname")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDOJ(DataUtility.getDate(request.getParameter("doj")));
		
		System.out.println("populate bean date faculty-------------..."+request.getParameter("doj"));
		bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
		bean.setEmail_id(DataUtility.getString(request.getParameter("loginid")));
		bean.setMobile_No(DataUtility.getString(request.getParameter("mobileno")));
		bean.setCollege_id(DataUtility.getInt(request.getParameter("collegeid")));
		bean.setCourse_id(DataUtility.getInt(request.getParameter("courseid")));
		bean.setSubject_id(DataUtility.getInt(request.getParameter("subjectid")));
//		bean.setCourseName(DataUtility.getString(request.getParameter("courseid")));
//		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectid")));
		populateDTO(bean, request);
		log.debug("populate bean faculty ctl Ended");
		System.out.println(" populate bean ctl out ");
		return bean;
	}
	
	
    /**
     * Contains Display logics
     */




	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("Do get of faculty ctl Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		//Get Model
		FacultyModel model = new FacultyModel();
		Long id = DataUtility.getLong(request.getParameter("id"));
		
		if(id>0 || op!= null){
			FacultyBean bean;
			try {
				bean = model.findByPk(id);
			ServletUtility.setBean(bean, request);
			
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		System.out.println(" do get out ");
		log.debug("Do get of  faculty ctl Ended");
		ServletUtility.forward(getView(), request, response);
	}

    /**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do post of  faculty ctl Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));	
			
		// Get Model
		FacultyModel model = new FacultyModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			System.out.println("date in f post---------"+bean.getDOJ());
			try{
			if(id > 0){
				model.update(bean);
				ServletUtility.setBean(bean, request);

			}else{
			long pk = model.add(bean);
			ServletUtility.setBean(bean, request);
		//		bean.setId(pk);
			}
			ServletUtility.setSuccessMessage("Faculty Successfully Register", request);
			}catch(ApplicationException e){
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return ; 
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}/*else if (OP_DELETE.equalsIgnoreCase(op)) {
			FacultyBean bean =(FacultyBean) populateBean(request);
	
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
	}*/	else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return ;
			}			
	else if (OP_CANCEL.equalsIgnoreCase(op) ) {
		ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
		return ;
	}			
		//System.out.println(" do post out ");
		ServletUtility.forward(getView(), request, response);
		log.debug("Do post of  faculty ctl Ended");
	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}