(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('Seat', Seat);

    Seat.$inject = ['$resource'];

    function Seat ($resource) {
        var resourceUrl =  'api/seats/:id';

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
