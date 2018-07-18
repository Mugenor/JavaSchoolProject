let csrfToken = $("meta[name='_csrf']").attr("content");
let csrfHeader = $("meta[name='_csrf_header']").attr("content");

let adminApp = angular.module('adminApp', ['ngRoute', 'ui.validate'])
    .config(function ($routeProvider) {
        $routeProvider.when('/all-departures',
            {
                templateUrl: '/resource/admin/template/all-departures.html',
                controller: 'allDeparturesController'
            })
            .when('/departure/:departureId',
                {
                    templateUrl: '/resource/admin/template/passengers.html',
                    controller: 'departureController'
                })
            .when('/stations',
                {
                    templateUrl: '/resource/admin/template/stations.html',
                    controller: 'stationsController'
                })
            .when('/',
                {
                    templateUrl: '/resource/admin/template/welcome.html'
                })
            .otherwise({
                redirectTo: '/'
            });
    }).run(function ($http) {
        $http.defaults.headers.common[csrfHeader] = csrfToken;
    });
