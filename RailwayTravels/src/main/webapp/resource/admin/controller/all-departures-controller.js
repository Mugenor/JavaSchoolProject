function setValidClass(valid, obj) {
    if (valid) {
        obj.class = 'is-valid';
    } else {
        obj.class = 'is-invalid';
    }
}

adminApp.controller('allDeparturesController', function ($scope, departureTableService, stationService) {
    const SEATS_IN_COACH = 36;
    let departuresPromise = departureTableService.load();
    let stationsPromise = stationService.getAllStationNames();

    departuresPromise.then(function (value) {
        $scope.departures = value;
    });

    stationsPromise.then(function (stationNames) {
        $scope.stationNames = stationNames;
        $('.autocomplete').autocomplete({
            source: stationNames,
        });
    });

    $scope.checkStation = function (station) {
        if ($scope.stationNames) {
            if (!$scope.stationNames.includes(station.$viewValue)) {
                station.$modelValue = '';
            }
        }
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

    dateTimeFromInput.on('change.dp', function (event) {
        let now = new Date();
        now.setMinutes(now.getMinutes() + 10);
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        if (now.getTime() >= departureTime.getTime()) {
            dateTimeFromInput.val('');
        } else {
            dateTimeToInput.data('datetimepicker').setStartDate(departureTime);
        }
    });
    dateTimeToInput.on('change.dp', function (event) {
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        let arrivalTime = dateTimeToInput.data('datetimepicker').getDate();
        if(departureTime.getTime() >= arrivalTime.getTime()) {
            dateTimeToInput.val('');
        } else {
            dateTimeFromInput.data('datetimepicker').setEndDate(arrivalTime);
        }
    });

    $scope.sendNewDeparture = function (newDeparture, departureForm) {
        if (departureForm.$valid && !dateTimeFromInput.hasClass('is-invalid') && !dateTimeToInput.hasClass('in-invalid')) {
            $scope.addButtonDisabled = true;
            let departure = {
                stationFrom: newDeparture.stationFrom,
                stationTo: newDeparture.stationTo,
                dateTimeFrom: dateTimeFromInput.data('datetimepicker').getDate().getTime(),
                dateTimeTo: dateTimeToInput.data('datetimepicker').getDate().getTime(),
                coachCount: newDeparture.coachCount
            };

            departureTableService.sendNewDeparture(departure, $scope.departures).then(function (value) {
                departureTableService.addDepartureToTable(value, $scope.departures);
                $scope.addButtonDisabled = false;
            });
            $scope.dateTimeToClass.class = '';
            $scope.dateTimeFromClass.class = '';
            $scope.newDeparture = {};
            departureForm.$setPristine(true);
        }
    };

    $scope.notTheSame = function (v1, v2) {
        if (v1.$viewValue && v2.$viewValue) {
            return v1.$viewValue !== v2.$viewValue;
        } else {
            return true;
        }
    };

    $scope.afterNow = function () {
        if (!dateTimeFromInput.val() || dateTimeFromInput.val() === '') {
            return true;
        }
        let now = new Date();
        now.setMinutes(now.getMinutes() + 10);
        let inputDate = dateTimeFromInput.data('datetimepicker').getDate();
        return now.getTime() < inputDate.getTime();
    };
    $scope.beforeArrival = function () {
        if (!dateTimeToInput.val() || dateTimeToInput.val() === '' || !dateTimeFromInput.val() ||  dateTimeFromInput.val() === '') {
            return true;
        }
        let arrivalTime = dateTimeToInput.data('datetimepicker').getDate();
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        return departureTime.getTime() < arrivalTime.getTime();
    };
    $scope.afterDeparture = function () {
        if(!dateTimeFromInput.val() || dateTimeFromInput === '' || !dateTimeToInput.val() || dateTimeToInput.val() === '') {
            return true;
        }
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        let arrivalTime = dateTimeToInput.data('datetimepicker').getDate();
        return departureTime.getTime() < arrivalTime.getTime();
    }

    // $scope.createDataTable = function () {
    //     departureTableService.createDataTable('departures', {
    //         responsive: true,
    //         paging: false,
    //         columnDefs: [{
    //             targets: 5,
    //             searchable: false,
    //             orderable: false,
    //         }],
    //         bAutoWidth: false
    //     });
    // }
});