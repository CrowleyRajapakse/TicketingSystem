(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('RouteDetailController', RouteDetailController);

    RouteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Route', 'Vehicle'];

    function RouteDetailController($scope, $rootScope, $stateParams, previousState, entity, Route, Vehicle) {
        var vm = this;

        vm.route = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ticketingSystemApp:routeUpdate', function(event, result) {
            vm.route = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
