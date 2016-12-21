App.filter('saunaMenu',[function () {
    return function (saunaMenuList, category) {
        var out=[];
        if(!saunaMenuList){
            return
        }
        if(!category || category=='全部'){
            return saunaMenuList;
        }
        for (var i = 0; i < saunaMenuList.length; i++) {
            var saunaMenu = saunaMenuList[i];
            if(saunaMenu.category==category){
                out.push(saunaMenu);
            }
        }
        return out;
    }
}]);