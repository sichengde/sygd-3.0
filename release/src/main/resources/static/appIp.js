window.szIpGoogleHost = 'http://localhost';
angular.module("appIp", [])
    .constant('host', 'http://192.168.0.33')//在云端时这个需要设置为空
    .constant('hostJquery', 'http://192.168.0.33')//这个是无论如何都需要的，客户端能ping通的服务器ip
    .constant('cloud', 'http://101.200.171.37')
    .constant('database', 'yujianyifenghua');
    /*.config(['$httpProvider', function ($httpProvider) {
        //initialize get if not there
        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }

        // Answer edited to include suggestions from comments
        // because previous version of code introduced browser-related errors

        //disable IE ajax request caching
        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        // extra
        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    }])*/
