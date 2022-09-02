package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.Bean.CollegeBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.model.CollegeModel;


/**
 * College Model Test classes
 *
 * @author Sachin Mandloi
 
 */
public class CollegeModelTest {
	 /**
     * Model object to test
     */
	public static CollegeModel model = new CollegeModel();
	/**
     * Main method to call test methods.
   
     */
	public static void main(String[] args) throws Exception {
		testAdd();
		// testDelete();
		// testUpdate();
		// testFindByName();
		//testFindByPK();
		//testSearch();
//		testList();

	}
/**
 * test list of college
 */
	public static void testList() {
		CollegeBean bean = new CollegeBean();
		
		try {
			List list = new ArrayList();
			
			list = model.list(2, 1);
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();	
				System.out.println(bean.getId());
                System.out.println(bean.getName());
                System.out.println(bean.getAddress());
                System.out.println(bean.getState());
                System.out.println(bean.getCity());
                System.out.println(bean.getPhoneNo());
                System.out.println(bean.getCreatedBy());
                System.out.println(bean.getCreatedDatetime());
                System.out.println(bean.getModifiedBy());
                System.out.println(bean.getModifiedDatetime());
			}
		
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
	}
/**
 * test serarch of college
 */
	public static void testSearch() {
		CollegeBean bean = new CollegeBean();
		try {
			bean.setCity("Indore");
		
			List list = new ArrayList();
			list = model.search(bean, 1, 2);
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
		        System.out.println(bean.getAddress());
		        System.out.println(bean.getState());
		        System.out.println(bean.getCity());
		        System.out.println(bean.getPhoneNo());
		        System.out.println(bean.getCreatedBy());
		        System.out.println(bean.getCreatedDatetime());
		        System.out.println(bean.getModifiedBy());
		        System.out.println(bean.getModifiedDatetime());
			}
			
		}catch (ApplicationException e) {
            e.printStackTrace();
        }
		System.out.println("Search is success");
	}
	/**
	 * test findByPk of college
	 */
	public static void testFindByPK() throws Exception {
		CollegeBean bean = new CollegeBean();
	
		try {	
			bean = model.findByPk(1);
			
			System.out.println(bean.getId());
	        System.out.println(bean.getName());
	        System.out.println(bean.getAddress());
	        System.out.println(bean.getState());
	        System.out.println(bean.getCity());
	        System.out.println(bean.getPhoneNo());
	        System.out.println(bean.getCreatedBy());
	        System.out.println(bean.getCreatedDatetime());
	        System.out.println(bean.getModifiedBy());
	        System.out.println(bean.getModifiedDatetime());
	    } catch (ApplicationException e) {
	        e.printStackTrace();
	    }
			System.out.println("find by pk =success");
	}
	/**
	 * test findByName of college
	 */
	public static void testFindByName() throws Exception {
		CollegeBean bean = new CollegeBean();

		try {
		bean = model.findByName("Anshu"); // why we use bean object here
		
		System.out.println(bean.getId());
        System.out.println(bean.getName());
        System.out.println(bean.getAddress());
        System.out.println(bean.getState());
        System.out.println(bean.getCity());
        System.out.println(bean.getPhoneNo());
        System.out.println(bean.getCreatedBy());
        System.out.println(bean.getCreatedDatetime());
        System.out.println(bean.getModifiedBy());
        System.out.println(bean.getModifiedDatetime());
    } catch (ApplicationException e) {
        e.printStackTrace();
    }
		System.out.println("find by name =success");

	}
	/**
	 * test update of college
	 */
	public static void testUpdate() throws Exception {
		CollegeBean bean = new CollegeBean();
		bean.setId(2);

		model.update(bean);
		System.out.println("update=success");

	}
	/**
	 * test delete of college
	 */
	public static void testDelete() throws Exception {
		CollegeBean bean = new CollegeBean();
		bean.setId(2);

		model.delete(bean);
		System.out.println("delete = suceess in Marksheet");

	}
	/**
	 * test add of college
	 */
	public static void testAdd() throws Exception {

		CollegeBean bean = new CollegeBean();
		bean.setName("RGPV University");
		bean.setAddress("Bhopal");
		bean.setState("Madhya Pradesh");
		bean.setCity("Bhopal");
		bean.setPhoneNo("0731-123456");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);

		System.out.println("Add = success in Marksheet");
	}
}