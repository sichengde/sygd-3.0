/**
 * Created by Administrator on 2016-08-08.
 */
App.factory('readIdCardService', ['$http', function ($http) {
    var guestInfo;
    var raceList = [];
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
    var countryList = [
        {alias: 'AO', name: "安哥拉"},
        {alias: "AF", name: "阿富汗"},
        {alias: "AL", name: "阿尔巴尼亚"},
        {alias: "DZ", name: "阿尔及利亚"},
        {alias: "AD", name: "安道尔共和国"},
        {alias: "AI", name: "安圭拉岛"},
        {alias: "AG", name: "安提瓜和巴布达"},
        {alias: "AR", name: "阿根廷"},
        {alias: "AM", name: "亚美尼亚"},
        {alias: "AU", name: "澳大利亚"},
        {alias: "AT", name: "奥地利"},
        {alias: "AZ", name: "阿塞拜疆"},
        {alias: "BS", name: "巴哈马"},
        {alias: "BH", name: "巴林"},
        {alias: "BD", name: "孟加拉国"},
        {alias: "BB", name: "巴巴多斯"},
        {alias: "BY", name: "白俄罗斯"},
        {alias: "BE", name: "比利时"},
        {alias: "BZ", name: "伯利兹"},
        {alias: "BJ", name: "贝宁"},
        {alias: "BM", name: "百慕大群岛"},
        {alias: "BO", name: "玻利维亚"},
        {alias: "BW", name: "博茨瓦纳"},
        {alias: "BR", name: "巴西"},
        {alias: "BN", name: "文莱"},
        {alias: "BG", name: "保加利亚"},
        {alias: "BF", name: "布基纳法索"},
        {alias: "MM", name: "缅甸"},
        {alias: "BI", name: "布隆迪"},
        {alias: "CM", name: "喀麦隆"},
        {alias: "CA", name: "加拿大"},
        {alias: "CF", name: "中非共和国"},
        {alias: "TD", name: "乍得"},
        {alias: "CL", name: "智利"},
        {alias: "CN", name: "中国"},
        {alias: "CO", name: "哥伦比亚"},
        {alias: "CG", name: "刚果"},
        {alias: "CK", name: "库克群岛"},
        {alias: "CR", name: "哥斯达黎加"},
        {alias: "CU", name: "古巴"},
        {alias: "CY", name: "塞浦路斯"},
        {alias: "CZ", name: "捷克"},
        {alias: "DK", name: "丹麦"},
        {alias: "DJ", name: "吉布提"},
        {alias: "DO", name: "多米尼加共和国"},
        {alias: "EC", name: "厄瓜多尔"},
        {alias: "EG", name: "埃及"},
        {alias: "SV", name: "萨尔瓦多"},
        {alias: "EE", name: "爱沙尼亚"},
        {alias: "ET", name: "埃塞俄比亚"},
        {alias: "FJ", name: "斐济"},
        {alias: "FI", name: "芬兰"},
        {alias: "FR", name: "法国"},
        {alias: "GF", name: "法属圭亚那"},
        {alias: "GA", name: "加蓬"},
        {alias: "GM", name: "冈比亚"},
        {alias: "GE", name: "格鲁吉亚"},
        {alias: "DE", name: "德国"},
        {alias: "GH", name: "加纳"},
        {alias: "GI", name: "直布罗陀"},
        {alias: "GR", name: "希腊"},
        {alias: "GD", name: "格林纳达"},
        {alias: "GU", name: "关岛"},
        {alias: "GT", name: "危地马拉"},
        {alias: "GN", name: "几内亚"},
        {alias: "GY", name: "圭亚那"},
        {alias: "HT", name: "海地"},
        {alias: "HN", name: "洪都拉斯"},
        {alias: "HK", name: "香港"},
        {alias: "HU", name: "匈牙利"},
        {alias: "IS", name: "冰岛"},
        {alias: "IN", name: "印度"},
        {alias: "ID", name: "印度尼西亚"},
        {alias: "IR", name: "伊朗"},
        {alias: "IQ", name: "伊拉克"},
        {alias: "IE", name: "爱尔兰"},
        {alias: "IL", name: "以色列"},
        {alias: "IT", name: "意大利"},
        {alias: "JM", name: "牙买加"},
        {alias: "JP", name: "日本"},
        {alias: "JO", name: "约旦"},
        {alias: "KH", name: "柬埔寨"},
        {alias: "KZ", name: "哈萨克斯坦"},
        {alias: "KE", name: "肯尼亚"},
        {alias: "KR", name: "韩国"},
        {alias: "KW", name: "科威特"},
        {alias: "KG", name: "吉尔吉斯坦"},
        {alias: "LA", name: "老挝"},
        {alias: "LV", name: "拉脱维亚"},
        {alias: "LB", name: "黎巴嫩"},
        {alias: "LS", name: "莱索托"},
        {alias: "LR", name: "利比里亚"},
        {alias: "LY", name: "利比亚"},
        {alias: "LI", name: "列支敦士登"},
        {alias: "LT", name: "立陶宛"},
        {alias: "LU", name: "卢森堡"},
        {alias: "MO", name: "澳门"},
        {alias: "MG", name: "马达加斯加"},
        {alias: "MW", name: "马拉维"},
        {alias: "MY", name: "马来西亚"},
        {alias: "MV", name: "马尔代夫"},
        {alias: "ML", name: "马里"},
        {alias: "MT", name: "马耳他"},
        {alias: "MU", name: "毛里求斯"},
        {alias: "MX", name: "墨西哥"},
        {alias: "MD", name: "摩尔多瓦"},
        {alias: "MC", name: "摩纳哥"},
        {alias: "MN", name: "蒙古"},
        {alias: "MS", name: "蒙特塞拉特岛"},
        {alias: "MA", name: "摩洛哥"},
        {alias: "MZ", name: "莫桑比克"},
        {alias: "NA", name: "纳米比亚"},
        {alias: "NR", name: "瑙鲁"},
        {alias: "NP", name: "尼泊尔"},
        {alias: "NL", name: "荷兰"},
        {alias: "NZ", name: "新西兰"},
        {alias: "NI", name: "尼加拉瓜"},
        {alias: "NE", name: "尼日尔"},
        {alias: "NG", name: "尼日利亚"},
        {alias: "KP", name: "朝鲜"},
        {alias: "NO", name: "挪威"},
        {alias: "OM", name: "阿曼"},
        {alias: "PK", name: "巴基斯坦"},
        {alias: "PA", name: "巴拿马"},
        {alias: "PG", name: "巴布亚新几内亚"},
        {alias: "PY", name: "巴拉圭"},
        {alias: "PE", name: "秘鲁"},
        {alias: "PH", name: "菲律宾"},
        {alias: "PL", name: "波兰"},
        {alias: "PF", name: "法属玻利尼西亚"},
        {alias: "PT", name: "葡萄牙"},
        {alias: "PR", name: "波多黎各"},
        {alias: "QA", name: "卡塔尔"},
        {alias: "RO", name: "罗马尼亚"},
        {alias: "RU", name: "俄罗斯"},
        {alias: "LC", name: "圣卢西亚"},
        {alias: "VC", name: "圣文森特岛"},
        {alias: "SM", name: "圣马力诺"},
        {alias: "ST", name: "圣多美和普林西比"},
        {alias: "SA", name: "沙特阿拉伯"},
        {alias: "SN", name: "塞内加尔"},
        {alias: "SC", name: "塞舌尔"},
        {alias: "SL", name: "塞拉利昂"},
        {alias: "SG", name: "新加坡"},
        {alias: "SK", name: "斯洛伐克"},
        {alias: "SI", name: "斯洛文尼亚"},
        {alias: "SB", name: "所罗门群岛"},
        {alias: "SO", name: "索马里"},
        {alias: "ZA", name: "南非"},
        {alias: "ES", name: "西班牙"},
        {alias: "LK", name: "斯里兰卡"},
        {alias: "SD", name: "苏丹"},
        {alias: "SR", name: "苏里南"},
        {alias: "SZ", name: "斯威士兰"},
        {alias: "SE", name: "瑞典"},
        {alias: "CH", name: "瑞士"},
        {alias: "SY", name: "叙利亚"},
        {alias: "TW", name: "台湾省"},
        {alias: "TJ", name: "塔吉克斯坦"},
        {alias: "TZ", name: "坦桑尼亚"},
        {alias: "TH", name: "泰国"},
        {alias: "TG", name: "多哥"},
        {alias: "TO", name: "汤加"},
        {alias: "TT", name: "特立尼达和多巴哥"},
        {alias: "TN", name: "突尼斯"},
        {alias: "TR", name: "土耳其"},
        {alias: "TM", name: "土库曼斯坦"},
        {alias: "UG", name: "乌干达"},
        {alias: "UA", name: "乌克兰"},
        {alias: "AE", name: "阿拉伯联合酋长国"},
        {alias: "GB", name: "英国"},
        {alias: "US", name: "美国"},
        {alias: "UY", name: "乌拉圭"},
        {alias: "UZ", name: "乌兹别克斯坦"},
        {alias: "VE", name: "委内瑞拉"},
        {alias: "VN", name: "越南"},
        {alias: "YE", name: "也门"},
        {alias: "YU", name: "南斯拉夫"},
        {alias: "ZW", name: "津巴布韦"},
        {alias: "ZR", name: "扎伊尔"},
        {alias: "ZM", name: "赞比亚"}
    ];

    function readIdCard() {
        var ip = localStorage.getItem('ip');
        if (!ip) {
            messageService.setMessage({type: 'error', content: '没有获取到本机ip，制卡失败，请重新登陆，或者手动在localStorage中添加ip'});
            popUpService.pop('message');
            return $q.reject();
        }
        return $http.get('http://' + ip + ':8081/readIdCard')
            .then(function (msg) {
                guestInfo = null;
                if (msg.data) {
                    guestInfo = msg.data;
                    guestInfo.birthdayTime = new Date(guestInfo.year, parseInt(guestInfo.month) - 1, guestInfo.day);
                    delete guestInfo.year;
                    delete guestInfo.month;
                    delete guestInfo.day;
                    guestInfo.race = raceList[guestInfo.race];
                    guestInfo.cardType = '身份证';
                    guestInfo.country = '中国';
                }
                return guestInfo;
            })
    }

    function getGuestInfo() {
        return guestInfo;
    }

    function getCountryList() {
        return countryList;
    }

    return {
        readIdCard: readIdCard,
        getGuestInfo: getGuestInfo,
        getCountryList:getCountryList,
    }
}]);