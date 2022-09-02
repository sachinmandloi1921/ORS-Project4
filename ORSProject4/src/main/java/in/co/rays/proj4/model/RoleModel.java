package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.RoleBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DatabaseException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of RoleModel
 *
 * @author Sachin Mandloi
 */
public class RoleModel {
	private static Logger log = Logger.getLogger(RoleModel.class);
	/** 
	* Find next PK of Role
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_ROLE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}
	/**
	 * Add a Role
	
	 */
	public long add(RoleBean bean) throws ApplicationException, DatabaseException {
		log.debug("Model and Started");
		Connection conn = null;
		int pk = 0;
		// How its work
//		  RoleBean duplicataRoleNo = findByName(bean.getName());
//	        // Check if create Role already exist
//	        if (duplicataRoleNo != null) {
//	            throw new DuplicateRecordException("RollNo already exists");
//	        }

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into ST_Role values (?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDatetime());
			ps.setTimestamp(7, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataException.", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception : rollback not work");
			}
			throw new DatabaseException("Exception : method add for role model not works");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}
	/**
	 * Delete a Role
	 */
	public void delete(RoleBean bean) throws ApplicationException, DatabaseException {
		log.debug("Model deleted started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from ST_ROLE where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception",e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception : rollback of delete not work");
			}
			throw new DatabaseException("Exception : method delete for role model not works");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete Started");
	}
	/**
	 * Find FindByName User by Role
	 */
	public RoleBean findByName(String name) throws ApplicationException {
		log.debug("Model FindBY EmailId Started");
		RoleBean bean = null;
		Connection conn =null;
		
		StringBuffer sql = new StringBuffer("select * from ST_ROLE where name=?");
		
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
	            bean.setName(rs.getString(2));
	            bean.setDescription(rs.getString(3));
	            bean.setCreatedBy(rs.getString(4));
	            bean.setModifiedBy(rs.getString(5));
	            bean.setCreatedDatetime(rs.getTimestamp(6));
	            bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in find by name");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model FindByEmail End");
		return bean;
	}
	/**
	 * Find Role by PK
	 */
	public RoleBean findByPK(long pk) throws ApplicationException {
		log.debug("Model FindByPK Started");
		RoleBean bean = null;
		Connection conn = null;
		
		StringBuffer sql = new StringBuffer("select * from ST_ROLE where id=?");
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean=new RoleBean();
				bean.setId(rs.getLong(1));
	            bean.setName(rs.getString(2));
	            bean.setDescription(rs.getString(3));
	            bean.setCreatedBy(rs.getString(4));
	            bean.setModifiedBy(rs.getString(5));
	            bean.setCreatedDatetime(rs.getTimestamp(6));
	            bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exception..",e);
			throw new ApplicationException("Exception in find by PK");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model FindByPK End");
		return bean;
	}
	/**
	 * Update a Role
	 */
	 public void update(RoleBean bean) throws ApplicationException, DatabaseException, DuplicateRecordException {
		 log.debug("Model Updated Started");
		 Connection conn =null;
		 //how this works
		 	RoleBean duplicataRole = findByName(bean.getName());
	        // Check if updated Role already exist
	        if (duplicataRole != null && duplicataRole.getId() != bean.getId()) {
	            throw new DuplicateRecordException("Role already exists");
	        }
		 
		 try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getCreatedBy());
			ps.setString(4, bean.getModifiedBy());
			ps.setTimestamp(5, bean.getCreatedDatetime());
			ps.setTimestamp(6, bean.getModifiedDatetime());
			ps.setLong(7, bean.getId());
			ps.executeUpdate();
            conn.commit(); 
            ps.close();
		} catch (Exception e) {
			log.error("Database Exception.., e");
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Role back is not done for Update of RoleBean");
			}
				throw new DatabaseException("Exception in update DB");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		 log.debug("Model Update End");
	 }
	 	/**
		 * Search Role
		 */
	 public List search(RoleBean bean) throws ApplicationException {
	        return search(bean, 0, 0);
	    }
	 	/**
		 * Search Role with pagination
		 */
	 public List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		 log.debug("Model Search Started");
		 StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getName() != null && bean.getName().length() > 0) {
	                sql.append(" AND NAME like '" + bean.getName() + "%'");
	            }
	            if (bean.getDescription() != null
	                    && bean.getDescription().length() > 0) {
	                sql.append(" AND DESCRIPTION like '" + bean.getDescription()
	                        + "%'");
	            }

	        }

	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate starting index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        List list = new ArrayList();
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                bean = new RoleBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setDescription(rs.getString(3));
	                bean.setCreatedBy(rs.getString(4));
	                bean.setModifiedBy(rs.getString(5));
	                bean.setCreatedDatetime(rs.getTimestamp(6));
	                bean.setModifiedDatetime(rs.getTimestamp(7));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            throw new ApplicationException(
	                    "Exception : Exception in search method of RoleModel");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	    log.debug("Model search end");
		return list;
		}
	 	/**
		 * Get List of Role with pagination
		 */
	 public List list() throws ApplicationException {
	        return list(0, 0);
	    }
	
	 public List list(int pageNo, int pageSize) throws ApplicationException {
		 log.debug("Model list Started");
		 List list = new ArrayList();
		 
		 StringBuffer sql = new StringBuffer("select * from ST_ROLE");
	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql.toString());
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                RoleBean bean = new RoleBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setDescription(rs.getString(3));
	                bean.setCreatedBy(rs.getString(4));
	                bean.setModifiedBy(rs.getString(5));
	                bean.setCreatedDatetime(rs.getTimestamp(6));
	                bean.setModifiedDatetime(rs.getTimestamp(7));
	                list.add(bean);
	            }
	            rs.close();
	        }catch (Exception e) {
	        	log.error("Database Exception..", e);
	        	 //throw new ApplicationException("Exception : Exception in getting list of Role");
			}finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model list End");
	        return list;
	 }
}
