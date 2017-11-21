(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('RouteDeleteController',RouteDeleteController);

    RouteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Route'];

    function RouteDeleteController($uibModalInstance, entity, Route) {
        var vm = this;

        vm.route = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Route.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
