package com.booktrading.server.controller;

import com.booktrading.server.common.Result;
import com.booktrading.server.entity.BookCategory;
import com.booktrading.server.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;

    @GetMapping("/list")
    public Result<?> getCategoryList() {
        List<BookCategory> categories = bookCategoryService.getCategoryList();
        return Result.success(categories);
    }

    @GetMapping("/child")
    public Result<?> getChildCategories(@RequestParam Long parentId) {
        List<BookCategory> childCategories = bookCategoryService.getChildCategories(parentId);
        return Result.success(childCategories);
    }

    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody BookCategory category) {
        bookCategoryService.addCategory(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> updateCategory(@RequestBody BookCategory category) {
        bookCategoryService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result<?> deleteCategory(@RequestParam Long categoryId) {
        bookCategoryService.deleteCategory(categoryId);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateCategoryStatus(@RequestParam Long categoryId, @RequestParam Integer status) {
        bookCategoryService.updateCategoryStatus(categoryId, status);
        return Result.success();
    }
}
