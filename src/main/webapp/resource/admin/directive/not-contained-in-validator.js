adminApp.directive('notContainedIn', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attr, ngModel) {
            ngModel.$validators.containedIn = function (modelValue, viewValue) {
                let list = scope[attr.notContainedIn];
                console.log('in not contained', list, modelValue, viewValue);
                if(list) {
                    return !list.includes(viewValue);
                }
            }
        }
    }
});