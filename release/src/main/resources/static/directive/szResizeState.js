App.directive('szResizeState',function () {
    return {
        restrict: 'A',
        link: function (scope, ele, attr, ctr) {
            ele.on('click', function () {
                var href="../css/mainState/" + attr.szResizeState + ".css";
                document.getElementsByTagName("link")["pagestyle"].href = href;
                localStorage.setItem('mainStateCss',href);
            });
        }
    }
});