/**
 * 工具包
 * 索引：
 * getValueByField根据字段名查找数组中的某个对象
 * getValueListByField根据字段名查找数组中的某些对象
 * deleteFromArray根据字段名删除数组中的某个对象，如果字段名为null则直接比较
 * getDeletedArray通过原数组和剩余数组查找出被删除了的数组
 * objectListToString把对象数组的某一列返回成字符串(自动去重)
 * objectListToStringDuplicate把对象数组的某一列返回成字符串(不去重)
 * parseSearch分析查询对象
 * wrapWithBrackets为字符串包裹上单括号
 * addAttributeOnly为数组某一个条件相等的对象新增一个对象，通过field相等来判断
 * addAttributes为数组某一些条件相等的对象新增一个对象，通过field相等来判断
 * addArray为数组新增一个对象数组，通过field相等来判断
 * dateEqualsDay:判断两个date对象对日期是否相等
 * getHourMinSec:根据时间戳获得小时，分钟，秒
 * newDateNow根据参数创建一个时间对象
 * newDateAndTime为日期对象设定时分秒
 * isNullSetZero:判断是否为空，空则返回0
 * isNullSetString:判断是否为空，空则返回''
 * timestampByTimeString:通过时分秒字符串获得时间戳
 * getTodayMin:把当前的时间设为0点，主要用于初始化报表时间范围
 * getTodayMax:把当前的时间设为24点，主要用于初始化报表时间范围
 */
App.factory('util', [function () {
    /**
     * 根据字段名查找数组中的某个对象
     */
    function getValueByField(array, field, value) {
        if (!array) {
            return false;
        }
        var l = array.length;
        for (var i = 0; i < l; i++) {
            if (array[i][field] == (value)) {
                return array[i];
            }
        }
        return false;
    }

    /**
     * 根据字段名查找数组中的某些对象
     */
    function getValueListByField(array, field, value) {
        if (!array) {
            return;
        }
        var l = array.length;
        var out = [];
        for (var i = 0; i < l; i++) {
            if (array[i][field] == (value)) {
                out.push(array[i]);
            }
        }
        return out;
    }

    /**
     * 根据字段名删除数组中的某个对象
     */
    function deleteFromArray(array, field, value) {
        var l = array.length;
        if (field != null) {
            for (var i = 0; i < l; i++) {
                if (array[i][field] == value) {
                    break;
                }
            }
        } else {
            for (var i = 0; i < l; i++) {
                if (array[i] == value) {
                    break;
                }
            }
        }
        if (i < l) {
            array.splice(i, 1);
            return true
        }
        return false;
    }

    /**
     * 通过原数组和剩余数组查找出被删除了的数组
     */
    function getDeletedArray(srcList, remainList, field) {
        var out = [];
        for (var i = 0; i < srcList.length; i++) {
            var obj = srcList[i];
            if (!getValueByField(remainList, field, obj[field])) {
                out.push(obj);
            }
        }
        return out;
    }

    /**
     * 根据参数创建一个时间对象
     */
    var newDateNow = function (d) {
        var time = new Date(d);
        time.setMilliseconds(0);
        return time;
    };

    /**
     * 为日期对象设定时分秒
     */
    function newDateAndTime(date, time) {
        var out=angular.copy(date);
        out.setHours(time.substring(0, 2));
        out.setMinutes(time.substring(3, 5));
        out.setSeconds(time.substring(6, 8));
        return out;
    }

    /**
     * 把对象数组的某一列返回成字符串(自动去重)
     */
    function objectListToString(objList, field1) {
        if (!objList) {
            return false;
        }
        var r = [];
        for (var i = 0; i < objList.length; i++) {
            var obj = objList[i];
            if (r.indexOf(obj[field1]) == -1) {
                r.push(obj[field1]);
            }
        }
        return r;
    }

    /**
     * 把对象数组的某一列返回成字符串(不去重)
     */
    function objectListToStringDuplicate(objList, field1, filter, format) {
        var r = [];
        for (var i = 0; i < objList.length; i++) {
            var obj = objList[i];
            var var1;
            if (filter) {
                var1 = filter(obj[field1], format);
            } else {
                var1 = obj[field1];
            }
            r.push(var1);
        }
        return r;
    }

    /**
     * 分析查询对象
     */
    function parseSearch(searchCondition, fields) {
        if (!searchCondition) {
            return null;
        }
        /*中文字符串替换为对应的id*/
        var reg = /[\u4E00-\u9FA5]+/g;
        var id = searchCondition.replace(reg, function (word, pos) {
            if (pos == 0 || searchCondition[pos - 1] == ' ') {
                return getValueByField(fields, 'name', word).id;
            } else if (searchCondition[pos - 1]) {
                return word;
            }
        });
        /*空格替换为and*/
        //var and = id.replace(/\s+/g, ' and ');
        /*模糊查询*/
        var like = id.replace(/~+/g, ' like ');
        /*字段名替换为mysql的模式*/
        reg = /[A-Z]+/g;
        return like.replace(reg, function (word) {
            return '_' + word.toLowerCase();
        })
    }

    /**
     * 为字符串包裹上分号
     */
    function wrapWithBrackets(str) {
        return '\'' + str + '\'';
    }

    /**
     * 为字符串包裹上单括号
     */
    function wrapWithBracketsCircle(str) {
        return '\(' + str + '\)';
    }

    /**
     * 为数组某一个条件相等的对象新增一个对象，通过field相等来判断
     */
    function addAttributeOnly(dest, src, field, name) {
        var out = [];
        angular.forEach(src, function (item) {
            var var1 = getValueByField(dest, field, item[field]);
            if (var1) {
                var1[name] = item;
                out.push(var1);
            }
        });
        return out;
    }

    /**
     * 为数组某一些条件相等的对象新增一个对象，通过field相等来判断
     */
    function addAttributes(dest, src, field, name) {
        angular.forEach(src, function (item) {
            var var1 = getValueListByField(dest, field, item[field]);
            angular.forEach(var1, function (item2) {
                item2[name] = item;
            })
        })
    }

    /**
     * 为数组新增一个对象数组，通过field相等来判断
     */
    function addArray(dest, src, field, name) {
        angular.forEach(src, function (item) {
            try {
                getValueByField(dest, field, item[field])[name].push(item);
            } catch (e) {
                getValueByField(dest, field, item[field])[name] = [];
                getValueByField(dest, field, item[field])[name].push(item);
            }
        })
    }

    /**
     * 为数组1里的每个值添加一个特定的对象
     */
    function addAttributeAnyWay(dest,field,value) {
        angular.forEach(dest,function (item) {
            item[field]=value;
        })
    }

    /**
     * 判断两个date对象对日期是否相等
     */
    function dateEqualsDay(date1, date2) {
        if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据时间戳获得小时，分钟，秒
     */
    function getHourMinSec(time) {
        return [Math.floor(time / (60 * 60 * 1000)), Math.floor(time % (60 * 60 * 1000) / (60 * 1000)), Math.floor(time % (60 * 1000) / 1000)]
    }

    /**
     * 判断是否为空，空则返回0
     */

    function isNullSetZero(item) {
        if (!item) {
            return 0;
        } else {
            return item
        }
    }

    /**
     * 判断是否为空，空则返回0
     */

    function isNullSetString(item) {
        if (!item) {
            return '';
        } else {
            return item
        }
    }

    /**
     * 通过时分秒字符串获得时间戳
     */
    function timestampByTimeString(time) {
        var timestamp=0;
        timestamp+=time.substring(0, 2)*60*60*1000;
        timestamp+=time.substring(3, 5)*60*1000;
        timestamp+=time.substring(6, 8)*1000;
        return timestamp;
    }

    /**
     * 把当前的时间设为0点，主要用于初始化报表时间范围
     */
    function getTodayMin() {
        var today=new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        today.setMilliseconds(0);
        return today;
    }

    /**
     * 把当前的时间设为24点，主要用于初始化报表时间范围
     */
    function getTodayMax() {
        var today=new Date();
        today.setHours(24);
        today.setMinutes(0);
        today.setSeconds(0);
        today.setMilliseconds(0);
        return today;
    }

    return {
        getValueByField: getValueByField,
        getValueListByField: getValueListByField,
        deleteFromArray: deleteFromArray,
        getDeletedArray: getDeletedArray,
        newDateNow: newDateNow,
        newDateAndTime: newDateAndTime,
        objectListToString: objectListToString,
        objectListToStringDuplicate: objectListToStringDuplicate,
        parseSearch: parseSearch,
        wrapWithBrackets: wrapWithBrackets,
        wrapWithBracketsCircle:wrapWithBracketsCircle,
        addAttributeOnly: addAttributeOnly,
        addAttributes: addAttributes,
        addArray: addArray,
        addAttributeAnyWay:addAttributeAnyWay,
        dateEqualsDay: dateEqualsDay,
        getHourMinSec: getHourMinSec,
        isNullSetZero:isNullSetZero,
        isNullSetString:isNullSetString,
        timestampByTimeString:timestampByTimeString,
        getTodayMin:getTodayMin,
        getTodayMax:getTodayMax
    };
}]);