let dateTimeOptions = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};

function convertTrip(trip) {
    for (let i = 0; i < trip.departures.length; ++i) {
        trip.departures[i].dateTimeFromMilliseconds = trip.departures[i].dateTimeFrom;
        trip.departures[i].dateTimeToMilliseconds = trip.departures[i].dateTimeTo;
        trip.departures[i].dateTimeFrom = new Date(trip.departures[i].dateTimeFrom).toLocaleTimeString(undefined, dateTimeOptions);
        trip.departures[i].dateTimeTo = new Date(trip.departures[i].dateTimeTo).toLocaleTimeString(undefined, dateTimeOptions);
    }
    return trip;
}

adminApp.service('tripTableService', function ($http, $q) {
    this.load = function () {
        let deferred = $q.defer();
        $http.get('trip').then(function success(response) {
            deferred.resolve(response.data.map(convertTrip));
        }, function error(response) {
            deferred.reject(response.status);
        });
        return deferred.promise;
    };
    this.addDepartureToNewDepartureList = function (departure, departureList) {
        departureList.push(departure);
    };
    this.removeTripFromTrips = function (trip, trips) {
        for(let i = 0; i < trips.length; ++i) {
            if(trip.id === trips[i].id) {
                trips.splice(i, 1);
                return;
            }
        }
    };
    this.saveTrip = function (trip) {
        let deferred = $q.defer();
        $http.post('trip/add', trip).then(function success(response) {
            deferred.resolve(response.data);
        }, function error(response) {
            deferred.reject(response.status);
        });
        return deferred.promise;
    };
    this.convertTrip = function (trip) {
        return convertTrip(trip);
    };
    this.deleteTrip = function (trip) {
        let deferred = $q.defer();
        $http.delete('trip/' + trip.id).then(function (response) {
            deferred.resolve(response.data);
        }, function (response) {
            deferred.reject(response);
        });
        return deferred.promise;
    }
});