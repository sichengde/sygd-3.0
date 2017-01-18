/**
 * Created by Administrator on 2016-08-08.
 */
App.factory('readIdCardService',['$http',function ($http) {
    var guestInfo;
    var raceList=[];
    raceList[1] = '汉';
    raceList[2] = '蒙古';
    raceList[3] = '回';
    raceList[4] = '藏';
    raceList[5] = '维吾尔';
    raceList[6] = '苗';
    raceList[7] = '彝';
    raceList[8] = '壮';
    raceList[9] = '布依';
    raceList[10] = '朝鲜';
    raceList[11] = '满';
    raceList[12] = '侗';
    raceList[13] = '瑶';
    raceList[14] = '白';
    raceList[15] = '土家';
    raceList[16] = '哈尼';
    raceList[17] = '哈萨克';
    raceList[18] = '傣';
    raceList[19] = '黎';
    raceList[20] = '傈僳';
    raceList[21] = '佤';
    raceList[22] = '畲';
    raceList[23] = '高山';
    raceList[24] = '拉祜';
    raceList[25] = '水';
    raceList[26] = '东乡';
    raceList[27] = '纳西';
    raceList[28] = '景颇';
    raceList[29] = '柯尔克孜';
    raceList[30] = '土';
    raceList[31] = '达斡尔';
    raceList[32] = '仫佬';
    raceList[33] = '羌';
    raceList[34] = '布朗';
    raceList[35] = '撒拉';
    raceList[36] = '毛南';
    raceList[37] = '仡佬';
    raceList[38] = '锡伯';
    raceList[39] = '阿昌';
    raceList[40] = '普米';
    raceList[41] = '塔吉克';
    raceList[42] = '怒';
    raceList[43] = '乌孜别克';
    raceList[44] = '俄罗斯';
    raceList[45] = '鄂温克';
    raceList[46] = '德昂';
    raceList[47] = '保安';
    raceList[48] = '裕固';
    raceList[49] = '京';
    raceList[50] = '塔塔尔';
    raceList[51] = '独龙';
    raceList[52] = '鄂伦春';
    raceList[53] = '赫哲';
    raceList[54] = '门巴';
    raceList[55] = '珞巴';
    raceList[56] = '基诺';
    raceList[97] = '其他';
    raceList[98] = '外国血统中国籍人士';
    function readIdCard() {
        var ip = localStorage.getItem('ip');
        if (!ip) {
            messageService.setMessage({type: 'error', content: '没有获取到本机ip，制卡失败，请重新登陆，或者手动在localStorage中添加ip'});
            popUpService.pop('message');
            return $q.reject();
        }
        return $http.get('http://' + ip + ':8081/readIdCard')
            .then(function (msg) {
                guestInfo=null;
                if(msg.data){
                    guestInfo=msg.data;
                    guestInfo.birthdayTime=new Date(guestInfo.year,parseInt(guestInfo.month)-1,guestInfo.day);
                    delete guestInfo.year;
                    delete guestInfo.month;
                    delete guestInfo.day;
                    guestInfo.race=raceList[guestInfo.race];
                    guestInfo.cardType='身份证';
                    guestInfo.country='中国';
                }
                return guestInfo;
            })
    }
    function getGuestInfo() {
        return guestInfo;
    }
    return {
        readIdCard:readIdCard,
        getGuestInfo:getGuestInfo
    }
}]);