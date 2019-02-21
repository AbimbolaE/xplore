xploreApp.controller('productController', function ($scope, $http) {

    $scope.brand = "";
    $scope.category = "Shoes";
    $scope.colour = "Black";
    $scope.region = "UK";
    $scope.size = 14;
    $scope.heel = "";
    $scope.kind = "";
    $scope.design = "";

    $scope.categories = ["Shoes", "Clothes"];

    $scope.create = () => {

        let description = {
            heel: $scope.heel,
            kind: $scope.kind,
            design: $scope.design
        };

        let payload = {
            brand: $scope.brand,
            category: $scope.category,
            colour: $scope.colour,
            region: $scope.region,
            size: $scope.size.toString(),
            description: description
        };

        $http
            .post("/products", JSON.stringify(payload))
            .then(console.log, console.log)
            .then(location.reload);
    }
});