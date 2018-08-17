adminApp.controller('editTripController', function ($scope, $routeParams, editTripService) {
    let tripId;
    $scope.editDepartureControl = {};
    $scope.messageModalControl = {};
    $scope.makeEditable = function (i) {
        $scope.editDepartureControl.show(tripId, $scope.trip.departures[i].numberInTrip,
            $scope.trip.departures[i-1],
            $scope.trip.departures[i],
            $scope.trip.departures[i+1]);
    };
    $scope.onerror = function(error) {
        $scope.messageModalControl.show(error.data);
    };
    $scope.$on('$routeChangeSuccess', function () {
        tripId = $routeParams.tripId;
        editTripService.getTripInfo(tripId)
            .then(function (data) {
                $scope.trip = data;
            });
    })
});