
import request from './request';

export const getOrderList = (params) => {
    return request({
        url: '/order/admin/list',
        method: 'get',
        params,
    });
};

export const getOrderDetail = (params) => {
    return request({
        url: '/order/detail',
        method: 'get',
        params,
    });
};

