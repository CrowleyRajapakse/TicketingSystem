(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('SmartCardDialogController', SmartCardDialogController);

    SmartCardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SmartCard'];

    function SmartCardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SmartCard) {
        var vm = this;

        vm.smartCard = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.smartCard.id !== null) {
                SmartCard.update(vm.smartCard, onSaveSuccess, onSaveError);
            } else {
                SmartCard.save(vm.smartCard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:smartCardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.expiryDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        $scope.options = ['Activated','Not Activated','Rejected'];
    }
})();
