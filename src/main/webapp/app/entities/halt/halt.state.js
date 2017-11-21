(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('halt', {
            parent: 'entity',
            url: '/halt?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ticketingSystemApp.halt.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/halt/halts.html',
                    controller: 'HaltController',
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
                    $translatePartialLoader.addPart('halt');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('halt-detail', {
            parent: 'halt',
            url: '/halt/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ticketingSystemApp.halt.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/halt/halt-detail.html',
                    controller: 'HaltDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('halt');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Halt', function($stateParams, Halt) {
                    return Halt.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'halt',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('halt-detail.edit', {
            parent: 'halt-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/halt/halt-dialog.html',
                    controller: 'HaltDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Halt', function(Halt) {
                            return Halt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('halt.new', {
            parent: 'halt',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/halt/halt-dialog.html',
                    controller: 'HaltDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                start_halt: null,
                                end_halt: null,
                                halt_distance: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('halt', null, { reload: 'halt' });
                }, function() {
                    $state.go('halt');
                });
            }]
        })
        .state('halt.edit', {
            parent: 'halt',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/halt/halt-dialog.html',
                    controller: 'HaltDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Halt', function(Halt) {
                            return Halt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('halt', null, { reload: 'halt' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('halt.delete', {
            parent: 'halt',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/halt/halt-delete-dialog.html',
                    controller: 'HaltDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Halt', function(Halt) {
                            return Halt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('halt', null, { reload: 'halt' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
