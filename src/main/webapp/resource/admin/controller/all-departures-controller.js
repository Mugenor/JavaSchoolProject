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
        console.log('in promise resolve', value);
        $scope.departures = value;
    });

    stationsPromise.then(function (stationNames) {
        console.log('in promise resolve', stationNames);
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
    let endDate = new Date();
    endDate.setFullYear(endDate.getFullYear() + 5);

    $scope.dateTimeToClass = {class: ''};
    $scope.dateTimeFromClass = {class: ''};

    $("#dateTimeFrom, #dateTimeTo").datetimepicker({
        format: "dd.mm.yyyy, HH:ii",
        startDate: today,
        endDate: endDate,
        immediateUpdates: true
    }).change(function (event) {
        let dateTimeFromValid = true, dateTimeToValid = true;
        if (dateTimeToInput.val() === '' ||
            dateTimeFromInput.data('datetimepicker').getDate().getTime() >= dateTimeToInput.data('datetimepicker').getDate().getTime()) {
            dateTimeToValid = false;
        }
        console.log(dateTimeFromInput.val());
        if (dateTimeFromInput.val().trim() === '') {
            dateTimeFromValid = false;
        }
        setValidClass(dateTimeFromValid, $scope.dateTimeFromClass);
        setValidClass(dateTimeToValid, $scope.dateTimeToClass);
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
        }
    };


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