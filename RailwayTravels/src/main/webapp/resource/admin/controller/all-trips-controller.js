function setValidClass(valid, obj) {
    if (valid) {
        obj.class = 'is-valid';
    } else {
        obj.class = 'is-invalid';
    }
}

adminApp.controller('allTripsController', function ($scope, $location, tripTableService, stationService) {
    const SEATS_IN_COACH = 36;
    let tripsPromise = tripTableService.load();
    let stationsPromise = stationService.getAllStationNames();
    $scope.departuresInNewTrip = [];
    $scope.newTrip = {
        departures: $scope.departuresInNewTrip
    };

    tripsPromise.then(function (value) {
        value.forEach(function (it) {
            it.chosen = [];
        });
        $scope.trips = value;
    });

    stationsPromise.then(function (stationNames) {
        $scope.stationNames = stationNames;
        $('.autocomplete').autocomplete({
            source: stationNames,
        });
    });

    $scope.selectStation = function (trip, index) {
        let chosen = trip.chosen;
        let indexInChosen = chosen.indexOf(index);
        if (indexInChosen !== -1) {
            chosen.splice(indexInChosen, 1);
        } else {
            if (chosen.length < 2) {
                chosen.push(index);
            }
        }
        chosen.sort();
    };

    $scope.deleteTrip = function (trip) {
        tripTableService.deleteTrip(trip).then(function (data) {
            tripTableService.removeTripFromTrips(trip, $scope.trips);
        }, function (resp) {
            console.log(resp);
        });
    };

    $scope.watchPassengers = function (trip) {
        $location.path('/trip/' + trip.id + '/' + trip.chosen[0] + '/' + (trip.chosen[1] - 1))
    };


    $scope.numberInputStyle = {width: '160  px'};
    $scope.addButtonDisabled = false;

    $scope.newDeparture = {};

    let dateTimeFromInput = $('#dateTimeFrom');
    let dateTimeToInput = $('#dateTimeTo');
    let today = new Date();
    today.setMinutes(today.getMinutes() + 10);
    let endDate = new Date();
    endDate.setFullYear(endDate.getFullYear() + 5);

    $scope.dateTimeToClass = {class: ''};
    $scope.dateTimeFromClass = {class: ''};

    $("#dateTimeFrom, #dateTimeTo").datetimepicker({
        format: "dd.mm.yyyy, hh:ii",
        startDate: today,
        endDate: endDate,
        immediateUpdates: true
    });

    console.log(dateTimeFromInput.data('datetimepicker'));

    $scope.checkStation = function (station) {
        if ($scope.stationNames) {
            if (!$scope.stationNames.includes(station.$viewValue)) {
                station.$modelValue = '';
            }
        }
    };

    $scope.clearTrip = function () {
        $scope.departuresInNewTrip.length = 0;
        $scope.newDeparture = {};
        dateTimeFromInput.data('datetimepicker').setStartDate(today);
        dateTimeToInput.data('datetimepicker').setStartDate(today);
        dateTimeFromInput.data('datetimepicker').setInitialDate(today);
        dateTimeToInput.data('datetimepicker').setInitialDate(today);
        dateTimeFromInput.data('datetimepicker').setEndDate(endDate);
        dateTimeToInput.data('datetimepicker').setEndDate(endDate);
    };

    $scope.removeLastArrival = function () {
        $scope.departuresInNewTrip.length = $scope.departuresInNewTrip.length - 1;
        let lastDeparture = $scope.departuresInNewTrip.length > 0 ? $scope.departuresInNewTrip[$scope.departuresInNewTrip.length - 1] : undefined;
        $scope.newDeparture = {
            stationFrom: lastDeparture !== undefined ? lastDeparture.stationTo : ''
        };
        let dateTimeTo = lastDeparture !== undefined ? lastDeparture.dateTimeTo : new Date();
        dateTimeFromInput.data('datetimepicker').setStartDate(dateTimeTo);
        dateTimeToInput.data('datetimepicker').setStartDate(dateTimeTo);
        dateTimeFromInput.data('datetimepicker').setInitialDate(dateTimeTo);
        dateTimeToInput.data('datetimepicker').setInitialDate(dateTimeTo);
        dateTimeTo = new Date(dateTimeTo);
        dateTimeTo.setFullYear(dateTimeTo.getFullYear() + 2);
        dateTimeFromInput.data('datetimepicker').setEndDate(dateTimeTo);
        dateTimeToInput.data('datetimepicker').setEndDate(dateTimeTo);
    };

    dateTimeFromInput.on('change.dp', function (event) {
        let minTime;
        if ($scope.departuresInNewTrip.length === 0) {
            minTime = new Date();
            minTime.setMinutes(minTime.getMinutes() + 10);
        } else {
            minTime = new Date($scope.departuresInNewTrip[$scope.departuresInNewTrip.length - 1].dateTimeToMilliseconds);
        }
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        if (minTime.getTime() >= departureTime.getTime()) {
            dateTimeFromInput.val('');
        } else {
            dateTimeToInput.data('datetimepicker').setStartDate(departureTime);
            dateTimeToInput.data('datetimepicker').setInitialDate(departureTime);
        }
    });
    dateTimeToInput.on('change.dp', function (event) {
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        let arrivalTime = dateTimeToInput.data('datetimepicker').getDate();
        if (departureTime.getTime() >= arrivalTime.getTime()) {
            dateTimeToInput.val('');
        } else {
            dateTimeFromInput.data('datetimepicker').setEndDate(arrivalTime);
        }
    });

    $scope.addNextDeparture = function (newDeparture, departureForm) {
        if (departureForm.$valid && !dateTimeFromInput.hasClass('is-invalid') && !dateTimeToInput.hasClass('in-invalid')) {
            let dateTimeFrom = dateTimeFromInput.data('datetimepicker').getDate();
            let dateTimeTo = dateTimeToInput.data('datetimepicker').getDate();
            let departure = {
                stationFrom: newDeparture.stationFrom,
                stationTo: newDeparture.stationTo,
                dateTimeFrom: dateTimeFrom.toLocaleTimeString(undefined, dateTimeOptions),
                dateTimeFromMilliseconds: dateTimeFrom.getTime(),
                dateTimeTo: dateTimeTo.toLocaleTimeString(undefined, dateTimeOptions),
                dateTimeToMilliseconds: dateTimeTo.getTime(),
            };

            tripTableService.addDepartureToNewDepartureList(departure, $scope.departuresInNewTrip);

            $scope.dateTimeToClass.class = '';
            $scope.dateTimeFromClass.class = '';
            dateTimeFromInput.data('datetimepicker').setStartDate(dateTimeTo);
            dateTimeFromInput.data('datetimepicker').setInitialDate(dateTimeTo);
            dateTimeTo = new Date(dateTimeTo);
            dateTimeTo.setFullYear(dateTimeTo.getFullYear() + 2);
            dateTimeToInput.data('datetimepicker').setStartDate(dateTimeTo);
            dateTimeFromInput.data('datetimepicker').setEndDate(dateTimeTo);
            $scope.newDeparture = {
                stationFrom: departure.stationTo,
                stationTo: undefined
            };
            departureForm.$setPristine(true);
        }
    };
    $scope.saveTrip = function (tripForm) {
        if ($scope.departuresInNewTrip.length !== 0 && tripForm.$valid) {
            let departures = [];
            for (let i = 0; i < $scope.departuresInNewTrip.length; ++i) {
                departures.push({
                    stationFrom: $scope.departuresInNewTrip[i].stationFrom,
                    stationTo: $scope.departuresInNewTrip[i].stationTo,
                    dateTimeFrom: $scope.departuresInNewTrip[i].dateTimeFromMilliseconds,
                    dateTimeTo: $scope.departuresInNewTrip[i].dateTimeToMilliseconds,
                    coachCount: $scope.coachCount
                });
            }
            $scope.departuresInNewTrip = [];
            $scope.newTrip = {
                departures: $scope.departuresInNewTrip
            };
            $scope.newDeparture = {};
            console.log($scope.departuresInNewTrip);
            tripTableService.saveTrip(departures).then(function (trip) {
                tripTableService.addDepartureToNewDepartureList(tripTableService.convertTrip(trip), $scope.trips);
            }, function (err) {
                alert('error');
            });
        }
    };

    $scope.notTheSame = function (v1, v2) {
        console.log(v1, v2);
        if (!v1.$pristine && !v1.$pristine) {
            return v1.$viewValue !== v2.$viewValue;
        } else {
            return true;
        }
    };

    $scope.afterNow = function () {
        if (!dateTimeFromInput.val() || dateTimeFromInput.val() === '') {
            return true;
        }
        let minTime;
        if ($scope.departuresInNewTrip.length === 0) {
            minTime = new Date();
            minTime.setMinutes(minTime.getMinutes() + 10);
        } else {
            minTime = new Date($scope.departuresInNewTrip[$scope.departuresInNewTrip.length - 1].dateTimeToMilliseconds);
        }
        let inputDate = dateTimeFromInput.data('datetimepicker').getDate();
        return minTime.getTime() < inputDate.getTime();
    };
    $scope.beforeArrival = function () {
        if (!dateTimeToInput.val() || dateTimeToInput.val() === '' || !dateTimeFromInput.val() || dateTimeFromInput.val() === '') {
            return true;
        }
        let arrivalTime = dateTimeToInput.data('datetimepicker').getDate();
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        return departureTime.getTime() < arrivalTime.getTime();
    };
    $scope.afterDeparture = function () {
        if (!dateTimeFromInput.val() || dateTimeFromInput === '' || !dateTimeToInput.val() || dateTimeToInput.val() === '') {
            return true;
        }
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        let arrivalTime = dateTimeToInput.data('datetimepicker').getDate();
        return departureTime.getTime() < arrivalTime.getTime();
    };
});