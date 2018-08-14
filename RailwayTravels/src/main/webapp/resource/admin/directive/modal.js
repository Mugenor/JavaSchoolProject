adminApp.directive('modal', function () {
    return {
        restrict: 'E',
        templateUrl: 'resource/admin/template/modal.html',
        scope: {
            control: '='
        },
        link: function (scope, element, attrs) {
            let modal = $('#modal').modal({
                show: false
            });
            scope.message = '';
            scope.internalControl = scope.control || {};
            scope.internalControl.show = function (message) {
                console.log('WooHoo', message);
                scope.message = message;
                modal.modal('show');
            };
        }
    }
});