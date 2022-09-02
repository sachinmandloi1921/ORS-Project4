package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.CollegeBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of CollegeModel
 *
 * @author Sachin Mandloi
 * 
 */
public class CollegeModel {
	/**
	 * Find next PK of College
	 *
	 
	 *
	 */
	private static Logger log = Logger.getLogger(CollegeModel.class);
	public Integer nextPk() throws Exception{
		Connection conn = null;
		int pk=0;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_college");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new DatabaseException("Exception: method nextPk is not working");
		}
		
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk+1;
	}
	/**
	 * Add a College
	 
	 */
	public long add(CollegeBean bean) throws Exception  {
		log.debug("Model add Started");
		Connection conn =null;
		// Get auto-generated next primary key
		int pk =0;
		
		conn = JDBCDataSource.getConnection();
		pk=nextPk();
		
		try{
			conn.setAutoCommit(false);
		
		
		PreparedStatement ps = conn.prepareStatement("insert into st_college values (?,?,?,?,?,?,?,?,?,?)");
			
			ps.setInt(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getAddress());
			ps.setString(4, bean.getState());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getPhoneNo());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			
			ps.executeUpdate();
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Exception in rollback"+e2.getMessage());
			}finally {
				JDBCDataSource.closeConnection(conn);
			}
			
		}
		log.debug("Model add End");
		return pk ; 
	}
	
	/**
	 * delete by CollegeBean
	 */
	public void delete(CollegeBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn =null;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement("delete from st_college where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: rollback");
				
			}
			 throw new ApplicationException("Exception : Exception in delete college");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}
	
	/**
	 * Find User by College
	 */
	public CollegeBean findByName(String name) throws ApplicationException {
		log.debug("Model findByName Started");
		CollegeBean bean =null;
		Connection conn =null;
		
		try {
			conn=JDBCDataSource.getConnection();
			
			StringBuffer sql = new StringBuffer("select * from st_college where name=?");
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				bean = new CollegeBean();
				
					bean.setId(rs.getLong(1));
				 	bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in finding College Name");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName End");
		return bean;
	}
	
	/**
	 * Find User by College
	 */
	public CollegeBean findByPk(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		Connection conn =null;
		CollegeBean bean = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			StringBuffer sql = new StringBuffer("select * from st_college where id=?");
			PreparedStatement ps =  conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				bean = new CollegeBean();
				
					bean.setId(rs.getLong(1));
				 	bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: in College find with pk");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}
	/**
	 * Update a College
	
	 */
	public void update(CollegeBean bean) throws ApplicationException, DatabaseException {
		log.debug("Model update Started");
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
				ps.setString(1, bean.getName());
				ps.setString(2, bean.getAddress());
				ps.setString(3, bean.getState());
				ps.setString(4, bean.getCity());
				ps.setString(5, bean.getPhoneNo());
				ps.setString(6, bean.getCreatedBy());
				ps.setString(7, bean.getModifiedBy());
				ps.setTimestamp(8, bean.getCreatedDatetime());
				ps.setTimestamp(9, bean.getModifiedDatetime());			
				ps.setLong(10, bean.getId());
				ps.executeUpdate();
				ps.close();
				conn.commit();
				
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				
				throw new ApplicationException("Exception: in rollback of update"+ e2.getMessage());
			}
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception: Exception in update database");
			
		}finally {
			JDBCDataSource.closeConnection(conn);
		}	
		log.debug("Model update End");
	}
	/**
	 * Search College with pagination
	
	*/
	 public List search(CollegeBean bean, int pageNo, int pageSize)
	            throws ApplicationException {
		log.debug("Model search Started");
	        StringBuffer sql = new StringBuffer(
	                "SELECT * FROM ST_COLLEGE WHERE 1=1");

	        if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" AND id = " + bean.getId());
	            }
	            if (bean.getName() != null && bean.getName().length() > 0) {
	                sql.append(" AND NAME like '" + bean.getName() + "%'");
	            }
	            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
	                sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
	            }
	            if (bean.getState() != null && bean.getState().length() > 0) {
	                sql.append(" AND STATE like '" + bean.getState() + "%'");
	            }
	            if (bean.getCity() != null && bean.getCity().length() > 0) {
	                sql.append(" AND CITY like '" + bean.getCity() + "%'");
	            }
	            if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
	                sql.append(" AND PHONE_NO = " + bean.getPhoneNo());
	            }

	        }

	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;

	            sql.append(" Limit " + pageNo + ", " + pageSize);
	            // sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        ArrayList list = new ArrayList();
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                bean = new CollegeBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	        	log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in search college");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	     log.debug("Model search End");
	        return list;
	    }
	 /**
	  * Get List 
		
	  */
	 public List list() throws ApplicationException {
			return list(0,0);
		}
	 
	 /**
	  * Get List of College with pagination
		
	  */
	 public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		 ArrayList list = new ArrayList();
		 StringBuffer sql = new StringBuffer("select * from st_college");
		 
		 // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {						// how it is work??
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
	            	
	                CollegeBean bean = new CollegeBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	        	log.error("Database Exception..", e);
	            throw new ApplicationException("Exception : Exception in list");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        //System.out.println("list method runs successfully");
	     log.debug("Model list End");
		return list;
	 }
}