package com.rtk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rtk.Services.BookServiceInterface;
import com.rtk.dtos.BookRequestDTO;
import com.rtk.entities.BookEntity;

@Controller                            // ① MVC controller, not REST
@RequestMapping("/ui/book")           // ② All URLs start with /ui/book
public class BookMvcController {

    private final BookServiceInterface bookService;

    public BookMvcController(BookServiceInterface bookService) {
        this.bookService = bookService;
    }

    /** 
     * 1) Show list of all active books 
     * URL: GET /ui/book/view 
     */
    @GetMapping("/view")
    public String viewAllBooks(Model model) {
        // Fetch entities, pass them directly or map to DTO if you prefer
        model.addAttribute("books", bookService.getBooks());
        return "book-list";            // resolves to templates/book-list.html
    }

    /** 
     * 2) Show empty form to create a new book 
     * URL: GET /ui/book/create 
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("bookRequestDTO", new BookRequestDTO());
        return "book-form";            // resolves to templates/book-form.html
    }

    /** 
     * 3) Handle form submission for both create & update 
     *    We’ll distinguish by presence of bookId in the hidden field 
     * URL: POST /ui/book/save 
     */
    @PostMapping("/save")
    public String saveOrUpdate(
            @RequestParam(value = "bookId", required = false) Integer bookId,
            @ModelAttribute BookRequestDTO dto
    ) {
        BookEntity entity = new BookEntity();
        entity.setBookname(dto.getBookname());
        entity.setAuthor(dto.getAuthor());
        entity.setPrice(dto.getPrice());

        if (bookId == null) {
            // CREATE
            bookService.createBook(entity);
        } else {
            // UPDATE
            bookService.updateBook(bookId, entity);
        }
        return "redirect:/ui/book/view";
    }

    /** 
     * 4) Show form pre-populated for editing an existing book 
     * URL: GET /ui/book/edit/{id} 
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        BookEntity existing = bookService.getBookById(id);

        // map Entity → RequestDTO to populate the form
        BookRequestDTO dto = new BookRequestDTO();
        dto.setBookname(existing.getBookname());
        dto.setAuthor(existing.getAuthor());
        dto.setPrice(existing.getPrice());

        model.addAttribute("bookRequestDTO", dto);
        model.addAttribute("bookId", id);   // used in hidden form field
        return "book-form";
    }

    /** 
     * 5) Soft-delete an existing book and redirect back to list 
     * URL: GET /ui/book/delete/{id} 
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.softDelete(id);
        return "redirect:/ui/book/view";
    }
}
