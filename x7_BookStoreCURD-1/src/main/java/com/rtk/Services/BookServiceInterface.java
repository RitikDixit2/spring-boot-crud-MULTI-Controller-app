package com.rtk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import com.rtk.entities.BookEntity;

public interface BookServiceInterface {
	
	public BookEntity createBook(BookEntity bookEntity);
	// Wrtien type BookEnity it self so insted of just ture false can return more detials
	
	// GetAllBooks
	public List<BookEntity> getBooks();
	
	// Get1book
	public BookEntity getBookById(Integer id);
	
	// softDelete 
	public Boolean softDelete(Integer id);
	
	//update book , 
		public BookEntity updateBook(Integer id, BookEntity updatesEntity);
}
