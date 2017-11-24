(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('BalanceSearch', BalanceSearch);

    BalanceSearch.$inject = ['$resource'];

    function BalanceSearch($resource) {
        var resourceUrl =  'api/_search/balances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
