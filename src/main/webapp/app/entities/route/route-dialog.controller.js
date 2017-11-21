(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('RouteDialogController', RouteDialogController);

    RouteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Route', 'Vehicle'];

    function RouteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Route, Vehicle) {
        var vm = this;

        vm.route = entity;
        vm.clear = clear;
        vm.save = save;
        vm.vehicles = Vehicle.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.route.id !== null) {
                Route.update(vm.route, onSaveSuccess, onSaveError);
            } else {
                Route.save(vm.route, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:routeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
