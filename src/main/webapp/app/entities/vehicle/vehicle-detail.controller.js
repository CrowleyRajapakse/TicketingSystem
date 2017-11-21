(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('VehicleDetailController', VehicleDetailController);

    VehicleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vehicle', 'Driver', 'Route', 'Seat'];

    function VehicleDetailController($scope, $rootScope, $stateParams, previousState, entity, Vehicle, Driver, Route, Seat) {
        var vm = this;

        vm.vehicle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:vehicleUpdate', function(event, result) {
            vm.vehicle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
