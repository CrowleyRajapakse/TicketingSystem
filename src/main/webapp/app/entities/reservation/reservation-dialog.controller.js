(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('ReservationDialogController', ReservationDialogController);

    ReservationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Reservation', 'Vehicle', 'Seat'];

    function ReservationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Reservation, Vehicle, Seat) {
        var vm = this;

        vm.reservation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.vehicles = Vehicle.query();
        vm.seats = Seat.query({filter: 'reservation-is-null'});
        $q.all([vm.reservation.$promise, vm.seats.$promise]).then(function() {
            if (!vm.reservation.seatId) {
                return $q.reject();
            }
            return Seat.get({id : vm.reservation.seatId}).$promise;
        }).then(function(seat) {
            vm.seats.push(seat);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reservation.id !== null) {
                Reservation.update(vm.reservation, onSaveSuccess, onSaveError);
            } else {
                Reservation.save(vm.reservation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ticketingSystemApp:reservationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
