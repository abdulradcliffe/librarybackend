package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer>// id type  clmn> {//table, 
{
	@Query(value = "SELECT * FROM book_dtl where title like %:string% ", nativeQuery = true)
	 List<Book> searchYourBook(@Param("string") String searchBook);
}
