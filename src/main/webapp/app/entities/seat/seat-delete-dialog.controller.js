(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('SeatDeleteController',SeatDeleteController);

    SeatDeleteController.$inject = ['$uibModalInstance', 'entity', 'Seat'];

    function SeatDeleteController($uibModalInstance, entity, Seat) {
        var vm = this;

        vm.seat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Seat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
