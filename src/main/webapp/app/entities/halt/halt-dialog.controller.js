(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('HaltDialogController', HaltDialogController);

    HaltDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Halt', 'Route'];

    function HaltDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Halt, Route) {
        var vm = this;

        vm.halt = entity;
        vm.clear = clear;
        vm.save = save;
        vm.routes = Route.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.halt.id !== null) {
                Halt.update(vm.halt, onSaveSuccess, onSaveError);
            } else {
                Halt.save(vm.halt, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:haltUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
