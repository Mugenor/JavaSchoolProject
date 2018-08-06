adminApp.directive('trip', function () {
    console.log('in trip');
    return {
        restrict: 'E',
        scope: {
            trip: '=trip',
            selectStation: '=selectStation'
        },
        templateUrl: 'resource/admin/template/trip.html'
    };
});