package com.booktrading.server.service.impl;

import com.booktrading.server.entity.Book;
import com.booktrading.server.mapper.BookMapper;
import com.booktrading.server.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Override
    public IPage<Book> getBookList(IPage<Book> page, Long categoryId, String keyword, Integer sort) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getStatus, 1); // 只查询已上架的书籍
        
        if (categoryId != null) {
            queryWrapper.eq(Book::getCategoryId, categoryId);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(Book::getTitle, keyword).or().like(Book::getAuthor, keyword).or().like(Book::getIsbn, keyword);
        }
        
        // 排序
        switch (sort) {
            case 1: // 最新上架
                queryWrapper.orderByDesc(Book::getCreatedAt);
                break;
            case 2: // 价格从低到高
                queryWrapper.orderByAsc(Book::getPrice);
                break;
            case 3: // 价格从高到低
                queryWrapper.orderByDesc(Book::getPrice);
                break;
            case 4: // 浏览量
                queryWrapper.orderByDesc(Book::getViewCount);
                break;
            default:
                queryWrapper.orderByDesc(Book::getCreatedAt);
        }
        
        return page(page, queryWrapper);
    }

    @Override
    public List<Book> getHotBooks(Integer limit) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getStatus, 1);
        queryWrapper.orderByDesc(Book::getViewCount);
        queryWrapper.last("limit " + limit);
        return list(queryWrapper);
    }

    @Override
    public List<Book> getNewBooks(Integer limit) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getStatus, 1);
        queryWrapper.orderByDesc(Book::getCreatedAt);
        queryWrapper.last("limit " + limit);
        return list(queryWrapper);
    }

    @Override
    public Book getBookDetail(Long bookId) {
        Book book = getById(bookId);
        if (book != null) {
            // 增加浏览量
            book.setViewCount(book.getViewCount() + 1);
            updateById(book);
        }
        return book;
    }

    @Override
    public void addBook(Book book) {
        book.setStatus(0); // 初始状态为待审核
        book.setViewCount(0);
        book.setCommentCount(0);
        save(book);
    }

    @Override
    public void updateBook(Book book) {
        updateById(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        removeById(bookId);
    }

    @Override
    public void updateBookStatus(Long bookId, Integer status) {
        lambdaUpdate().eq(Book::getId, bookId).set(Book::getStatus, status).update();
    }

    @Override
    public IPage<Book> getBooksByUserId(IPage<Book> page, Long userId) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getUserId, userId);
        queryWrapper.orderByDesc(Book::getCreatedAt);
        return page(page, queryWrapper);
    }

    @Override
    public IPage<Book> getBooksForAudit(IPage<Book> page) {
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getStatus, 0); // 待审核状态
        queryWrapper.orderByAsc(Book::getCreatedAt);
        return page(page, queryWrapper);
    }
}
