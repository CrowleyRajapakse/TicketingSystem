(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('RouteSearch', RouteSearch);

    RouteSearch.$inject = ['$resource'];

    function RouteSearch($resource) {
        var resourceUrl =  'api/_search/routes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
