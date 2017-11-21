(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('HaltDetailController', HaltDetailController);

    HaltDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Halt', 'Route'];

    function HaltDetailController($scope, $rootScope, $stateParams, previousState, entity, Halt, Route) {
        var vm = this;

        vm.halt = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:haltUpdate', function(event, result) {
            vm.halt = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
