package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "issued_books")
public class IssuedBooks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // id already generated
	
	@Column(name="id")
	private Integer id;
	
	
	@Column(name="student_id")
	private Integer studentId;
	
	@Column(name="book_id")
	private Integer bookId;
	
	@Column(name="date_of_issue")
	private Date dateOfIssue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	

}
