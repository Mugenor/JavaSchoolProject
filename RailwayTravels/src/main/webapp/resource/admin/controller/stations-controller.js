adminApp.controller('stationsController', function ($scope, stationService, stationTableService) {
    let stationsPromise = stationService.getAllStationNames();
    $scope.stations = [];
    $scope.modalControl = {};
    $scope.addButtonDisabled = false;
    function showError(error) {
        $scope.modalControl.show(error.data);
    }
    stationsPromise.then(function (value) {
        stationTableService.addStationsToTable(value, $scope.stations);
    }, showError);

    $scope.newStation = {name: ''};

    $scope.sendNewStation = function (newStation, stationForm) {
        if(stationForm.$valid) {
            $scope.addButtonDisabled = true;
            let stationName = newStation.name.trim();
            stationService.sendNewStation(stationName).then(function () {
                stationTableService.addStationToTable(stationName, $scope.stations);
                $scope.addButtonDisabled = false;
            }, showError);
        }
    }
});