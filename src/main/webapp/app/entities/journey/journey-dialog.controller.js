(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('JourneyDialogController', JourneyDialogController);

    JourneyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Journey', 'Vehicle'];

    function JourneyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Journey, Vehicle) {
        var vm = this;

        vm.journey = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.journey.id !== null) {
                Journey.update(vm.journey, onSaveSuccess, onSaveError);
            } else {
                Journey.save(vm.journey, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:journeyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.departure_time = false;
        vm.datePickerOpenStatus.arrival_time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
