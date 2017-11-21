(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('Route', Route);

    Route.$inject = ['$resource'];

    function Route ($resource) {
        var resourceUrl =  'api/routes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
