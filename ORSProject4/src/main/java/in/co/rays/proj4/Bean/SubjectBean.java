package in.co.rays.proj4.Bean;
/**
 * Subject JavaBean encapsulates Subject attributes
 *
 * @author Sachin Mandloi
 */
public class SubjectBean extends BaseBean{
	
	private String Subject_Name;
	private String Course_Name;
	private int Course_Id;
	private String Discription;
	
	public String getSubject_Name() {
		return Subject_Name;
	}
	public void setSubject_Name(String subject_Name) {
		Subject_Name = subject_Name;
	}
	public String getCourse_Name() {
		return Course_Name;
	}
	public void setCourse_Name(String course_Name) {
		Course_Name = course_Name;
	}
	public int getCourse_Id() {
		return Course_Id;
	}
	public void setCourse_Id(int course_Id) {
		Course_Id = course_Id;
	}
	public String getDiscription() {
		return Discription;
	}
	public void setDiscription(String discription) {
		Discription = discription;
	}
	public String getKey() {
		return id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return Subject_Name;
	}
	
}

