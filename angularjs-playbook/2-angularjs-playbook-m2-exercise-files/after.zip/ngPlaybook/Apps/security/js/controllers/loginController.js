﻿(function(module) {

    var loginController = function(oauth, currentUser, alerting, loginRedirect) {
        var model = this;

        model.username = "";
        model.password = "";
        model.user = currentUser.profile;

        model.login = function(form) {
            if (form.$valid) {
                oauth.login(model.username, model.password)
                     .then(function () {
                         loginRedirect.redirectPostLogin();
                     })
                     .catch(alerting.errorHandler("Could not login"));
                model.password = model.username = "";
                form.$setUntouched();                
            }
        }
    };

    module.controller("loginController", loginController);

}(angular.module("security")));