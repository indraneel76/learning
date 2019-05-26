(function (module) {

    var addToken = function (currentUser, $q) {
    
        return {
        }
    };

    module.factory("addToken", addToken);
    module.config(function ($httpProvider) {
        $httpProvider.interceptors.push("addToken");
    });

}(angular.module("common")));