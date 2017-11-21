(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('Vehicle', Vehicle);

    Vehicle.$inject = ['$resource'];

    function Vehicle ($resource) {
        var resourceUrl =  'api/vehicles/:id';

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
