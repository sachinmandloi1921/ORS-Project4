package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.CourseBean;
import in.co.rays.proj4.Bean.SubjectBean;
import in.co.rays.proj4.Bean.TimeTableBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimeTableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * TimeTable functionality Controller which can Performs operation for add, update, delete
 * and get TimeTable
 *
 * @author Pushpendra Singh Kushwah
 */

@WebServlet("/ctl/TimeTableCtl")
public class TimeTableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	protected void preload(HttpServletRequest request) {
		CourseModel crsm = new CourseModel();
		SubjectModel stm = new SubjectModel();
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		try {
			clist = crsm.list();
			slist = stm.list();
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("validate method of TimeTable Ctl started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}
		else	if (!DataValidator.isDate(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue( "Exam Date is not valid"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExTime"))) {
			request.setAttribute("ExTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}
		
		
		/*if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", "Please Select Description");
			pass = false;
		}*/

		log.debug("validate method of TimeTable Ctl End");
		return pass;
	}

	protected TimeTableBean populateBean(HttpServletRequest request) {
		log.debug("populateBean method of TimeTable Ctl start");
		TimeTableBean tb = new TimeTableBean();

		//System.out.println("check 1");
		tb.setId(DataUtility.getLong(request.getParameter("id")));
		
		tb.setSubject_Id(DataUtility.getInt(request.getParameter("subjectId")));
		//tb.setSubject_Name(DataUtility.getString(request.getParameter("Subject_Name")));
		tb.setCourse_Id(DataUtility.getInt(request.getParameter("courseId")));
	//System.out.println("check 2");
	//	tb.setCourse_Name(DataUtility.getString(request.getParameter("Course_Name")));
		tb.setSemester(DataUtility.getString(request.getParameter("semester")));
	//	tb.setDescription(DataUtility.getString(request.getParameter("description")));
		tb.setExam_Date(DataUtility.getDate(request.getParameter("ExDate")));
		tb.setExam_Time(DataUtility.getString(request.getParameter("ExTime")));
		//System.out.println("<<<<<<__________>>>>>>>>");
		//System.out.println(request.getParameter("ExDate"));
		//System.out.println(tb.getExam_Date());
		populateDTO(tb, request);
		log.debug("populateBean method of TimeTable Ctl End");
		return tb;
	}
    /**
     * Contains Display logics
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do Get method of TimeTable Ctl Started");
	//System.out.println("Timetable ctl .do get started........>>>>>");

		String op = DataUtility.getString(request.getParameter("operation"));
		int id = (int) DataUtility.getLong(request.getParameter("id"));
	
		TimeTableModel tm = new TimeTableModel();
		TimeTableBean tb = null;
		if (id > 0) {
			try {
				tb = tm.findByPK(id);
				ServletUtility.setBean(tb, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}

		log.debug("do Get method of TimeTable Ctl End");
		System.out.println("Timetable ctl .do get End........>>>>>");
		ServletUtility.forward(getView(), request, response);
	}
    /**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do post method of TimeTable Ctl start");

		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel tm = new TimeTableModel();
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) 
		{
			TimeTableBean tb = (TimeTableBean)populateBean(request);
			try {
				if(id>0){
					tm.update(tb);
					ServletUtility.setBean(tb, request);
					ServletUtility.setSuccessMessage(" TimeTable is Successfully update", request);
				}else{
	
				tm.add(tb);
			
				
				ServletUtility.setBean(tb, request);
				ServletUtility.setSuccessMessage(" TimeTable is Successfully Added", request);
				}	
			}catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setBean(tb, request);
				ServletUtility.setErrorMessage("Time Table already Exists", request);
			}
		}
		else if (OP_CANCEL.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
		else if ( OP_RESET.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}