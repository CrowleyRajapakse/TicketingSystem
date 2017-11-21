(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('DriverSearch', DriverSearch);

    DriverSearch.$inject = ['$resource'];

    function DriverSearch($resource) {
        var resourceUrl =  'api/_search/drivers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
