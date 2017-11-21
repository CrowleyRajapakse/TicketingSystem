(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('HaltDeleteController',HaltDeleteController);

    HaltDeleteController.$inject = ['$uibModalInstance', 'entity', 'Halt'];

    function HaltDeleteController($uibModalInstance, entity, Halt) {
        var vm = this;

        vm.halt = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Halt.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
