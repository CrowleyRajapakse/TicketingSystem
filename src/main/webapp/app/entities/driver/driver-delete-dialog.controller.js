(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('DriverDeleteController',DriverDeleteController);

    DriverDeleteController.$inject = ['$uibModalInstance', 'entity', 'Driver'];

    function DriverDeleteController($uibModalInstance, entity, Driver) {
        var vm = this;

        vm.driver = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Driver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
