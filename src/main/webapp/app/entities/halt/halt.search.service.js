(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('HaltSearch', HaltSearch);

    HaltSearch.$inject = ['$resource'];

    function HaltSearch($resource) {
        var resourceUrl =  'api/_search/halts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
