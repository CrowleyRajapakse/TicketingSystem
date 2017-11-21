(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('SeatDetailController', SeatDetailController);

    SeatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Seat', 'Vehicle'];

    function SeatDetailController($scope, $rootScope, $stateParams, previousState, entity, Seat, Vehicle) {
        var vm = this;

        vm.seat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:seatUpdate', function(event, result) {
            vm.seat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
