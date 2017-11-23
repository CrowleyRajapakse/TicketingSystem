(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('ReservationDetailController', ReservationDetailController);

    ReservationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reservation', 'Vehicle', 'Seat'];

    function ReservationDetailController($scope, $rootScope, $stateParams, previousState, entity, Reservation, Vehicle, Seat) {
        var vm = this;

        vm.reservation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:reservationUpdate', function(event, result) {
            vm.reservation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
