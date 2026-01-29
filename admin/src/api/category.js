
import request from './request';

export const getCategoryList = () => {
    return request({
        url: '/category/list',
        method: 'get',
    });
};

export const addCategory = (data) => {
    return request({
        url: '/category/add',
        method: 'post',
        data,
    });
};

export const updateCategory = (data) => {
    return request({
        url: '/category/update',
        method: 'put',
        data,
    });
};

export const deleteCategory = (params) => {
    return request({
        url: '/category/delete',
        method: 'delete',
        params,
    });
};

export const updateCategoryStatus = (params) => {
    return request({
        url: '/category/status',
        method: 'put',
        params,
    });
};

