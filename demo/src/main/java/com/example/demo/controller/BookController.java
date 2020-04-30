package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

@RestController
@RequestMapping(value = "/book")//mappping url...
public class BookController {

	@Autowired//injects bookrepository ka object inject krega bkcntlr
	private BookRepository repository;

	@GetMapping(value = "/getAll")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> allbooks = repository.findAll();

		return ResponseEntity.ok(allbooks);
	}

	@GetMapping(value = "/add")
	public ResponseEntity<String> addBook(@RequestParam("title") String title,
			@RequestParam("author") String authorName, @RequestParam("pages") Integer pageSize,
			@RequestParam("category") String category,@RequestParam("quantity") Integer quantity) {
		Book newBook = new Book();
		newBook.setAuthorName(authorName);
		newBook.setCategory(category);
		newBook.setPageSize(pageSize);
		newBook.setTitle(title);
		newBook.setQuantity(quantity);
		repository.save(newBook);
		return ResponseEntity.ok("Added Successfully"); 
	}
	//	@GetMapping(value="/issue/dtl")
	//	public ResponseEntity<String>issuebook (@RequestParam("student_id")String Student_id,
	//	@RequestParam("book_id")String book_id,@RequestParam("date_of_issue")String date0fissue){
		//}
	
	@GetMapping(value="/delete") 
	public ResponseEntity<String> deleteentry (@RequestParam("id")Integer id){
		Optional<Book> optbook1=repository.findById(id);
		if(optbook1.isPresent())
		{
			repository.deleteById(id);
			return ResponseEntity.ok("deleted successfully");//ok>status code
		}
		return ResponseEntity.ok("id not available");
	}
	

	@GetMapping(value = "/update")
	public ResponseEntity<String> updateBook(@RequestParam("author") String authorName,
			@RequestParam("title") String title,@RequestParam("category")String category,
			@RequestParam("pages")Integer pageSize,@RequestParam ("quantity") Integer quantity, 
			@RequestParam("id") Integer id) {
		Optional<Book> optbook = repository.findById(id);
		if (optbook.isPresent()) {
			Book b = optbook.get();// if optbook present,using get retrieve,b>object in db,one row=one obj

			//String author = null;
			b.setAuthorName(authorName);// pass the variable
			b.setTitle(title);
			b.setPageSize(pageSize);
			b.setCategory(category);
			b.setQuantity(quantity);
			repository.save(b);
			return ResponseEntity.ok("updated Successfully");

		}
		return ResponseEntity.ok("id not available");

	}

}
