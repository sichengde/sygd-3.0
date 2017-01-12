App.directive('szResizeState',function () {
    return {
        restrict: 'A',
        link: function (scope, ele, attr, ctr) {
            ele.on('click', function () {
                document.getElementsByTagName("link")["pagestyle"].href = "../css/mainState/" + attr.szResizeState + ".css";
            });
        }
    }
});