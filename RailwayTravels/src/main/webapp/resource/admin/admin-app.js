// let csrfToken = $("meta[name='_csrf']").attr("content");
// let csrfHeader = $("meta[name='_csrf_header']").attr("content");

let adminApp = angular.module('adminApp', ['ngRoute', 'ui.validate'])
    .config(function ($routeProvider) {
        $routeProvider.when('/all-trips',
            {
                templateUrl: 'resource/admin/template/all-trips.html',
                controller: 'allTripsController'
            })
            .when('/trip/:tripId/:from/:to',
                {
                    templateUrl: 'resource/admin/template/passengers.html',
                    controller: 'tripController'
                })
            .when('/stations',
                {
                    templateUrl: 'resource/admin/template/stations.html',
                    controller: 'stationsController'
                })
            .when('/',
                {
                    templateUrl: 'resource/admin/template/welcome.html'
                })
            .otherwise({
                redirectTo: '/'
            });
    });
