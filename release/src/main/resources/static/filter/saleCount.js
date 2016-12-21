/**
 * Created by Administrator on 2016/10/23 0023.
 */
App.filter('saleCount', ['dataService', function (dataService) {
    return function (saleCountList, target) {
        if (!target) {
            return saleCountList;
        }
        var out = [];
        var var1 = dataService.getPointOfSale();
        for (var j = 0; j < var1.length; j++) {
            var pointOfSale = var1[j];
            if (pointOfSale.firstPointOfSale == target) {
                out = pointOfSale.secondPointOfSale.split(' ')
            }
        }
        return out;
    }
}]);