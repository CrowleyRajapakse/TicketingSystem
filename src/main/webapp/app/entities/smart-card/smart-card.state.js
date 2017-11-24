(function () {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('smart-card', {
                parent: 'entity',
                url: '/smart-card?page&sort&search',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_INSPECTOR', 'ROLE_PASSENGER'],
                    pageTitle: 'ticketingSystemApp.smartCard.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/smart-card/smart-cards.html',
                        controller: 'SmartCardController',
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
                        $translatePartialLoader.addPart('smartCard');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smart-card-detail', {
                parent: 'smart-card',
                url: '/smart-card/{id}',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_INSPECTOR'],
                    pageTitle: 'ticketingSystemApp.smartCard.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/smart-card/smart-card-detail.html',
                        controller: 'SmartCardDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smartCard');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmartCard', function ($stateParams, SmartCard) {
                        return SmartCard.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'smart-card',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('smart-card-detail.edit', {
                parent: 'smart-card-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_INSPECTOR']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/smart-card/smart-card-dialog.html',
                        controller: 'SmartCardDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['SmartCard', function (SmartCard) {
                                return SmartCard.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('smart-card.new', {
                parent: 'smart-card',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_INSPECTOR']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/smart-card/smart-card-dialog.html',
                        controller: 'SmartCardDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    smartCardID: null,
                                    expiryDate: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('smart-card', null, {reload: 'smart-card'});
                    }, function () {
                        $state.go('smart-card');
                    });
                }]
            })
            .state('smart-card.edit', {
                parent: 'smart-card',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_INSPECTOR']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/smart-card/smart-card-dialog.html',
                        controller: 'SmartCardDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['SmartCard', function (SmartCard) {
                                return SmartCard.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('smart-card', null, {reload: 'smart-card'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('smart-card.delete', {
                parent: 'smart-card',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_INSPECTOR']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/smart-card/smart-card-delete-dialog.html',
                        controller: 'SmartCardDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['SmartCard', function (SmartCard) {
                                return SmartCard.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('smart-card', null, {reload: 'smart-card'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
