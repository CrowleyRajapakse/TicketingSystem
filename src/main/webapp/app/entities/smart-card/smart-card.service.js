(function() {
    'use strict';
    angular
        .module('ticketingSystemApp')
        .factory('SmartCard', SmartCard);

    SmartCard.$inject = ['$resource', 'DateUtils'];

    function SmartCard ($resource, DateUtils) {
        var resourceUrl =  'api/smart-cards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.expiryDate = DateUtils.convertLocalDateFromServer(data.expiryDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expiryDate = DateUtils.convertLocalDateToServer(copy.expiryDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expiryDate = DateUtils.convertLocalDateToServer(copy.expiryDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
