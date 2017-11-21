(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('DriverDialogController', DriverDialogController);

    DriverDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Driver', 'Vehicle'];

    function DriverDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Driver, Vehicle) {
        var vm = this;

        vm.driver = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.vehicles = Vehicle.query({filter: 'driver-is-null'});
        $q.all([vm.driver.$promise, vm.vehicles.$promise]).then(function() {
            if (!vm.driver.vehicleId) {
                return $q.reject();
            }
            return Vehicle.get({id : vm.driver.vehicleId}).$promise;
        }).then(function(vehicle) {
            vm.vehicles.push(vehicle);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.driver.id !== null) {
                Driver.update(vm.driver, onSaveSuccess, onSaveError);
            } else {
                Driver.save(vm.driver, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:driverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_of_birth = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
