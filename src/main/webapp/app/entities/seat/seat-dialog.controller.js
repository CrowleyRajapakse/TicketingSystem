(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('SeatDialogController', SeatDialogController);

    SeatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Seat', 'Vehicle'];

    function SeatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Seat, Vehicle) {
        var vm = this;

        vm.seat = entity;
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
            if (vm.seat.id !== null) {
                Seat.update(vm.seat, onSaveSuccess, onSaveError);
            } else {
                Seat.save(vm.seat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:seatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
