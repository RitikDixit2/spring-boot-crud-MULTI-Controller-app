package com.rtk.Services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.rtk.bookrepo.BookRepo;
import com.rtk.entities.BookEntity;

@Service                                         // 1. Register as a Spring bean
public class BookServiceImpl implements BookServiceInterface {

    private final BookRepo bookRepo;             // 2. Constructor injection

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookEntity createBook(BookEntity bookEntity) {
        // 4. Set default activation flag before saving
        bookEntity.setActiveSw("T");
        return bookRepo.save(bookEntity);        // Local return, no need for a field
    }

    @Override
    public List<BookEntity> getBooks() {
        // 6. Only return active books (assumes you’ve added findByActiveSw in BookRepo)
        return bookRepo.findByActiveSw("T");
    }

    @Override
    public BookEntity getBookById(Integer id) {
        // 5 & 7. Safe optional handling + correct equals()
        BookEntity book = bookRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        if ("T".equals(book.getActiveSw())) {
            return book;
        }
        throw new RuntimeException("Book is deleted");
    }

    @Override
    public Boolean softDelete(Integer id) {
        BookEntity book = bookRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setActiveSw("F");
        BookEntity updated = bookRepo.save(book);
        return updated != null;
    }

	@Override
	public BookEntity  updateBook(Integer id, BookEntity updates) {
		BookEntity existing = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
		// 2. चेक करो कि बुक अभी active है या नहीं
		if (!"T".equals(existing.getActiveSw())) {
	        throw new RuntimeException("Cannot update a deleted book");
	    }
		
		// 3. अपडेट करने योग्य फील्ड्स को नए वैल्यू से रिप्लेस करो
	    //    (यहां आप जितने फील्ड्स allow करना चाहें उतने सेट कर लें)
		existing.setBookname(updates.getBookname());       
	    existing.setAuthor(updates.getAuthor());
	    existing.setPrice(updates.getPrice());
		
	 // 4. फिर से save करो ताकि DB में ये बदलाव persist हो जाएँ
	    BookEntity saved = bookRepo.save(existing);

	    // 5. सेव्ड ऑब्जेक्ट रिटर्न करो (इसमें ID और सभी फील्ड्स अपडेटेड मिलेंगी)
	    return saved;
	}
}
