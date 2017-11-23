(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('SmartCardDetailController', SmartCardDetailController);

    SmartCardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SmartCard'];

    function SmartCardDetailController($scope, $rootScope, $stateParams, previousState, entity, SmartCard) {
        var vm = this;

        vm.smartCard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:smartCardUpdate', function(event, result) {
            vm.smartCard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
