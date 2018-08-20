adminApp.directive('trip', function () {
    return {
        restrict: 'E',
        scope: {
            trip: '=trip',
            selectStation: '=selectStation'
        },
        templateUrl: 'resource/admin/template/trip.html'
    };
});