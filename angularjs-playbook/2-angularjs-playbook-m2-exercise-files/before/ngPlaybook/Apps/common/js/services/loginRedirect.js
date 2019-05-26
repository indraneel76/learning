(function (module) {

    var loginRedirect = function ($q, $location) {
       
        return {
        };
    };

    module.factory("loginRedirect", loginRedirect);
    module.config(function ($httpProvider) {
        $httpProvider.interceptors.push("loginRedirect");
    });

}(angular.module("common")));