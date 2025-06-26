package com.rtk.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtk.Services.BookServiceInterface;
import com.rtk.dtos.BookRequestDTO;
import com.rtk.dtos.BookResponseDto;
import com.rtk.entities.BookEntity;

@RestController
@RequestMapping("/book")
public class BookController {
	public final BookServiceInterface bookService;
	
	public BookController(BookServiceInterface bookservice) {
		this.bookService=bookservice;
	}
	
// createbook 
	@PostMapping("/create")
	public  ResponseEntity<BookResponseDto> createBook(@RequestBody BookRequestDTO requestDTO){
		// Now map requestDto to the real entity
		BookEntity tosavetoBookEntity = new BookEntity();
			
		// map data 
			tosavetoBookEntity.setBookname(requestDTO.getBookname());
			tosavetoBookEntity.setAuthor(requestDTO.getAuthor());
			tosavetoBookEntity.setPrice(requestDTO.getPrice());
			
		// बाकी  activeStatus ('T') और ID सर्विस लेयर में सेट होते हैं
			
			// Now data is set from The dto to our entity object now save this Entity object 
			BookEntity saved = bookService.createBook(tosavetoBookEntity);
			
			// For Response only few data 
			//   Entity → DTO मैपिंग: : DB में सेव्ड ऑब्जेक्ट से Response DTO बनाएं
			
			BookResponseDto resp = new BookResponseDto(
					saved.getBookname(), saved.getAuthor(), saved.getPrice());
			
		return ResponseEntity
                .status(201)              // HttpStatus.CREATED
                .body(resp);             // return created entity
    }	
	
	//getallBooks
	@GetMapping("/getAll")
	public ResponseEntity<List<BookResponseDto>> getAllBooks(){
		List<BookEntity> allBooksEntities = bookService.getBooks();
		
		// 13. Entity लिस्ट को DTO लिस्ट में कन्वर्ट करें
		List<BookResponseDto> list = allBooksEntities.stream()
		.map(e-> new BookResponseDto(e.getBookname(), e.getAuthor(), e.getPrice())).
		collect(Collectors.toList());
		
		return ResponseEntity.ok(list);
	}
	
	//getBookById
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<BookResponseDto> getBookById(@PathVariable Integer id){
		BookEntity b = bookService.getBookById(id);
		// Mapping entity to respone objecj
		BookResponseDto bookResponseDto = new BookResponseDto(b.getBookname(), b.getAuthor(), b.getPrice());
		return ResponseEntity.ok(bookResponseDto);
	}
	
	// Update book by id
	@PostMapping("/update/{id}")
	public ResponseEntity<BookResponseDto> updateBookById(
			@PathVariable Integer id,
			@RequestBody BookRequestDTO req
			) {
			// dto aayi hai  entity mei convert karo db mei save karne kel liye 
		 BookEntity updates = new BookEntity();
		 updates.setBookname(req.getBookname());
		 updates.setAuthor(req.getAuthor());
		 updates.setPrice(req.getPrice());
		 
		 BookEntity updateBook = bookService.updateBook(id, updates);
		 
		 //To give respone map entity to respone obj
		 BookResponseDto res = new BookResponseDto(updateBook.getBookname(), updateBook.getAuthor(), updateBook.getPrice());

			return ResponseEntity.ok(res);
	}
	
	
	//Delete book
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deletBookById(@PathVariable Integer id){
			
			Boolean deleted = bookService.softDelete(id);
			
			// 30. अगर डिलीट हुआ तो 204 No Content, वरना 404 Not Found
	        return deleted
	            ? ResponseEntity.noContent().build()
	            : ResponseEntity.notFound().build();
	    }
			
			
	}
	

