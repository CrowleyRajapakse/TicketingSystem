(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('SeatSearch', SeatSearch);

    SeatSearch.$inject = ['$resource'];

    function SeatSearch($resource) {
        var resourceUrl =  'api/_search/seats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
