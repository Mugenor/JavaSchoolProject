adminApp.directive('trip', function () {
    console.log('in trip');
    return {
        restrict: 'E',
        scope: {
            departures: '=departures'
        },
        templateUrl: 'resource/admin/template/trip.html'
    };
});