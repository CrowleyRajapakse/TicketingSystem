(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('VehicleSearch', VehicleSearch);

    VehicleSearch.$inject = ['$resource'];

    function VehicleSearch($resource) {
        var resourceUrl =  'api/_search/vehicles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
