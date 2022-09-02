package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.CollegeBean;
import in.co.rays.proj4.Bean.CourseBean;
import in.co.rays.proj4.Bean.FacultyBean;
import in.co.rays.proj4.Bean.SubjectBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of FacultyModel
 *
 * @author Sachin Mandloi
 */
public class FacultyModel {

	private static Logger log = Logger.getLogger(FacultyModel.class);
	

	public Integer nextPk() throws ApplicationException {
		log.debug("Faculty Model nextPK method Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_faculty");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("DataBase Exception ..", e);
			throw new ApplicationException("pk is not generated");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model nextPK method End");
		return pk + 1;
	}

	/**
	 * Add a Faculty.
	 *
	 * @param bean the bean
	 * @return the int
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public int add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
    	log.debug("Model add Started");
		int pk = 0;
		Connection conn = null;
		CollegeModel colmodel 	= new CollegeModel();
		CourseModel cosmodel	=  new CourseModel();
		SubjectModel submodel 	=  new SubjectModel();
		
		CollegeBean collegeBean = colmodel.findByPk(bean.getCollege_id());
		bean.setCollege_Name(collegeBean.getName());

		CourseBean courseBean = cosmodel.findByPk(bean.getCourse_id());
		bean.setCourse_Name(courseBean.getCourse_Name());

		SubjectBean subjectBean;
		try {
			subjectBean = submodel.findByPk(bean.getSubject_id());
			bean.setSubject_Name(subjectBean.getSubject_Name());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FacultyBean duplicataRole = findByName(bean.getFirst_Name());
		// Check if create Faculty already exist
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Faculty already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk = nextPk();
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getFirst_Name());
			ps.setString(3, bean.getLast_Name());
			ps.setString(4, bean.getGender());
			ps.setDate(5, new java.sql.Date(bean.getDOJ().getTime()));
			ps.setString(6, bean.getQualification());
			ps.setString(7, bean.getEmail_id());
			ps.setString(8, bean.getMobile_No());
			ps.setInt(9, bean.getCollege_id());
			ps.setString(10, bean.getCollege_Name());
			ps.setInt(11, bean.getCourse_id());
			ps.setString(12, bean.getCourse_Name());
			ps.setInt(13, bean.getSubject_id());
			ps.setString(14, bean.getSubject_Name());
			ps.setString(15, bean.getCreatedBy());
			ps.setString(16, bean.getModifiedBy());
			ps.setTimestamp(17, bean.getCreatedDatetime());
			ps.setTimestamp(18, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				log.error("Database Exception..", e);
				throw new ApplicationException("rollback in not done in add method of facultyModel");
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ApplicationException("Exception in add method of facultyModel");
		}
//		log.debug("Model add End");
		return pk;
	}
	/**
	 * Delete a Faculty.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("Faculty delete model started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_faculty where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				throw new ApplicationException("rollback is not working in delete");
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ApplicationException("delete method of FacultyModel=failed");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model Delete method End");
	}
	/**
	 * Update a Faculty.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public void update(FacultyBean bean) throws ApplicationException {
		log.debug("Model update Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_FACULTY SET  FIRST_NAME=?, LAST_NAME=?, GENDER=?, DOJ=?,QUALIFICATION=?, EMAIL_ID=?, MOBILE_NO=? , COLLEGE_ID=?, COLLEGE_NAME=?,COURSE_ID=?,COURSE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?, CREATED_BY=? , MODIFIED_BY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=?  WHERE ID= ?");
			ps.setString(1, bean.getFirst_Name());
			ps.setString(2, bean.getLast_Name());
			ps.setString(3, bean.getGender());
			ps.setDate(4, new java.sql.Date(bean.getDOJ().getTime()));
			ps.setString(5, bean.getQualification());
			ps.setString(6, bean.getEmail_id());
			ps.setString(7, bean.getMobile_No());
			ps.setInt(8, bean.getCollege_id());
			ps.setString(9, bean.getCollege_Name());
			ps.setInt(10, bean.getCourse_id());
			ps.setString(11, bean.getCourse_Name());
			ps.setInt(12, bean.getSubject_id());
			ps.setString(13, bean.getSubject_Name());
			ps.setString(14, bean.getCreatedBy());
			ps.setString(15, bean.getModifiedBy());
			ps.setTimestamp(16, bean.getCreatedDatetime());
			ps.setTimestamp(17, bean.getModifiedDatetime());
			ps.setLong(18, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			//log.error("DATABASE EXCEPTION ...", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception is rollback of facultyModel");
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ApplicationException("Exception in update method of facultymodel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model update method End");
	}
	/**
	 * Find User by Faculty name.
	 *
	 * @param EmailId            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public FacultyBean findByEmail(String Emailid) throws ApplicationException {
        log.debug("Faculty Model findByName method Started");
		Connection conn = null;
		FacultyBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_faculty where email_id=?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, Emailid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
				bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            log.error("database exception ..." , e);
			throw new ApplicationException("Exception in findByEmailMethod");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model findbyName method End");
		return bean;
	}
	/**
	 * Find User by Faculty PK.
	 *
	 * @param pk            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public FacultyBean findByPk(long pk) throws ApplicationException {
		log.debug("Faculty Model findByPK method Started");
		Connection conn = null;
		FacultyBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_faculty where id=?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
				bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("database exception ..." , e);
			throw new ApplicationException("Exception in findByPk method of faculty method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model FindByPK method end");
		return bean;
	}
	/**
	 * Search Faculty.
	 *
	 * @param bean            : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List Search(FacultyBean bean) throws ApplicationException {
		return Search(bean, 0, 0);
	}
	/**
	 * Search Faculty with pagination.
	 *
	 * @param bean            : Search Parameters
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of Users
	 * @throws ApplicationException the application exception
	 */
	public List Search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model search  method Started");
		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getCourse_id() > 0) {
				sql.append(" and college_Id = " + bean.getCourse_id());
			}
			if (bean.getFirst_Name() != null && bean.getFirst_Name().trim().length() > 0) {
				sql.append(" AND First_Name like '" + bean.getFirst_Name() + "%' ");
			}
			if (bean.getLast_Name() != null && bean.getLast_Name().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLast_Name() + "%' ");
			}

			if (bean.getEmail_id() != null && bean.getEmail_id().length() > 0) {
				sql.append(" AND Email_Id like '" + bean.getEmail_id() + "%' ");
			}

			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND Gender like '" + bean.getGender() + "%' ");
			}

			if (bean.getMobile_No() != null && bean.getMobile_No().length() > 0) {
				sql.append(" AND Mobile_No like '" + bean.getMobile_No() + "%' ");
			}

			if (bean.getCollege_Name() != null && bean.getCollege_Name().length() > 0) {
				sql.append(" AND college_Name like '" + bean.getCollege_Name() + "%' ");
			}
			if (bean.getCourse_id() > 0) {
				sql.append(" AND course_Id = " + bean.getCourse_id());
			}
			if (bean.getCollege_Name() != null && bean.getCollege_Name().length() > 0) {
				sql.append(" AND course_Name like '" + bean.getCollege_Name() + "%' ");
			}
			if (bean.getSubject_id() > 0) {
				sql.append(" AND Subject_Id = " + bean.getSubject_id());
			}
			if (bean.getSubject_Name() != null && bean.getSubject_Name().length() > 0) {
				sql.append(" AND subject_Name like '" + bean.getSubject_Name() + "%' ");
			}
		}

		// if page no is greater then zero then apply pagination
		System.out.println("model page ........" + pageNo + " " + pageSize);
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		List list = new ArrayList();

		try {
			System.out.println(sql);
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
				bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//log.error("database Exception .. " , e);
			throw new ApplicationException("Exception in Search Method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//System.out.println("search run successfully");
		log.debug("Faculty Model search  method End");
		return list;
	}
	/**
	 * Get List of Faculty.
	 *
	 * @return list : List of Faculty
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}
	/**
	 * Get List of Faculty with pagination.
	 *
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of Faculty
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model List method Started");
		StringBuffer sql = new StringBuffer("select * from st_faculty");
		if (pageSize > 0) {
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		FacultyBean bean = null;
		List list = new ArrayList();

		try {
			System.out.println(sql);
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
				bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		log.error("Database Exception ......" , e);
			throw new ApplicationException("Exception in List method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		//System.out.println("check ");
		log.debug("Faculty Model List method End");
		return list;
	}

	public FacultyBean findByName(String firstname) throws ApplicationException {
	log.debug("Model FindByName Started ");
		StringBuffer sql = new StringBuffer("Select * from st_faculty where first_name=?");

		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, firstname);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirst_Name(rs.getString(2));
				bean.setLast_Name(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOJ(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmail_id(rs.getString(7));
				bean.setMobile_No(rs.getString(8));
				bean.setCollege_id(rs.getInt(9));
				bean.setCollege_Name(rs.getString(10));
				bean.setCourse_id(rs.getInt(11));
				bean.setCourse_Name(rs.getString(12));
				bean.setSubject_id(rs.getInt(13));
				bean.setSubject_Name(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();
		} catch (Exception e) {
		log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in findByName method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
    	log.debug("Model  findByName Ended");
		return bean;
	}
}