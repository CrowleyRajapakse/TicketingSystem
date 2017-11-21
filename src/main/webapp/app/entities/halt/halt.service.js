(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('Halt', Halt);

    Halt.$inject = ['$resource'];

    function Halt ($resource) {
        var resourceUrl =  'api/halts/:id';

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
