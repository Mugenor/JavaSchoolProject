adminApp.directive('editDepartureModal', function (stationService, editTripService) {
    function initDateTimePicker(picker, startDate, endDate, initDate) {
        startDate.setHours(startDate.getHours() - startDate.getTimezoneOffset() / 60);
        endDate.setHours(endDate.getHours() - endDate.getTimezoneOffset() / 60);
        picker.startDate = startDate;
        picker.endDate = endDate;
        picker.setDate(initDate);

    }

    return {
        restrict: 'E',
        templateUrl: 'resource/admin/template/edit-departure-modal.html',
        scope: {
            control: '=',
            onerror: '='
        },
        link: function (scope, element, attrs) {
            const datetimepickerConfigObj = {
                format: "dd.mm.yyyy, hh:ii",
                immediateUpdates: true
            };
            scope.dateTimeFromInput = $('#dateTimeFrom').datetimepicker(datetimepickerConfigObj).data('datetimepicker');
            scope.dateTimeToInput = $('#dateTimeTo').datetimepicker(datetimepickerConfigObj).data('datetimepicker');

            let modal = $('#editDepartureModal').modal({
                show: false
            });
            stationService.getAllStationNames()
                .then(function (data) {
                    scope.stations = data;
                    $('.autocomplete')
                        .autocomplete({
                            source: scope.stations
                        });
                });
            scope.checkStation = function (station) {
                if (scope.stations) {
                    if (!scope.stations.includes(station.$viewValue)) {
                        station.$modelValue = '';
                    }
                }
            };
            scope.notTheSame = function (v1, v2) {
                let res = v1.$viewValue !== v2.$viewValue;
                if (res) {
                    delete v1.$error.same;
                    delete v2.$error.same;
                }
                return res;
            };
            scope.sameWithNext = function (departure, stationForm) {
                return !departure || departure.stationTo !== stationForm.$viewValue;
            };
            scope.sameWithPrevious = function (departure, stationForm) {
                return !departure || departure.stationFrom !== stationForm.$viewValue;
            };
            scope.afterDate = function (datetimepicker) {
                return datetimepicker.getUTCDate().getTime() > datetimepicker.startDate.getTime();
            };
            scope.beforeDate = function (datetimepicker) {
                return datetimepicker.getUTCDate().getTime() < datetimepicker.endDate.getTime();
            };
            scope.internalControl = scope.control || {};
            scope.internalControl.show = function (tripId, departureIndex, departureBefore, departure, departureAfter) {
                scope.updateDepartureForm.$setPristine(true);
                scope.tripId = tripId;
                scope.departureIndex = departureIndex;
                scope.departure = {
                    stationFrom: departure.stationFrom,
                    stationTo: departure.stationTo,
                    dateTimeFrom: departure.dateTimeFrom,
                    dateTimeTo: departure.dateTimeTo
                };
                scope.outerDeparture = departure;
                scope.outerDepartureBefore = departureBefore;
                scope.outerDepartureAfter = departureAfter;
                let today = new Date();
                today.setMinutes(today.getMinutes() + 10);
                let endDate = new Date();
                endDate.setFullYear(endDate.getFullYear() + 5);
                initDateTimePicker(scope.dateTimeFromInput, departureBefore ? new Date(departureBefore.dateTimeToMilliseconds) : today,
                    new Date(departure.dateTimeToMilliseconds),
                    new Date(departure.dateTimeFromMilliseconds));
                initDateTimePicker(scope.dateTimeToInput, new Date(departure.dateTimeFromMilliseconds),
                    departureAfter ? new Date(departureAfter.dateTimeFromMilliseconds) : endDate,
                    new Date(departure.dateTimeToMilliseconds));
                modal.modal('show');
            };
            scope.applyChanges = function () {
                if (scope.updateDepartureForm.$valid) {
                    let _outerDeparture = scope.outerDeparture;
                    let _outerDepartureBefore = scope.outerDepartureBefore;
                    let _outerDepartureAfter = scope.outerDepartureAfter;
                    let _departure = scope.departure;
                    let dateTimeFromMilliseconds = scope.dateTimeFromInput.date.getTime() + scope.dateTimeFromInput.date.getTimezoneOffset() * 60000;
                    let dateTimeToMilliseconds = scope.dateTimeToInput.date.getTime() + scope.dateTimeToInput.date.getTimezoneOffset() * 60000;
                    editTripService.updateDeparture(scope.tripId, {
                        numberInTrip: scope.outerDeparture.numberInTrip,
                        stationFrom: scope.departure.stationFrom,
                        stationTo: scope.departure.stationTo,
                        dateTimeFrom: dateTimeFromMilliseconds,
                        dateTimeTo: dateTimeToMilliseconds
                    }).then(function (data) {
                        _outerDeparture.stationFrom = _departure.stationFrom;
                        _outerDeparture.stationTo = _departure.stationTo;
                        _outerDeparture.dateTimeFrom = _departure.dateTimeFrom;
                        _outerDeparture.dateTimeFromMilliseconds = dateTimeFromMilliseconds;
                        _outerDeparture.dateTimeTo = _departure.dateTimeTo;
                        _outerDeparture.dateTimeToMilliseconds = dateTimeToMilliseconds;
                        if(_outerDepartureBefore) {
                            _outerDepartureBefore.stationTo = _departure.stationFrom;
                        }
                        if(_outerDepartureAfter) {
                            _outerDepartureAfter.stationFrom = _departure.stationTo;
                        }
                        modal.modal('hide');
                    }, function (error) {
                        modal.modal('hide');
                        scope.onerror(error);
                    });
                }
            };
        }
    }
});