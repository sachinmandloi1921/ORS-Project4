package in.co.rays.proj4.test;

import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.Bean.CourseBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
/**
 * Course Model Test classes
 *
 * @author Sachin Mandloi
 
 */
public class CourseModelTest {
	 /**
     * Model object to test
     */
	private static CourseModel model = new CourseModel();
	/**
     * Main method to call test methods.
   
     */
	public static void main(String[] args) throws ApplicationException {
		 	testAdd();
		   	// testDelete();
		    //testUpdate();
			//testfindByName();
			//testFindByPK();
//			testSearch();
			//testList();
	}
	/**
	 * Test list of course
	 */
	public static void testList() {
		CourseBean bean = new CourseBean();
		List list = null; 
		
		try {
			list = model.list();
			if (list.size()==0) {
				System.out.println("List is empty");
			}
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
	            System.out.println(bean.getCourse_Name());
	            System.out.println(bean.getDiscription());
	            System.out.println(bean.getDuration());
	            System.out.println(bean.getCreatedBy());
	            System.out.println(bean.getCreatedDatetime());
	            System.out.println(bean.getModifiedBy());
	            System.out.println(bean.getModifiedDatetime());

				
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * test search of course
	 */
	private static void testSearch() {
		CourseBean bean = new CourseBean();
		bean.setCourse_Name("BBA");
		List list = null;
		
		try {
			list = model.search(bean,1,1);
			if (list.size()==0) {
				System.out.println("Search List is empty");
			}
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
		            System.out.println(bean.getId());
		            System.out.println(bean.getCourse_Name());
		            System.out.println(bean.getDiscription());
		            System.out.println(bean.getDuration());
		            System.out.println(bean.getCreatedBy());
		            System.out.println(bean.getCreatedDatetime());
		            System.out.println(bean.getModifiedBy());
		            System.out.println(bean.getModifiedDatetime());
		            
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * test findByPk of course
	 */
	public static void testFindByPK() {
		try {
			CourseBean bean = model.findByPk(6);
			if (bean==null) {
				System.out.println("testfindbyPK  = failed");
			}
				System.out.println(bean.getId());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getDiscription());
				System.out.println(bean.getDuration());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * test findByName of course
	 */
	public static void testfindByName() {
		try {
			CourseBean bean = model.findByName("LLB");
			
			if (bean==null) {
				System.out.println("testfindbyName  = failed");
			}
			
				System.out.println(bean.getId());
				System.out.println(bean.getCourse_Name());
				System.out.println(bean.getDiscription());
				System.out.println(bean.getDuration());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * test update of course
	 */
	public static void testUpdate() throws ApplicationException {
		CourseBean bean =  model.findByPk(6);
		
		bean.setCourse_Name("LLB");
		bean.setDuration("3 years");
		try {
			model.update(bean);
			
			 //check the required changes made or not
			CourseBean checkUpdate = new CourseBean();
			checkUpdate = model.findByPk(6);
			
//			if (!"Ph.d".equals(checkUpdate.getCourse_Name())) {
//				System.out.println("testUpdate fail");
//			}
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * test delete of course
	 */
	public static void testDelete() {
		CourseBean bean = new CourseBean();
		long pk = 6;
		bean.setId(pk);
		
		try {
			model.delete(bean);
			CourseBean delBean = model.findByPk(pk);
			if (delBean!=null) {
				System.out.println("testDelete=fail");
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		
	}
	/**
	 * test add of course
	 */
	private static void testAdd() {
		CourseBean bean = new CourseBean();
		bean.setCourse_Name("BE");
		bean.setDiscription("Bachelor of Engineering");
		bean.setDuration("4 years");
		
		try {
			long pk = model.add(bean);
			CourseBean addedBean = model.findByPk(pk);
			if (addedBean==null) {
				System.out.println("test add = fail");
			}	
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}