/**
 * Created by Administrator on 2016-05-28.
 * 与后端通讯的服务
 */
'use strict';

App.factory('webService', ['$http', '$location', 'host', 'popUpService', '$q', 'messageService',function ($http, $location, host, popUpService, $q, messageService) {
    /*异常处理*/
    function handleException(msg) {
        var message = {};
        messageService.setMessage({type: 'error', content: msg.message + '---' + msg.path});
        popUpService.pop('message');
    }

    return {
        /*get请求*/
        get: function (path) {
            //$http.get(cloud + '/' + path, {headers: {'database': database,'userId':encodeURI(LoginService.getUser())}});//向云端同步
            return $http.get(host + '/' + path)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        handleException(errResponse.data);
                        return $q.reject();
                    }
                );
        },
        /*post请求*/
        post: function (path, object) {
            //$http.post(cloud + '/' + path, object, {headers: {'database': database,'userId':encodeURI(LoginService.getUser())}});
            return $http.post(host + '/' + path, object)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        handleException(errResponse.data);
                        return $q.reject();
                    }
                );
        },
        /*打开一个新页面，多用于打印报表*/
        open: function (path) {
            window.open(path);
        },
        openReport: function (path) {
            window.open(host + '/receipt/' + path);
        },
        /*强制跳转*/
        redirect: function (path) {
            $location.path(path);
        },
        getWrongMsg: function () {
            return wrongMsg;
        }
    };
}]);

