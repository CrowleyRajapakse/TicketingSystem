(function () {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('journey', {
                parent: 'entity',
                url: '/journey?page&sort&search',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_PASSENGER'],
                    pageTitle: 'ticketingSystemApp.journey.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/journey/journeys.html',
                        controller: 'JourneyController',
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
                        $translatePartialLoader.addPart('journey');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('journey-detail', {
                parent: 'journey',
                url: '/journey/{id}',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_PASSENGER'],
                    pageTitle: 'ticketingSystemApp.journey.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/journey/journey-detail.html',
                        controller: 'JourneyDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('journey');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Journey', function ($stateParams, Journey) {
                        return Journey.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'journey',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('journey-detail.edit', {
                parent: 'journey-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_PASSENGER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/journey/journey-dialog.html',
                        controller: 'JourneyDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Journey', function (Journey) {
                                return Journey.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('journey.new', {
                parent: 'journey',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_PASSENGER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/journey/journey-dialog.html',
                        controller: 'JourneyDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    journey_id: null,
                                    departure: null,
                                    departure_time: null,
                                    arrival: null,
                                    arrival_time: null,
                                    amount: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('journey', null, {reload: 'journey'});
                    }, function () {
                        $state.go('journey');
                    });
                }]
            })
            .state('journey.edit', {
                parent: 'journey',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_PASSENGER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/journey/journey-dialog.html',
                        controller: 'JourneyDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Journey', function (Journey) {
                                return Journey.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('journey', null, {reload: 'journey'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('journey.delete', {
                parent: 'journey',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_PASSENGER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/journey/journey-delete-dialog.html',
                        controller: 'JourneyDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Journey', function (Journey) {
                                return Journey.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('journey', null, {reload: 'journey'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
