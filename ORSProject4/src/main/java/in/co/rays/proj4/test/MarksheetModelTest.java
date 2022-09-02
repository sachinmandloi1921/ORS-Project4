package in.co.rays.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.Bean.MarksheetBean;
import in.co.rays.proj4.Exception.ApplicationException;
import in.co.rays.proj4.Exception.DatabaseException;
import in.co.rays.proj4.model.MarksheetModel;
/**
 * Marksheet Model Test classes
 *
 * @author Sachin Mandloi
 
 */
public class MarksheetModelTest {
	 /**
     * Model object to test
     */
	public static MarksheetModel model = new MarksheetModel();
	/**
     * Main method to call test methods.
   
     */
	public static void main(String[] args) throws Exception {
		//testAdd();
		// testDelete();
		// testUpdate();
		// testFindByRollNo();
		// testFindByPK();
		// testSearch();
		//testMeritList();
		//testList();   
	}
	/**
	 * test list of Marksheet
	 */
	public static void testList() {
			try {
				MarksheetBean bean = new MarksheetBean();
				List list = new ArrayList();
				list = model.list(1, 10);
					 Iterator it = list.iterator();
			            while (it.hasNext()) {
			                bean = (MarksheetBean) it.next();
			                System.out.println(bean.getId());
			                System.out.println(bean.getRollNo());
			                System.out.println(bean.getName());
			                System.out.println(bean.getPhysics());
			                System.out.println(bean.getChemistry());
			                System.out.println(bean.getMaths());
			                System.out.println(bean.getCreatedBy());
			                System.out.println(bean.getCreatedDatetime());
			                System.out.println(bean.getModifiedBy());
			                System.out.println(bean.getModifiedDatetime());
			            }
				}
			 catch (ApplicationException e) {
				e.printStackTrace();
			}
			
		}
	/**
	 * test merit list of Marksheet
	 */
	public static void testMeritList() {
		 try {
	            MarksheetBean bean = new MarksheetBean();
	            List list = new ArrayList();
	            list = model.getMeritList(1, 10);
	            if (list.size() <= 0) {
	                System.out.println("Test List fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (MarksheetBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getRollNo());
	                System.out.println(bean.getName());
	                System.out.println(bean.getPhysics());
	                System.out.println(bean.getChemistry());
	                System.out.println(bean.getMaths());
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

	}
	/**
	 * test search of Marksheet
	 */
	public static void testSearch() throws ApplicationException {
		MarksheetBean bean = new MarksheetBean();
		List list = new ArrayList();
		// bean.setId(2);
		bean.setRollNo("101");
		// list = model.search(bean, 1, 1);
		list = model.search(bean);
		System.out.println("check1");
		if (list.size() < 0) {
			System.out.println("test serch is failed");
		}

		Iterator it = list.iterator();
		while (it.hasNext()) {
			System.out.println("inside loop");
			bean = (MarksheetBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		}

		System.out.println("check2");
	}
	/**
	 * test FindByPk of Marksheet
	 */
	public static void testFindByPK() {
		try {
			MarksheetBean bean = new MarksheetBean();
			bean = model.findByPK(2);

			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * test FindByRollNo of Marksheet
	 */
	public static void testFindByRollNo() {

		try {
			MarksheetBean bean = model.findByRollNo("101");
			if (bean == null) {
				System.out.println("Roll No. is invalid");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * test update of Marksheet
	 */
	public static void testUpdate() throws Exception {
		try {
			MarksheetBean bean;
			bean = model.findByPK(2);
			bean.setChemistry(99);
			bean.setMaths(99);

			model.update(bean);

			/*
			 * MarksheetBean updatedbean = model.findByPK(2L);
			 * System.out.println("Test Update succ"); if
			 * (!"IIM".equals(updatedbean.getName())) {
			 * System.out.println("Test Update fail"); }
			 */

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

//	public static void testAdd() {
	//	try {
		//	MarksheetBean bean = new MarksheetBean();
		//	bean.setRollNo("101");
		//	bean.setPhysics(60);
		//	bean.setChemistry(45);
		//	bean.setMaths(35);
	//		bean.setStudentId(2022101L);
		//	bean.setCreatedBy("Admin");
		//	bean.setModifiedBy("Admin");
	//		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
    //			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	//		model.add(bean);
	//	} catch (ApplicationException e) {
		//	e.printStackTrace();
	//	} catch (DatabaseException e) {
		//	e.printStackTrace();
//		}
//s	}
	/**
	 * test delete of Marksheet
	 */
	public static void testDelete() {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 1L;
			bean.setId(pk);

			model.delete(bean);
			MarksheetBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
}