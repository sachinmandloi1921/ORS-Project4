package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.MarksheetBean;
import in.co.rays.proj4.Bean.StudentBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DatabaseException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of MarksheetModel
 *
 * @author Sachin Mandloi
 */
public class MarksheetModel {
	
	private Logger log = Logger.getLogger(MarksheetModel.class);
	
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_MARKSHEET");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new DatabaseException("Exception: Unable to create PK in Marksheet table");
		}
		JDBCDataSource.closeConnection(conn);
		log.debug("Model nextPK End");
		return pk + 1;
	}
	/**
	 * Adds a Marksheet
	 *
	 */
	public long add(MarksheetBean bean) throws ApplicationException, Exception, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		
		 // get Student Name
        StudentModel sModel = new StudentModel();
        StudentBean studentbean = sModel.findByPK(bean.getStudentId());
        bean.setName(studentbean.getFirstName() + " "
                + studentbean.getLastName());

        MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());
        int pk = 0;

        if (duplicateMarksheet != null) {
            throw new DuplicateRecordException("Roll Number already exists");
        }
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getRollNo());
			ps.setLong(3, bean.getStudentId());
			ps.setString(4, bean.getName());
			ps.setInt(5, bean.getPhysics());
			ps.setInt(6, bean.getChemistry());
			ps.setInt(7, bean.getMaths());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: rollback is not done");
			}
			throw new DatabaseException("Exception: not added in Database");
		}
		JDBCDataSource.closeConnection(conn);
		log.debug("Model add End");
		return pk;
	}
	/**
	 * Deletes a Marksheet
	
	 */
	public void delete(MarksheetBean bean) throws ApplicationException, DatabaseException {
		log.debug("Model delete Started");
		Connection conn = null;
		long id = bean.getId();

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_MARKSHEET WHERE ID=?");
			ps.setLong(1, id);
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				log.error(e2);
				throw new ApplicationException("Exception : rollback isn't work");
			}
			throw new DatabaseException("Exception: ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete End");
	}
	/**
	 * Finds Marksheet by Roll No
	 */
	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		log.debug("Model findByRollNo Started");
		MarksheetBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, rollNo);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new ApplicationException("Exception: Exception is findByRollNo method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByRollNo End");
		return bean;
	}
	/**
	 * Finds Marksheet by PK
	 
	 */
	public MarksheetBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");

		MarksheetBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ID=?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new ApplicationException("Exception: findByPk method not working");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}
	/**
	 * Updates a Marksheet
	 */
	public void update(MarksheetBean bean) throws ApplicationException, DatabaseException {
		log.debug("Model update Started");
		Connection conn = null;
		
		 StudentModel sModel = new StudentModel();
	        StudentBean studentbean = sModel.findByPK(bean.getStudentId());
	        bean.setName(studentbean.getFirstName() + " "
	                + studentbean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getRollNo());
			ps.setLong(2, bean.getStudentId());
			ps.setString(3, bean.getName());
			ps.setInt(4, bean.getPhysics());
			ps.setInt(5, bean.getChemistry());
			ps.setInt(6, bean.getMaths());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.setLong(11, bean.getId());

			ps.executeUpdate();
			ps.close();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: rollback isnt done");
			}
			throw new DatabaseException("Exception: Database isnt upadated");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Searches Marksheet
	
	 */
	 public List search(MarksheetBean bean) {
	 
	 return search(bean,0,0);
	  
	  }
	 
	 /**
	  * Searches Marksheet with pagination
		
	  */
	 public List search(MarksheetBean bean, int pageNo, int pageSize) {
		log.debug("Model search Started");
		 List list = new ArrayList();
		 
		 StringBuffer sql = new StringBuffer("select * from ST_MARKSHEET where true");
		 
		 if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
	                sql.append(" AND roll_no like '" + bean.getRollNo() + "%'");
	            }
	            if (bean.getName() != null && bean.getName().length() > 0) {
	                sql.append(" AND name like '" + bean.getName() + "%'");
	            }
	            if (bean.getPhysics() != null && bean.getPhysics() > 0) {
	                sql.append(" AND physics = " + bean.getPhysics());
	            }
	            if (bean.getChemistry() != null && bean.getChemistry() > 0) {
	                sql.append(" AND chemistry = " + bean.getChemistry());
	            }
	            if (bean.getMaths() != null && bean.getMaths() > 0) {
	                sql.append(" AND maths = '" + bean.getMaths());
	            }

	        }

	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }
		 
		 Connection conn= null;
		 
		 try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
		
			while (rs.next()) {
				
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
                bean.setStudentId(rs.getLong(3));
                bean.setName(rs.getString(4));
                bean.setPhysics(rs.getInt(5));
                bean.setChemistry(rs.getInt(6));
                bean.setMaths(rs.getInt(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));
                list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		 finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		 return list;
	 }
	 
	 /**
	  * Gets List of Marksheet
		
	  */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_MARKSHEET");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception: DB cant show list");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
		
	}
	/**
	 * get Merit List of Marksheet with pagination
	 */
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model MeritList Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT ID, ROLL_NO, NAME, PHYSICS, CHEMISTRY, MATHS, (PHYSICS + CHEMISTRY + MATHS) as total from ST_MARKSHEET WHERE PHYSICS>33 AND CHEMISTRY>33 AND MATHS>33 order by total desc");
		
		 if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" Limit " + pageNo + "," + pageSize);
	        }
		 
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getString(2));
                bean.setName(rs.getString(3));
                bean.setPhysics(rs.getInt(4));
                bean.setChemistry(rs.getInt(5));
                bean.setMaths(rs.getInt(6));
                list.add(bean);
			}
			rs.close();
			
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception: Merit list cant show");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}		
		log.debug("Model MeritList End");
		return list;

	}
}