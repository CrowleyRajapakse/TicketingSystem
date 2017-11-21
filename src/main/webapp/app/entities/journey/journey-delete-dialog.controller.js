(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('JourneyDeleteController',JourneyDeleteController);

    JourneyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Journey'];

    function JourneyDeleteController($uibModalInstance, entity, Journey) {
        var vm = this;

        vm.journey = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Journey.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
