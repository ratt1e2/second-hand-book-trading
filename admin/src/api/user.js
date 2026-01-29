
import request from './request';

export const getUserList = (params) => {
    return request({
        url: '/user/list',
        method: 'get',
        params,
    });
};

export const updateUserStatus = (data) => {
    return request({
        url: '/user/status',
        method: 'put',
        data,
    });
};

