(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('BalanceDetailController', BalanceDetailController);

    BalanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Balance', 'SmartCard'];

    function BalanceDetailController($scope, $rootScope, $stateParams, previousState, entity, Balance, SmartCard) {
        var vm = this;

        vm.balance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:balanceUpdate', function(event, result) {
            vm.balance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
