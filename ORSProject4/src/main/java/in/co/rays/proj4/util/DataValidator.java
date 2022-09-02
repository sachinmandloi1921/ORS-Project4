package in.co.rays.proj4.util;

import java.util.Date;
/**
 * This class validates input data
 *
 * @author Sachin Mandloi
 */
public class DataValidator {

	/**
     * Checks if value is Null
     *
     * @param val
     * @return
     */
    public static boolean isNull(String val) {
        if (val == null || val.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if value is NOT Null
     *
     * @param val
     * @return
     */
    public static boolean isNotNull(String val) {
        return !isNull(val);
    }

    /**
     * Checks if value is an Integer
     *
     * @param val
     * @return
     */

    public static boolean isInteger(String val) {

        if (isNotNull(val)) {
            try {
                int i = Integer.parseInt(val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Checks if value is Long
     *
     * @param val
     * @return
     */
    public static boolean isLong(String val) {
        if (isNotNull(val)) {
            try {
                long i = Long.parseLong(val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Checks if value is valid Email ID
     *
     * @param val
     * @return
     */
    public static boolean isEmail(String val) {

        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (isNotNull(val)) {
            try {
                return val.matches(emailreg);
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return false;
        }
    }
    /**
     * check if value is password.
     *
     * @param val the val
     * @return true, if is password
     */
    public static boolean isPassword(String val){
    	
    	String pass = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})";
    	
    			if (DataValidator.isNotNull(pass)) {
   					boolean check =  val.matches(pass);
   						return check;
    				}else
    				{	
    					return false;
   					}	
    		}

    /**
     * Checks if value is Date
     *
     * @param val
     * @return
     */
    public static boolean isDate(String val) {

        Date d = null;
        if (isNotNull(val)) {
            d = DataUtility.getDate(val);
        }
        return d != null;
    }

    /**
     * Test above methods
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Not Null 2" + isNotNull("ABC"));
        System.out.println("Not Null 3" + isNotNull(null));
        System.out.println("Not Null 4" + isNull("123"));

        System.out.println("Is Int " + isInteger(null));
        System.out.println("Is Int " + isInteger("ABC1"));
        System.out.println("Is Int " + isInteger("123"));
        System.out.println("Is Int " + isNotNull("123"));
    }
    public static boolean isName(String val){
     	
	    //	String namereg = "^[a-zA-Z]+$";
	   // 	String namereg = "^[a-zA-Z_-]+$";
	   	String namereg = "^[^-\\s][\\p{L} .']+$";
	    	

	    			if (DataValidator.isNotNull(val)) {
	    				boolean check = val.matches(namereg);
	    
							return check;
	    				}else
	    				{	
	    					return false;
						}	
	    		}
    
    public static boolean isMobileNo(String val){
    	
    	String mobreg = "^[6-9][0-9]{9}$";
    	
    			if (isNotNull(val) && val.matches(mobreg)) {
					
						return true;
    				}else
    				{	
    					return false;
					}	
    		}
    public static boolean isValidName(String val){
	 	
    	String namereg = "^[a-zA-Z_-]+$";
   
    	//	String namereg = "^[^-\\s][\\p{L} .']+$";
    	
    			if (DataValidator.isNotNull(val)) {
    				boolean check = val.matches(namereg);
    	
						return check;
    				}else
    				{	
    					return false;
					}	
    		}
    
    /**
     * check if value is Roll no.
     *
     * @param val the val
     * @return true, if is roll no
     */
    public static boolean isRollNo(String val){
    	
    	String roll = "^[A-Z]{1}[0-9]{3,6}$";
    
    			if (DataValidator.isNotNull(roll)) {
   					boolean check = val.matches(roll);
                    		return check;
    				}else
    				{	
    					return false;
   					}	
    		}
}