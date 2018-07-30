adminApp.controller('departureController', function ($scope, departurePassengersService, $routeParams) {
    $scope.$on('$routeChangeSuccess', function () {
        let departureId = $routeParams['departureId'];
        if(departureId) {
            let passengersPromise = departurePassengersService.getAllPassengersByDepartureId(departureId);
            passengersPromise.then(function (passengers) {
                $scope.passengers = passengers;
            })
        }
    })
});