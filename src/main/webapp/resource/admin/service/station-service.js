adminApp.service('stationService', function ($http, $q) {
    this.getAllStationNames = function () {
        let deferred = $q.defer();

        $http.get('/station').then(function success(response) {
            deferred.resolve(response.data.map(station => station.name));
        }, function error(response) {
            deferred.reject(response.status);
        });
        return deferred.promise;
    }
});