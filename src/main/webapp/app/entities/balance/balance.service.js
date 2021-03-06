(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('Balance', Balance);

    Balance.$inject = ['$resource', 'DateUtils'];

    function Balance ($resource, DateUtils) {
        var resourceUrl =  'api/balances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
