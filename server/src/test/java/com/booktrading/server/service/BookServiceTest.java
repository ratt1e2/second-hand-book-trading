package com.booktrading.server.service;

import com.booktrading.server.entity.Book;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testAddBook() {
        // 测试添加书籍
        Book book = new Book();
        book.setUserId(1L);
        book.setCategoryId(1L);
        book.setTitle("测试书籍");
        book.setAuthor("测试作者");
        book.setIsbn("9787111612908");
        book.setPrice(new BigDecimal(59.9));
        book.setOriginalPrice(new BigDecimal(89.9));
        book.setCondition(1);
        book.setDescription("这是一本测试书籍");
        book.setCoverImage("");

        bookService.addBook(book);
        assertNotNull(book.getId());
        assertEquals("测试书籍", book.getTitle());
    }

    @Test
    public void testGetBookList() {
        // 测试获取书籍列表
        IPage<Book> page = new Page<>(1, 10);
        IPage<Book> bookPage = bookService.getBookList(page, null, null, 1);
        assertNotNull(bookPage);
        assertTrue(bookPage.getTotal() > 0);
    }

    @Test
    public void testGetHotBooks() {
        // 测试获取热门书籍
        List<Book> hotBooks = bookService.getHotBooks(10);
        assertNotNull(hotBooks);
    }

    @Test
    public void testGetNewBooks() {
        // 测试获取最新书籍
        List<Book> newBooks = bookService.getNewBooks(10);
        assertNotNull(newBooks);
    }

    @Test
    public void testGetBookDetail() {
        // 测试获取书籍详情
        // 先添加一本测试书籍
        Book book = new Book();
        book.setUserId(1L);
        book.setCategoryId(1L);
        book.setTitle("测试详情书籍");
        book.setAuthor("测试作者");
        book.setIsbn("9787111612909");
        book.setPrice(new BigDecimal(59.9));
        book.setOriginalPrice(new BigDecimal(89.9));
        book.setCondition(1);
        book.setDescription("这是一本测试详情书籍");
        book.setCoverImage("");

        bookService.addBook(book);
        assertNotNull(book.getId());

        // 获取书籍详情
        Book bookDetail = bookService.getBookDetail(book.getId());
        assertNotNull(bookDetail);
        assertEquals("测试详情书籍", bookDetail.getTitle());
    }

    @Test
    public void testUpdateBookStatus() {
        // 测试更新书籍状态
        // 先添加一本测试书籍
        Book book = new Book();
        book.setUserId(1L);
        book.setCategoryId(1L);
        book.setTitle("测试状态书籍");
        book.setAuthor("测试作者");
        book.setIsbn("9787111612910");
        book.setPrice(new BigDecimal(59.9));
        book.setOriginalPrice(new BigDecimal(89.9));
        book.setCondition(1);
        book.setDescription("这是一本测试状态书籍");
        book.setCoverImage("");

        bookService.addBook(book);
        assertNotNull(book.getId());

        // 更新书籍状态为已上架
        bookService.updateBookStatus(book.getId(), 1);
        Book updatedBook = bookService.getById(book.getId());
        assertEquals(1, updatedBook.getStatus());
    }
}
