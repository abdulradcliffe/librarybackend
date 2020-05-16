package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Book;
import com.example.demo.entity.IssuedBooks;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.IssuedBooksRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping(value = "/book") // mappping url...
public class BookController {

	@Autowired // injects bookrepository ka object inject krega bkcntlr
	private BookRepository repository;

	@Autowired
	private IssuedBooksRepository issueRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/getAll")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> allBooks = repository.findAll();

		return ResponseEntity.ok(allBooks);
	}

	@GetMapping(value = "/add")
	public ResponseEntity<String> addBook(@RequestParam("title") String title,
			@RequestParam("author") String authorName, @RequestParam("pages") Integer pageSize,
			@RequestParam("category") String category, @RequestParam("quantity") Integer quantity) {
		Book newBook = new Book();
		newBook.setAuthorName(authorName);
		newBook.setCategory(category);
		newBook.setPageSize(pageSize);
		newBook.setTitle(title);
		newBook.setQuantity(quantity);
		repository.save(newBook);
		return ResponseEntity.ok("Added Successfully");
	}
	// @GetMapping(value="/issue/dtl")
	// public ResponseEntity<String>issuebook (@RequestParam("student_id")String
	// Student_id,
	// @RequestParam("book_id")String book_id,@RequestParam("date_of_issue")String
	// date0fissue){
	// }

	@GetMapping(value = "/delete")
	public ResponseEntity<String> deleteEntry(@RequestParam("id") Integer id) {
		Optional<Book> optbook1 = repository.findById(id);
		if (optbook1.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok("deleted successfully");// ok>status code
		}
		return ResponseEntity.ok("id not available");
	}

	@GetMapping(value = "/update")
	public ResponseEntity<String> updateBook(@RequestParam("author") String authorName,
			@RequestParam("title") String title, @RequestParam("category") String category,
			@RequestParam("pages") Integer pageSize, @RequestParam("quantity") Integer quantity,
			@RequestParam("id") Integer id) {
		Optional<Book> optbook = repository.findById(id);
		if (optbook.isPresent()) {
			Book b = optbook.get();// if optbook present,using get retrieve,b>object in db,one row=one obj

			// String author = null;
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

	@GetMapping(value = "/search")
	public ResponseEntity<List<Book>> searchYourBook(@RequestParam("string") String searchBook) {

		if (searchBook == null || searchBook.equals("")) {
			ArrayList<Book> list = new ArrayList<Book>();
			return ResponseEntity.ok(list);
		}
		List<Book> listBook = repository.searchYourBook(searchBook);

		return ResponseEntity.ok(listBook);

	}

	@Transactional
	@GetMapping(value = "/issued")
	public ResponseEntity<String> issuedBooks(@RequestParam("studentid") Integer studentId,
			@RequestParam("bookid") Integer bookId) {

		Book book = repository.getBookById(bookId);

		if (book == null) {
			return ResponseEntity.ok("book does not exist");
		}

		User user = userRepository.getUserById(studentId);

		if (user == null) {
			return ResponseEntity.ok("student does not exist");
		}

		// now, reduce the quantity of book in book_dtl table
		Integer bookQuantity = book.getQuantity();
		if (bookQuantity == null || bookQuantity <= 0) {
			return ResponseEntity.ok("book is out of stock");

		}
		IssuedBooks alreadyIssued = issueRepository.isAlreadyIssued(studentId, bookId);

		if (alreadyIssued != null) {
			return ResponseEntity.ok("This Book is already issued by student");
		}
		Integer bookCounts = issueRepository.countOfBookByStudent(studentId);

		if (bookCounts >= 3) {
			return ResponseEntity.ok("you can not issue more books");
		}

		Integer updatedQuantity = bookQuantity - 1;

		repository.updateBookQuantity(updatedQuantity, bookId);

		// finally issue the book to user
		IssuedBooks issuedBooks = new IssuedBooks();
		issuedBooks.setStudentId(studentId);
		issuedBooks.setBookId(bookId);
		Date date = new Date();
		issuedBooks.setDateOfIssue(date);
		issueRepository.save(issuedBooks);

		return ResponseEntity.ok("Issued Successfully");

	}

	@GetMapping(value = "/return")
	@Transactional
	public ResponseEntity<String> returnBook(@RequestParam("studentid") Integer studentId,
			@RequestParam("bookid") Integer bookId ,@RequestParam("isfinepaid") boolean isFinePaid) {
		IssuedBooks alreadyIssued = issueRepository.isAlreadyIssued(studentId, bookId);
		if (alreadyIssued == null) {
			return ResponseEntity.ok("Book is not issued");
		}
		if(isFinePaid == false)
		{
			Date issuedDate = alreadyIssued.getDateOfIssue();
			Date currentDate = new Date(); 

			long diffOfBothDates = currentDate.getTime() - issuedDate.getTime();
			long diffInDays = TimeUnit.DAYS.convert(diffOfBothDates, TimeUnit.MILLISECONDS);
			
			if (diffInDays > 5)
			{
				 long fineAbleDays =  diffInDays - 5;
				 long totalFine = fineAbleDays* 10;
				 return  ResponseEntity.ok("you have to pay Rs " + totalFine + " Fine");
				 
			}
			
		}
		

		issueRepository.returnBook(studentId, bookId);
		Book book = repository.getBookById(bookId);

		Integer bookQuantity = book.getQuantity();
		bookQuantity++;

		repository.updateBookQuantity(bookQuantity, bookId);
		return ResponseEntity.ok("Book returned Succesfully");

	}

}
