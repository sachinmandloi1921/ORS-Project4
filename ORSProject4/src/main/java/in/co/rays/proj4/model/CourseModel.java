package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.Bean.CourseBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of CourseModel
 *
 * @author Sachin Mandloi
 */
public class CourseModel {
	private static Logger log = Logger.getLogger(CourseModel.class);

		/**
		 * Find next PK of Course.
		
		 */
	public Integer nextPk() {
		log.debug("CourseModel nextPK method started");
		Connection conn = null;
		int pk=0;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_course");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Exception in Database..", e);
			e.printStackTrace();
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel nextPK method END");
		return pk+1;
	}
	/**
	 * Add a Course.
	 *
	 * @param bean the bean
	 * @return the int
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public int add(CourseBean bean) throws ApplicationException {
		log.debug("CourseModel Add method END");
		Connection conn=null;
		int pk=0;
		
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPk();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
			ps.setInt(1, pk);
			ps.setString(2, bean.getCourse_Name());
			ps.setString(3, bean.getDiscription());
			ps.setString(4, bean.getDuration());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDatetime());
			ps.setTimestamp(8, bean.getModifiedDatetime());
			
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("EXception in Database...", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception : rollback exception in add method of CourseModel");
			}
			e.printStackTrace();
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel Add method END");
		return pk;
	}
	/**
	 * Delete a Course.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(CourseBean bean) throws ApplicationException {
		log.debug("CourseModel Delete Method Started");
		Connection conn=null;
		
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception...", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in rollback of delete method of CourseModel");
			}
			throw new ApplicationException("Exception: Delete method not working" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel Delete Method End");
	}
	/**
	 * Update a Course.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		
		Connection conn = null;
		
		CourseBean beanExist = findByName(bean.getCourse_Name());
		if(beanExist !=null && beanExist.getId()!=bean.getId()){
			throw new DuplicateRecordException("Course Already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_COURSE SET COURSE_NAME=?,DISCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getCourse_Name());
			ps.setString(2, bean.getDiscription());
			ps.setString(3, bean.getDuration());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDatetime());
			ps.setTimestamp(7, bean.getModifiedDatetime());
			
			ps.setLong(8, bean.getId());
			
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Exception in Rollback.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating the Course Model");
		} finally {
			//System.out.println("update=success");
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * Find User by Course.
	 *
	 * @param name            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public CourseBean findByName(String name) throws ApplicationException {
		log.debug("Course Model FindByName method Started");
		CourseBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE COURSE_NAME=?");
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1,name );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setDiscription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		log.debug("Database Exception ..", e);
			throw new ApplicationException("Exception in FindByName Method of CourseModel ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Course Model FindByName method End");
		return bean;
	}
	/**
	 * Find User by Course.
	 *
	 * @param pk            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public CourseBean findByPk(long pk) throws ApplicationException {
		log.debug("CourseModel FindbyPK method Started");
		CourseBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
	
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
				bean.setDiscription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
			}
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
			log.error("DatabaseException ... ", e);
			throw new ApplicationException("Exception : Exception in the findbyPk method of CourseModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	log.debug("CourseModel FindbyPK method End");
		return bean;
	}
	/**
	 * Search Course.
	 *
	 * @param bean            : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search (CourseBean bean) throws ApplicationException{
		return search (bean,0,0);	
		}
	/**
	 * Search Course with pagination.
	 *
	 * @param bean            : Search Parameters
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of Users
	 * @throws ApplicationException the application exception
	 */
	
public List search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
	log.debug("CourseModel Search Method Started");	
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		if(bean!=null){
			if(bean.getId()>0){
				sql.append(" AND id = " + bean.getId());
			}
			if(bean.getCourse_Name()!= null && bean.getCourse_Name().length()>0){
				sql.append(" AND Course_Name  like '" + bean.getCourse_Name() +"%'");
			}
			if(bean.getDiscription()!=null && bean.getCourse_Name().length()>0){
				sql.append(" AND Description like '" + bean.getDiscription() + "%'");
			}
			if(bean.getDuration()!=null && bean.getDuration().length() >0){
				sql.append(" AND Duration like '" + bean.getDuration() + "%'");
			}
			
		}
		
		// if page size is greater than zero then apply pagination
		if(pageSize>0){
			pageNo = (pageNo-1)*pageSize;
			sql.append(" limit " +pageNo+","+pageSize);	
		}
		List list = new ArrayList();
		Connection conn = null;
		try{
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			System.out.println(sql);
			ResultSet rs =ps.executeQuery();
			while(rs.next()){
				bean=new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));
			    bean.setDiscription(rs.getString(3));
			    bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception ,,,," , e);
			throw new ApplicationException("Exception in Search Method of CourseModel");
		
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel Search Method End");
		return list;
	}
/**
 * Get List of Course.
 *
 * @return list : List of Course
 * @throws ApplicationException the application exception
 */
	public List list() throws ApplicationException{
		return list(0,0);
	}
	/**
	 * Get List of Course with pagination.
	 *
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of Course
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("CourseModel List Method End");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE ");
		if(pageSize>0){
			pageNo = (pageNo-1)*pageSize;
			sql.append(" limit "+ pageNo +", "+pageSize);
		}
		
		List list = new ArrayList();
		Connection conn = null;
		
		try{
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps =conn.prepareStatement(sql.toString());
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				CourseBean bean = new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setCourse_Name(rs.getString(2));	
				bean.setDiscription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception in list ...",e);
			throw new ApplicationException("Exception : Exception in List method of CourseModel  " + e.getMessage());		
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel List Method End");
		return list;
	}
}