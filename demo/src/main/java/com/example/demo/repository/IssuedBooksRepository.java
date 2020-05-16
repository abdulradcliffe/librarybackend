package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.IssuedBooks;

@Repository
public interface IssuedBooksRepository extends JpaRepository<IssuedBooks, Integer> {

	@Query(value = "SELECT count(*) FROM issued_books where  student_id=:studentid ", nativeQuery = true)
	public Integer countOfBookByStudent(@Param("studentid") Integer studentId);
	
	@Query(value = "SELECT * FROM issued_books where  student_id=:studentid and book_id=:bookid ", nativeQuery = true)
	IssuedBooks isAlreadyIssued(@Param("studentid") Integer studentId, @Param("bookid") Integer bookId );

	@Modifying
	@Query(value = "Delete FROM issued_books where  student_id=:studentid and book_id=:bookid ", nativeQuery = true)
    void returnBook (@Param("studentid") Integer studentId, @Param("bookid") Integer bookId );
 
	@Query(value = "SELECT * FROM issued_books where date_of_issue<=:date" , nativeQuery = true)
	List<IssuedBooks> getAllFinedStudent(@Param("date") String date);
    
	@Query(value = "SELECT * FROM issued_books where  student_id=:studentid", nativeQuery = true)
    List<IssuedBooks> getAllBooksForStudent ( @Param("studentid") Integer studentId );
	
}
