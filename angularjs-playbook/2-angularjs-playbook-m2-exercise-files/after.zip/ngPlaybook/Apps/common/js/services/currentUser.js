(function(module) {
   
    var currentUser = function(localStorage){

        var USERKEY = "utoken";

        var setProfile = function (username, token) {
            profile.username = username;
            profile.token = token;
            localStorage.add(USERKEY, profile);
        };

        var initialize = function () {
            var user = {
                username: "",
                token: "",
                get loggedIn() {
                    return this.token;
                }
            };

            var localUser = localStorage.get(USERKEY);
            if (localUser) {
                user.username = localUser.username;
                user.token = localUser.token;
            }
            return user;
        };

        var profile = initialize();
        
        return {
            setProfile: setProfile,
            profile: profile
        };
    };

    module.factory("currentUser", currentUser);

}(angular.module("common")));