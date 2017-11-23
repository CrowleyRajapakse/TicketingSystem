(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('ReservationSearch', ReservationSearch);

    ReservationSearch.$inject = ['$resource'];

    function ReservationSearch($resource) {
        var resourceUrl =  'api/_search/reservations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
