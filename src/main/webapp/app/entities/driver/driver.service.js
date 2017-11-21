(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('Driver', Driver);

    Driver.$inject = ['$resource', 'DateUtils'];

    function Driver ($resource, DateUtils) {
        var resourceUrl =  'api/drivers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_of_birth = DateUtils.convertLocalDateFromServer(data.date_of_birth);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_of_birth = DateUtils.convertLocalDateToServer(copy.date_of_birth);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_of_birth = DateUtils.convertLocalDateToServer(copy.date_of_birth);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
