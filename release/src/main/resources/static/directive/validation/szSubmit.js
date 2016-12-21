/**
 * Created by Administrator on 2016/7/2 0002.
 */
App.directive('szSubmit', ['validateService',function (validateService) {
    return {
        link: function (scope, element) {
            var checkList=[];
            validateService.init(wrongCheck);
            function wrongCheck(list) {
                for (var i = 0; i < list.length; i++) {
                    if (list[i]) {
                        element.attr({disabled: true});
                        element.addClass("no");
                        return
                    }
                }
                element.removeAttr('disabled');
                element.removeClass("no");
            }
            wrongCheck(checkList);
        }
    }
}]);