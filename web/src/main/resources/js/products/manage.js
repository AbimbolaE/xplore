xploreApp.controller('productController', function ($scope, $http) {

    $scope.products = [];

    let refreshProducts = (products) => $scope.products = products

    $http
        .get("/products")
        .then(refreshProducts, console.log)
});