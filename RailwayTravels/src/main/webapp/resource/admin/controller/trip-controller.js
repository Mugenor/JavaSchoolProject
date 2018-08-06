adminApp.controller('tripController', function ($scope, departurePassengersService, $routeParams) {
    $scope.$on('$routeChangeSuccess', function () {
        let departureId = $routeParams['tripId'];
        let from = $routeParams['from'];
        let to = $routeParams['to'];
        if(departureId && from && to) {
            let passengersPromise = departurePassengersService.getAllPassengersByTripIdFromToDepartureIndexes(departureId, from, to);
            passengersPromise.then(function (passengers) {
                $scope.passengers = passengers;
            })
        }
    })
});