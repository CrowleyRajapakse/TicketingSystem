(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('VehicleDeleteController',VehicleDeleteController);

    VehicleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vehicle'];

    function VehicleDeleteController($uibModalInstance, entity, Vehicle) {
        var vm = this;

        vm.vehicle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Vehicle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
