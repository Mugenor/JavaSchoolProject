adminApp.directive('notContainedIn', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attr, ngModel) {
            ngModel.$validators.containedIn = function (modelValue, viewValue) {
                let list = scope[attr.notContainedIn];
                if(list) {
                    return !list.includes(viewValue);
                }
            }
        }
    }
});