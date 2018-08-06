function convertPassenger(passenger) {
    return {
        name: passenger.name,
        surname: passenger.surname,
        birthdayMilliseconds: passenger.birthday,
        birthday: new Date(passenger.birthday).toLocaleTimeString(undefined, dateOptions),
    };
}

adminApp.service('departurePassengersService', function ($http, $q) {
    this.getAllPassengersByTripIdFromToDepartureIndexes = function (departureId, from, to) {
        let deferred = $q.defer();
        $http.get('passenger/' + departureId + '/' + from + '/' + to).then(function success(response) {
            deferred.resolve(response.data.map(convertPassenger));
        }, function error(response) {
            deferred.reject(response.status);
        });
        return deferred.promise;
    }
});