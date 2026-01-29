
import request from './request';

export const login = (data) => {
    return request({
        url: '/auth/login',
        method: 'post',
        data,
    });
};

export const register = (data) => {
    return request({
        url: '/auth/register',
        method: 'post',
        data,
    });
};

export const getUserList = (params) => {
    return request({
        url: '/user/list',
        method: 'get',
        params,
    });
};
