import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="issue_bk_dtl")
public class IssuBookDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "student_id")
	private Integer Studentid;
	
	@Column(name = "book_id")
	private Integer Bookid;
	
	@Column(name = "dateofissue")
	private Timestamp DateOfIssue;

	public void setDateOfIssue(Timestamp dateOfIssue) {
		DateOfIssue = dateOfIssue;
	}

	public Integer getStudentid() {
		return Studentid;
	}

	public void setStudentid(Integer studentid) {
		Studentid = studentid;
	}

	public Integer getBookid() {
		return Bookid;
	}

	public void setBookid(Integer bookid) {
		Bookid = bookid;
	}

	
	
}
