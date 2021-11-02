import request from '../utils/request';

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};
export const bbData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/getUser',
        method: 'get',
        params: query
    });
};
export const abData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/getSeller',
        method: 'get',
        params: query
    });
};
export const cbData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/getRoute',
        method: 'get',
        params: query
    });
};

export const dbData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/getImg',
        method: 'post',
        params: query
    });
};

export const ebData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/setRoute',
        method: 'post',
        params: query
    });
};

export const fbData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/setUser',
        method: 'post',
        params: query
    });
};

export const gbData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/setSeller',
        method: 'post',
        params: query
    });
};

export const hbData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/newSeller',
        method: 'post',
        params: query
    });
};

export const ibData = query => {
    return request({
        url: 'http://localhost:80/travel/VueServlet/getMessage',
        method: 'get',
        params: query
    });
};