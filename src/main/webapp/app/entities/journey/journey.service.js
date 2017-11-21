(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('Journey', Journey);

    Journey.$inject = ['$resource', 'DateUtils'];

    function Journey ($resource, DateUtils) {
        var resourceUrl =  'api/journeys/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.departure_time = DateUtils.convertLocalDateFromServer(data.departure_time);
                        data.arrival_time = DateUtils.convertLocalDateFromServer(data.arrival_time);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.departure_time = DateUtils.convertLocalDateToServer(copy.departure_time);
                    copy.arrival_time = DateUtils.convertLocalDateToServer(copy.arrival_time);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.departure_time = DateUtils.convertLocalDateToServer(copy.departure_time);
                    copy.arrival_time = DateUtils.convertLocalDateToServer(copy.arrival_time);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
