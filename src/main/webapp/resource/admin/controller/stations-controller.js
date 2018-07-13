adminApp.controller('stationsController', function ($scope, stationService, stationTableService) {
    let stationsPromise = stationService.getAllStationNames();
    $scope.stations = [];
    $scope.addButtonDisabled = false;
    stationsPromise.then(function (value) {
        console.log(value);
        stationTableService.addStationsToTable(value, $scope.stations);
    });

    $scope.newStation = {name: ''};

    $scope.sendNewStation = function (newStation, stationForm) {
        if(stationForm.$valid) {
            $scope.addButtonDisabled = true;
            let stationName = newStation.name.trim();
            stationService.sendNewStation(stationName).then(function () {
                stationTableService.addStationToTable(stationName, $scope.stations);
                $scope.addButtonDisabled = false;
            });
        }
    }
});