package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.CollegeBean;
import in.co.rays.proj4.Bean.StudentBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DatabaseException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of StudentModel
 *
 * @author Sachin Mandloi
 */
public class StudentModel {
	private static Logger log = Logger.getLogger(StudentModel.class);
	/**
	 * Find next PK of Student
	 *
	 * @throws DatabaseException
	 */
	public Integer nextPk() throws DatabaseException {
		log.debug("Model nextPK Started");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_student");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk= rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new DatabaseException("nextPk=failed");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk+1;
	}
	/**
	 * Add a Student
	
	 *
	 */
	public int add(StudentBean bean) throws ApplicationException, DatabaseException {
		log.debug("Model add Started");
		Connection conn= null;
		int pk=0;
		
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPk();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setLong(2, bean.getCollegeId());
			ps.setString(3, bean.getCollegeName());
			ps.setString(4, bean.getFirstName());
			ps.setString(5, bean.getLastName());
			ps.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			ps.setString(7, bean.getMobileNo());
			ps.setString(8, bean.getEmail());
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11, bean.getCreatedDatetime());
			ps.setTimestamp(12, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: rollback exception in add method of Student");
			}
			e.printStackTrace();
			throw new DatabaseException("DB not added the data");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}
	/**
	 * Delete a Student
	 */
	public void delete(StudentBean bean) throws ApplicationException, DatabaseException {
		log.debug("Model delete Started");
		Connection conn=null;
		
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from ST_STUDENT where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: rollback exception in delete ");
			}
			e.printStackTrace();
			throw new DatabaseException("DB cant delete the data");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		//System.out.println("delete runs successfully");
		log.debug("Model delete Started");
	}
	/**
	 * Find User by Student
	
	 */
	 public StudentBean findByEmailId(String Email) throws ApplicationException {
		log.debug("Model findBy Email Started");
		StudentBean bean = null;
		Connection conn=null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE EMAIL=?");
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, Email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getInt(1));
				bean.setCollegeId(rs.getInt(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));	
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: find by email method isn't run");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Email End");
		return bean;
	 }
	 	/**
		 * Find Student by PK
		
		 */
	 public StudentBean findByPK(long pk) throws ApplicationException {
		 log.debug("Model findByPK Started");
		StudentBean bean = null;
		Connection conn = null;
		StringBuffer sql =  new StringBuffer("SELECT * FROM ST_STUDENT WHERE ID=?");
		
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs= ps.executeQuery();
			while (rs.next()) {
				bean= new StudentBean();
				bean.setId(rs.getInt(1));
				bean.setCollegeId(rs.getInt(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: DB cant run findByPK method ");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	 }
	 	/**
		 * Update a Student
		 */
	 public void update(StudentBean bean) throws ApplicationException, DatabaseException, DuplicateRecordException {
		 log.debug("Model update Started");
		 Connection conn = null;
		 StudentBean beanExist = findByEmailId(bean.getEmail());

	        // Check if updated Roll no already exist
	        if (beanExist != null && beanExist.getId() != bean.getId()) {
	            throw new DuplicateRecordException("Email Id is already exist");
	        }

	        // get College Name
	        CollegeModel cModel = new CollegeModel();
	        CollegeBean collegeBean = cModel.findByPk(bean.getCollegeId());
	        bean.setCollegeName(collegeBean.getName());
		 
		 try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setLong(1, bean.getCollegeId());
			ps.setString(2, bean.getCollegeName());
			ps.setString(3, bean.getFirstName());
			ps.setString(4, bean.getLastName());
			ps.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			ps.setString(6, bean.getMobileNo());
			ps.setString(7, bean.getEmail());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.setLong(12, bean.getId());
			
			ps.executeUpdate();
			conn.commit();
			ps.close();
		 }catch (Exception e) {
			 log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: rollback isnt work on update method");
			}
			e.printStackTrace();
			throw new DatabaseException("DB cant update");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		 log.debug("Model update End");
		 //System.out.println("update done");
	 }
	 	/**
		 * Search Student
		 
		 */
	 public List search(StudentBean bean) throws ApplicationException{
		return search(bean, 0, 0);
		 
	 }
	 	/**
		 * Search Student with pagination
		 */
	 public List search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
		 log.debug("Model search Started");
		 StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE 1=1");
		
		 if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
	                sql.append(" AND FIRST_NAME like '" + bean.getFirstName()
	                        + "%'");
	            }
	            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
	                sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
	            }
	            if (bean.getDob() != null && bean.getDob().getDate() > 0) {
	                sql.append(" AND DOB = " + bean.getDob());
	            }
	            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
	                sql.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
	            }
	            if (bean.getEmail() != null && bean.getEmail().length() > 0) {
	                sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
	            }
	            if (bean.getCollegeName() != null
	                    && bean.getCollegeName().length() > 0) {
	                sql.append(" AND COLLEGE_NAME = " + bean.getCollegeName());
	            }

	        }

	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }
	        
	        List list = new ArrayList();
	        Connection conn=null;
	        try {
				conn=JDBCDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					bean = new StudentBean();
					bean.setId(rs.getLong(1));
	                bean.setCollegeId(rs.getLong(2));
	                bean.setCollegeName(rs.getString(3));
	                bean.setFirstName(rs.getString(4));
	                bean.setLastName(rs.getString(5));
	                bean.setDob(rs.getDate(6));
	                bean.setMobileNo(rs.getString(7));
	                bean.setEmail(rs.getString(8));
	                bean.setCreatedBy(rs.getString(9));
	                bean.setModifiedBy(rs.getString(10));
	                bean.setCreatedDatetime(rs.getTimestamp(11));
	                bean.setModifiedDatetime(rs.getTimestamp(12));
	                list.add(bean);
				}rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception: Exception in search Student");
			}finally {
				JDBCDataSource.closeConnection(conn);
			}
	        //System.out.println("search runs of StudentModel");
	        log.debug("Model search End");
		 return list; 
	 }
	 	/**
		 * Get List of Student
		 */
	 public List list() throws ApplicationException {
	        return list(0, 0);
	    }
	 	/**
		 * Get List of Student with pagination
		 */
	 public List list(int pageNo, int pageSize) throws ApplicationException {
		 log.debug("Model list Started");
		 	StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit " + pageNo + "," + pageSize);
			}		 
			List list = new ArrayList();
			Connection conn=null;
			
			try {
				conn=JDBCDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					StudentBean bean = new StudentBean();
					bean.setId(rs.getInt(1));
					bean.setCollegeId(rs.getInt(2));
					bean.setCollegeName(rs.getString(3));
					bean.setFirstName(rs.getString(4));
					bean.setLastName(rs.getString(5));
					bean.setDob(rs.getDate(6));
					bean.setMobileNo(rs.getString(7));
					bean.setEmail(rs.getString(8));
					bean.setCreatedBy(rs.getString(9));
					bean.setModifiedBy(rs.getString(10));
					bean.setCreatedDatetime(rs.getTimestamp(11));
					bean.setModifiedDatetime(rs.getTimestamp(12));
					list.add(bean);
		
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception: Exception in list method of Student model");
			}finally {
				JDBCDataSource.closeConnection(conn);
			}
			//System.out.println("method list = successfull");
			log.debug("Model list End");
		 return list;
	 }
}