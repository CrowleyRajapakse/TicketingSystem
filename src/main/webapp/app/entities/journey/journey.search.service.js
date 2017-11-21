(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('JourneySearch', JourneySearch);

    JourneySearch.$inject = ['$resource'];

    function JourneySearch($resource) {
        var resourceUrl =  'api/_search/journeys/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
