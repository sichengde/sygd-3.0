/**
 * Created by Administrator on 2016/8/12 0012.
 */
App.factory('bookService', [function () {
    function getOneRoomBookList(bookList) {
        if(!bookList){
            return;
        }
        var out=[];
        for (var i = 0; i < bookList.length; i++) {
            var obj = bookList[i];
            if(obj.totalRoom==1){
                out.push(obj);
            }
        }
        return out;
    }
    return{
        getOneRoomBookList:getOneRoomBookList
    }
}]);