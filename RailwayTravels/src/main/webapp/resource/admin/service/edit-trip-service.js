adminApp.service('editTripService', function ($http, $q) {
    this.getTripInfo = function (tripId) {
        let deferred = $q.defer();
        $http.get('trip/' + tripId).then(function (response) {
            deferred.resolve(convertTrip(response.data));
        }, function (error) {
            deferred.resolve(error);
        });
        return deferred.promise;
    };
    this.updateDeparture = function (tripId, updatedDeparture) {
        let deferred = $q.defer();
        let departure = {
            tripId: tripId,
            departureIndex: updatedDeparture.numberInTrip,
            departure: {
                stationFrom: updatedDeparture.stationFrom,
                stationTo: updatedDeparture.stationTo,
                dateTimeFrom: updatedDeparture.dateTimeFrom,
                dateTimeTo: updatedDeparture.dateTimeTo
            }
        };
        $http.post('departure/update', departure)
            .then(function (response) {
                deferred.resolve(response.data);
            }, function (error) {
                deferred.reject(error);
            });
        return deferred.promise;
    }
});