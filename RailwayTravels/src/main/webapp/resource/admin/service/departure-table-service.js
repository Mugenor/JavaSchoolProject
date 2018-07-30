let dateOptions = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
function convertDeparture(departure) {
    return {
        stationFrom: departure.stationFrom,
        stationTo: departure.stationTo,
        dateTimeFromMilliseconds: departure.dateTimeFrom,
        dateTimeToMilliseconds: departure.dateTimeTo,
        dateTimeFrom: new Date(departure.dateTimeFrom).toLocaleTimeString(undefined, dateOptions),
        dateTimeTo: new Date(departure.dateTimeTo).toLocaleTimeString(undefined, dateOptions),
        seatsCount: departure.sitsCount,
        freeSeatsCount: departure.freeSitsCount,
        id: departure.id
    };
}

adminApp.service('departureTableService', function ($http, $q) {
    this.createDataTable = function (id, options) {
        $('#' + id).DataTable(options);
    };
    this.load = function () {
        let deferred = $q.defer();
        $http.get('departure').then(function success(response) {
            deferred.resolve(response.data.map(convertDeparture));
        }, function error(response) {
            deferred.reject(response.status);
        });
        return deferred.promise;
    };
    this.sendNewDeparture = function (departure) {
        let deferred = $q.defer();
        $http.post('departure/add', departure).then(function success(response) {
            deferred.resolve(convertDeparture(response.data));
        }, function error(response) {
            deferred.reject(response.status);
        });
        return deferred.promise;
    };
    this.addDepartureToTable = function (newDeparture, table) {
        table.push(newDeparture);
    }
});