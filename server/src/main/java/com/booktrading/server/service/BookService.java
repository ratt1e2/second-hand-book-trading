package com.booktrading.server.service;

import com.booktrading.server.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

public interface BookService extends IService<Book> {
    IPage<Book> getBookList(IPage<Book> page, Long categoryId, String keyword, Integer sort);
    List<Book> getHotBooks(Integer limit);
    List<Book> getNewBooks(Integer limit);
    Book getBookDetail(Long bookId);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(Long bookId);
    void updateBookStatus(Long bookId, Integer status);
    IPage<Book> getBooksByUserId(IPage<Book> page, Long userId);
    IPage<Book> getBooksForAudit(IPage<Book> page);
}
