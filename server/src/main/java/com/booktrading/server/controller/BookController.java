package com.booktrading.server.controller;

import com.booktrading.server.common.Result;
import com.booktrading.server.entity.Book;
import com.booktrading.server.service.BookService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public Result<?> getBookList(@RequestParam(defaultValue = "1") Integer page, 
                                 @RequestParam(defaultValue = "10") Integer size, 
                                 @RequestParam(required = false) Long categoryId, 
                                 @RequestParam(required = false) String keyword, 
                                 @RequestParam(defaultValue = "1") Integer sort) {
        IPage<Book> bookPage = new Page<>(page, size);
        bookPage = bookService.getBookList(bookPage, categoryId, keyword, sort);
        return Result.success(bookPage);
    }

    @GetMapping("/hot")
    public Result<?> getHotBooks(@RequestParam(defaultValue = "10") Integer limit) {
        List<Book> hotBooks = bookService.getHotBooks(limit);
        return Result.success(hotBooks);
    }

    @GetMapping("/new")
    public Result<?> getNewBooks(@RequestParam(defaultValue = "10") Integer limit) {
        List<Book> newBooks = bookService.getNewBooks(limit);
        return Result.success(newBooks);
    }

    @GetMapping("/detail")
    public Result<?> getBookDetail(@RequestParam Long bookId) {
        Book book = bookService.getBookDetail(bookId);
        return Result.success(book);
    }

    @PostMapping("/add")
    public Result<?> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result<?> deleteBook(@RequestParam Long bookId) {
        bookService.deleteBook(bookId);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateBookStatus(@RequestParam Long bookId, @RequestParam Integer status) {
        bookService.updateBookStatus(bookId, status);
        return Result.success();
    }

    @GetMapping("/user/list")
    public Result<?> getBooksByUserId(@RequestParam Long userId, 
                                      @RequestParam(defaultValue = "1") Integer page, 
                                      @RequestParam(defaultValue = "10") Integer size) {
        IPage<Book> bookPage = new Page<>(page, size);
        bookPage = bookService.getBooksByUserId(bookPage, userId);
        return Result.success(bookPage);
    }

    @GetMapping("/audit/list")
    public Result<?> getBooksForAudit(@RequestParam(defaultValue = "1") Integer page, 
                                      @RequestParam(defaultValue = "10") Integer size) {
        IPage<Book> bookPage = new Page<>(page, size);
        bookPage = bookService.getBooksForAudit(bookPage);
        return Result.success(bookPage);
    }
}
