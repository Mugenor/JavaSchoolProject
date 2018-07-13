adminApp.controller('stationsController', function ($scope, stationService, stationTableService) {
    let stationsPromise = stationService.getAllStationNames();
    $scope.stations = [];
    stationsPromise.then(function (value) {
        console.log(value);
        stationTableService.addStationsToTable(value, $scope.stations);
    });
});