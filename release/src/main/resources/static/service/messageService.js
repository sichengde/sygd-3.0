/**
 * Created by Administrator on 2016/7/2 0002.
 */
App.factory('messageService', ['$interval', 'popUpService', '$q', function ($interval, popUpService, $q) {
    var message = {};
    var autoClose;
    var deferred;
    var messageScope;

    function actionSuccess() {
        setMessage({type: 'success'});
        popUpService.pop('message');
        message.content = '操作成功';
        autoClose = $interval(function () {
            $interval.cancel(autoClose);
            popUpService.close('message');
        }, 1600);

    }

    function actionChoose() {
        angular.extend(message, {type: 'choose'});
        popUpService.pop('message');
        deferred = $q.defer();
        return deferred.promise;
    }

    function ok() {
        return deferred.resolve();
    }

    function cancel() {
        return deferred.reject();
    }

    function getMessage() {
        return message;
    }

    /*如果有fun则先执行fun再执行回调函数*/
    function setMessage(msg) {
        message = msg;
        /*如果已经显示了，就更新一下*/
        if (messageScope) {
            updateMessage(msg);
        }
    }

    function initMessageScope(scope) {
        messageScope = scope;
    }

    function updateMessage(msg) {
        messageScope.message = msg;
        messageScope.now = new Date();
    }

    return {
        actionSuccess: actionSuccess,
        actionChoose: actionChoose,
        ok: ok,
        cancel: cancel,
        getMessage: getMessage,
        setMessage: setMessage,
        initMessageScope: initMessageScope,
        updateMessage: updateMessage
    }
}]);