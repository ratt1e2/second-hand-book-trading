
import request from './request';

export const getBookList = (params) => {
    return request({
        url: '/book/audit/list',
        method: 'get',
        params,
    });
};

export const updateBookStatus = (params) => {
    return request({
        url: '/book/status',
        method: 'put',
        params,
    });
};

export const getBookDetail = (params) => {
    return request({
        url: '/book/detail',
        method: 'get',
        params,
    });
};

