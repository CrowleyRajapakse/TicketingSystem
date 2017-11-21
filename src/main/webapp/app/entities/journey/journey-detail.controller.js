(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('JourneyDetailController', JourneyDetailController);

    JourneyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Journey', 'Vehicle'];

    function JourneyDetailController($scope, $rootScope, $stateParams, previousState, entity, Journey, Vehicle) {
        var vm = this;

        vm.journey = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:journeyUpdate', function(event, result) {
            vm.journey = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
