'use strict';

describe('Controller Tests', function() {

    describe('Vehicle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVehicle, MockDriver, MockRoute, MockSeat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVehicle = jasmine.createSpy('MockVehicle');
            MockDriver = jasmine.createSpy('MockDriver');
            MockRoute = jasmine.createSpy('MockRoute');
            MockSeat = jasmine.createSpy('MockSeat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Vehicle': MockVehicle,
                'Driver': MockDriver,
                'Route': MockRoute,
                'Seat': MockSeat
            };
            createController = function() {
                $injector.get('$controller')("VehicleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ticketingSystemApp:vehicleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
