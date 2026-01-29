package com.booktrading.server.service.impl;

import com.booktrading.server.entity.BookCategory;
import com.booktrading.server.mapper.BookCategoryMapper;
import com.booktrading.server.service.BookCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {

    @Override
    public List<BookCategory> getCategoryList() {
        LambdaQueryWrapper<BookCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookCategory::getStatus, 1);
        queryWrapper.orderByAsc(BookCategory::getSort);
        return list(queryWrapper);
    }

    @Override
    public List<BookCategory> getChildCategories(Long parentId) {
        LambdaQueryWrapper<BookCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookCategory::getParentId, parentId);
        queryWrapper.eq(BookCategory::getStatus, 1);
        queryWrapper.orderByAsc(BookCategory::getSort);
        return list(queryWrapper);
    }

    @Override
    public void addCategory(BookCategory category) {
        save(category);
    }

    @Override
    public void updateCategory(BookCategory category) {
        updateById(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        // 这里应该先检查是否有子分类，暂时简化处理
        removeById(categoryId);
    }

    @Override
    public void updateCategoryStatus(Long categoryId, Integer status) {
        lambdaUpdate().eq(BookCategory::getId, categoryId).set(BookCategory::getStatus, status).update();
    }
}
