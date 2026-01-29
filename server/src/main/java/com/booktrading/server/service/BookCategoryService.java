package com.booktrading.server.service;

import com.booktrading.server.entity.BookCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface BookCategoryService extends IService<BookCategory> {
    List<BookCategory> getCategoryList();
    List<BookCategory> getChildCategories(Long parentId);
    void addCategory(BookCategory category);
    void updateCategory(BookCategory category);
    void deleteCategory(Long categoryId);
    void updateCategoryStatus(Long categoryId, Integer status);
}
