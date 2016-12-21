/**
 * Created by 舒展 on 2016-05-29.
 * 
 */
App.factory('popUpService', [function () {
    var scopeMap = {};//弹窗对象
    var elementMap = {};//如果需要弹出时修改样式，例如右键时根据位置弹出
    var specialFunction;
    var param;

    /**
     * 初始化
     * @param name 弹窗名称.html前边的英文
     * @param scope 对应的域
     * @param element 弹窗元素
     */
    function init(name, scope, element) {
        scopeMap[name] = scope;
        elementMap[name] = element;
    }

    /**
     * 弹出窗口
     * @param name 弹窗名称
     * @param css 添加的样式
     * @param fun 关闭之后调用的函数
     * @param param2 传递进来的参数
     */
    function pop(name, css, fun, param2) {
        scopeMap[name].pop = true;
        if (css) {
            elementMap[name].css(css);
        }
        /*弹出时初始化特殊方法*/
        scopeMap[name].specialFunction=fun;
        /*弹出时可以带一个参数*/
        param=param2;
    }

    /**
     * 关闭弹窗
     * @param name 弹窗名称
     * @param notRefresh 关闭之后不刷新标签回调和pop传递进来的回调
     */
    function close(name, notRefresh) {
        scopeMap[name].pop = false;
        /*先判断有没有特殊方法，例如在结算时打开房吧页面，关闭房吧页面时需要刷新账务明细*/
        if (scopeMap[name].specialFunction && !notRefresh) {
            scopeMap[name].specialFunction();
        } else {
            /*有刷新方法并且notRefresh为空或false时刷新*/
            if (scopeMap[name].refresh && !notRefresh) {
                scopeMap[name].refresh();
            }
        }
    }

    /**
     * 获取pop传递进来的参数
     */
    function getParam() {
        return param;
    }
    return {
        init: init,
        pop: pop,
        close: close,
        getParam:getParam
    }
}]);