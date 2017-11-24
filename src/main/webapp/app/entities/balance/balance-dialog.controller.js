(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('BalanceDialogController', BalanceDialogController);

    BalanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Balance', 'SmartCard'];

    function BalanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Balance, SmartCard) {
        var vm = this;

        vm.balance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.smartcardids = SmartCard.query({filter: 'smartcardid-is-null'});
        $q.all([vm.balance.$promise, vm.smartcardids.$promise]).then(function() {
            if (!vm.balance.smartCardIDId) {
                return $q.reject();
            }
            return SmartCard.get({id : vm.balance.smartCardIDId}).$promise;
        }).then(function(smartCardID) {
            vm.smartcardids.push(smartCardID);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.balance.id !== null) {
                Balance.update(vm.balance, onSaveSuccess, onSaveError);
            } else {
                Balance.save(vm.balance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:balanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        $scope.method=['Card','Cash'];
    }
})();
