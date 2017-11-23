(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('SmartCardDeleteController',SmartCardDeleteController);

    SmartCardDeleteController.$inject = ['$uibModalInstance', 'entity', 'SmartCard'];

    function SmartCardDeleteController($uibModalInstance, entity, SmartCard) {
        var vm = this;

        vm.smartCard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SmartCard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
