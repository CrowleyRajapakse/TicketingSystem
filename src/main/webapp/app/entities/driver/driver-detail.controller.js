(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('DriverDetailController', DriverDetailController);

    DriverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Driver', 'Vehicle'];

    function DriverDetailController($scope, $rootScope, $stateParams, previousState, entity, Driver, Vehicle) {
        var vm = this;

        vm.driver = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:driverUpdate', function(event, result) {
            vm.driver = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
