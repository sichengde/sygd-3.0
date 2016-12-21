App.controller('rightClickStateController', ['$scope', 'popUpService','dataService','messageService', function ($scope, popUpService,dataService,messageService) {
    $scope.saunaRing = popUpService.getParam();
    $scope.saunaIn=function () {
        popUpService.close('rightClickState');
        var post = {};
        post.saunaInRowList = [];
    };
    $scope.saunaOut=function () {
        popUpService.close('rightClickState');
        var post = {};
        post.ringList = [];
        var ringNumber = $scope.saunaRing.number;
        post.ringList.push(ringNumber);
        if ($scope.saunaRing.saunaIn.saunaGroupSerial) {
            messageService.setMessage({content: '是否结算同组手牌？'});
            messageService.actionChoose()
                .then(function () {
                    post.groupSerial=$scope.saunaRing.saunaIn.saunaGroupSerial;
                    post.ringList=[];
                    var saunaRingList=dataService.getSaunaRingList();
                    for (var i = 0; i < saunaRingList.length; i++) {
                        var obj = saunaRingList[i];
                        if (obj.saunaIn) {
                            if (obj.saunaIn.saunaGroupSerial == $scope.saunaRing.saunaIn.saunaGroupSerial) {
                                post.ringList.push(obj.number);
                            }
                        }
                    }
                    popUpService.pop('saunaOut',null,$scope.refresh,post);
                },function () {
                    popUpService.pop('saunaOut',null,$scope.refresh,post);
                })
        }else {
            popUpService.pop('saunaOut',null,$scope.refresh,post);
        }
    }

}]);