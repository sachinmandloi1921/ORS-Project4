package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.CourseBean;
import in.co.rays.proj4.Bean.SubjectBean;
import in.co.rays.proj4.Bean.TimeTableBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of TimetableModel
 *
 * @author Sachin Mandloi
 * 
 */
public class TimeTableModel {
	private static Logger log = Logger.getLogger(TimeTableModel.class);
	public Integer nextPK() throws ApplicationException {
		log.debug("Timetable model nextPk method Started ");
		Connection conn = null;
		int pk=0;
		
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_timetable");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("database Exception ...", e);
			throw new ApplicationException("nextPK is not generated");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Timetable model nextPk method End ");
		return pk+1;
	}
	
	public int add(TimeTableBean bean) throws ApplicationException {
		log.debug("TimeTable model Add method Started");
		Connection conn =null;
		int pk=0;
	
		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(bean.getCourse_Id());
		String courseName = courseBean.getCourse_Name();

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subBean = subjectModel.findByPk(bean.getSubject_Id());
		String subjectName = subBean.getSubject_Name();
		
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextPK());
			ps.setString(2, courseName);
			ps.setInt(3, bean.getCourse_Id());
			ps.setString(4, subjectName);
			ps.setInt(5, bean.getSubject_Id());
			ps.setDate(6, new java.sql.Date(bean.getExam_Date().getTime()));
			System.out.println("check 0");
			ps.setString(7, bean.getExam_Time());
			ps.setString(8, bean.getSemester());
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11, bean.getCreatedDatetime());
			ps.setTimestamp(12, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("rollback isnt run in Add method of timetablemodel");
			}
			e.printStackTrace();
			throw new ApplicationException("exception in add method of timetablemodel");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable model Add method End");
		return pk;
	}
	public void delete(TimeTableBean bean) throws ApplicationException {
		log.debug("TIMETABLE Model Delete method Started");
		Connection conn=null;
		
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception ...", e);
			try {
			conn.rollback();
		} catch (Exception e2) {
			throw new ApplicationException("rollback not works for TimeTable Model");
		}
			e.printStackTrace();
			throw new ApplicationException("delete=fail fro timetable model");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TIMETABLE Model Delete method End");
	}
	
	public void update(TimeTableBean tb) throws ApplicationException, DuplicateRecordException {
		log.debug("TIMETABLE Model update method Started");
		Connection conn = null;

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(tb.getCourse_Id());
		String courseName = courseBean.getCourse_Name();

		SubjectModel submodel = new SubjectModel();
		SubjectBean subbean = submodel.findByPk(tb.getSubject_Id());
		String subjectName = subbean.getSubject_Name();
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_TIMETABLE SET COURSE_NAME=?,COURSE_ID=?,SUBJECT_NAME=?,SUBJECT_ID=?,EXAM_DATE=?,EXAM_TIME=?,SEMESTER=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");

			ps.setString(1,courseName);
			ps.setInt(2, tb.getCourse_Id());
			ps.setString(3, subjectName);
			ps.setInt(4, tb.getSubject_Id());
			ps.setDate(5, new java.sql.Date(tb.getExam_Date().getTime()));
			ps.setString(6, tb.getExam_Time());
			ps.setString(7, tb.getSemester());
			ps.setString(8, tb.getCreatedBy());
			ps.setString(9, tb.getModifiedBy());
			ps.setTimestamp(10, tb.getCreatedDatetime());
			ps.setTimestamp(11, tb.getModifiedDatetime());
			ps.setLong(12, tb.getId());

			ps.executeUpdate();

			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Rollback of Update Method of TimeTable Model" + ex.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TIMETABLE Model update method End");
	}
	
	public TimeTableBean findByName(String name) throws ApplicationException {
		log.debug("TimeTable Model findByName method Started");
		Connection conn=null;
		TimeTableBean bean =null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Subject_Name = ?");
		
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs =  ps.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("database Exception....", e1);
			throw new ApplicationException("findByName=failed");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model findByName method End");
		return bean;
	}
	public TimeTableBean findByPK(long pk) throws ApplicationException {
		log.debug("TimeTable Model findBypk method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		Connection conn=null;
		TimeTableBean bean = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps =  conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("findByPk=failed");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model findByPk method End");
		return bean;
	}
	
	public List Search(TimeTableBean bean) throws ApplicationException {
		return Search(bean,0,0);
	}
	
	public List Search(TimeTableBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("TimeTable Model search method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");
		Connection conn = null;
		List list = new ArrayList();
		
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getCourse_Id() > 0) {
				sql.append(" AND Course_ID = " + bean.getCourse_Id());
			}
			if (bean.getSubject_Id() > 0) {
				sql.append(" AND Subject_ID = " + bean.getSubject_Id());
			}
			if (bean.getExam_Date() != null && bean.getExam_Date().getTime() > 0) {
				Date d = new Date(bean.getExam_Date().getTime());
				sql.append(" AND Exam_Date = '" + d + "'");
			}

			if (bean.getCourse_Name() != null && bean.getCourse_Name().length() > 0) {
				sql.append(" AND Course_Name like '" + bean.getCourse_Name() + "%'");
			}

			if (bean.getSubject_Name() != null && bean.getSubject_Name().length() > 0) {
				sql.append(" AND Subject_Name like '" + bean.getSubject_Name() + "%'");
			}
		}

		// Page Size is greater then Zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		System.out.println("sql queryy " + sql);
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps= conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Search = failed in TimeTableModel");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model search method End");
		return list;
	}
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("TimeTable Model list method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE");
		// Page Size is greater then Zero then pagination begin
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					sql.append(" limit " + pageNo + " , " + pageSize);
				}
				
		System.out.println(sql);
		Connection conn = null;
		TimeTableBean bean = null;
		List list = new ArrayList();
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean= new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("listmethod = failed in TimeTableModel");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTable Model list method End");
		return list;
	}
	public TimeTableBean checkBycss(int CourseId, int SubjectId, int sem) throws ApplicationException {
		
		Connection conn = null;
		TimeTableBean bean = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Subject_ID=? AND Semester=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setInt(3, sem);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("Course,subject,sem =success");
		return bean;
	}	
	
	public TimeTableBean checkBycds(int CourseId, int sem, Date ExamDate) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Semester=? AND Exam_Date=?");

		Connection conn = null;
		TimeTableBean bean = null;
		Date ExDate = new Date(ExamDate.getTime());

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setInt(2, sem);
			ps.setDate(3, (java.sql.Date) ExDate);  // point to remember
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static TimeTableBean checkBysemester(long CourseId, long SubjectId, String semester, Date ExamDate) {

		TimeTableBean bean = null;

		Date ExDate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " SEMESTER=? AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, (java.sql.Date) ExDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setCourse_Id(rs.getInt(3));
				bean.setSubject_Name(rs.getString(4));
				bean.setSubject_Id(rs.getInt(5));
				bean.setExam_Date(rs.getDate(6));
				bean.setExam_Time(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public static TimeTableBean checkByCourseName(int CourseId, Date ExamDate) {
		Connection conn = null;
		TimeTableBean bean = null;

		Date Exdate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setInt(1, CourseId);
			ps.setDate(2, (java.sql.Date) Exdate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Id(rs.getInt(2));
				bean.setCourse_Name(rs.getString(3));
				bean.setSubject_Id(rs.getInt(4));
				bean.setSubject_Name(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExam_Date(rs.getDate(7));
				bean.setExam_Time(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

}