(function(module) {

    var secretController = function (recipes, $sce) {

        var model = this;

        model.recipe = null;
        model.getTrustedTitle = function () {
            return $sce.trustAsHtml(model.recipe.title);
        };

        recipes.getSecret().then(function(recipe) {
            model.recipe = recipe;
        });

    };

    module.controller("secretController", secretController);

}(angular.module("security")));