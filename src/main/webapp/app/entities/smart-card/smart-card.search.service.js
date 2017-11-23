(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('SmartCardSearch', SmartCardSearch);

    SmartCardSearch.$inject = ['$resource'];

    function SmartCardSearch($resource) {
        var resourceUrl =  'api/_search/smart-cards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
