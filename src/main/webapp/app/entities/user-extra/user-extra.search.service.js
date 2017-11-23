(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .factory('UserExtraSearch', UserExtraSearch);

    UserExtraSearch.$inject = ['$resource'];

    function UserExtraSearch($resource) {
        var resourceUrl =  'api/_search/user-extras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
