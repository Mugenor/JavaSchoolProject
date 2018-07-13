adminApp.service('stationTableService', function () {
    this.addStationToTable = function (newStation, stationList) {
        stationList.push(newStation);
    };
    this.addStationsToTable = function (stations, stationList) {
        stations.forEach((station) => stationList.push(station));
    };
});