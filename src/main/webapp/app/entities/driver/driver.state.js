(function () {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('driver', {
                parent: 'entity',
                url: '/driver?page&sort&search',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_DRIVER'],
                    pageTitle: 'ticketingSystemApp.driver.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/driver/drivers.html',
                        controller: 'DriverController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('driver');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('driver-detail', {
                parent: 'driver',
                url: '/driver/{id}',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_DRIVER'],
                    pageTitle: 'ticketingSystemApp.driver.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/driver/driver-detail.html',
                        controller: 'DriverDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('driver');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Driver', function ($stateParams, Driver) {
                        return Driver.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'driver',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('driver-detail.edit', {
                parent: 'driver-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_DRIVER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/driver/driver-dialog.html',
                        controller: 'DriverDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Driver', function (Driver) {
                                return Driver.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('driver.new', {
                parent: 'driver',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_DRIVER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/driver/driver-dialog.html',
                        controller: 'DriverDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    driver_name: null,
                                    license: null,
                                    date_of_birth: null,
                                    driver_email: null,
                                    driver_phone_no: null,
                                    status: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('driver', null, {reload: 'driver'});
                    }, function () {
                        $state.go('driver');
                    });
                }]
            })
            .state('driver.edit', {
                parent: 'driver',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_DRIVER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/driver/driver-dialog.html',
                        controller: 'DriverDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Driver', function (Driver) {
                                return Driver.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('driver', null, {reload: 'driver'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('driver.delete', {
                parent: 'driver',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_DRIVER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/driver/driver-delete-dialog.html',
                        controller: 'DriverDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Driver', function (Driver) {
                                return Driver.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('driver', null, {reload: 'driver'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
