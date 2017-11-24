(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .controller('GoogleMapsController', GoogleMapsController);

    GoogleMapsController.$inject = ['$scope', '$templateCache', 'uiGmapGoogleMapApi'];

    function GoogleMapsController($scope, $templateCache, uiGmapGoogleMapApi) {
        $scope.markersDescription = [];
        $scope.markerLocator = {};

        $templateCache.put('searchbox.tpl.html', '<input type="text" class="pac-controls form-control" name="title" placeholder="Search" id="pac-input" >');

        var marker1 = {
            id: 1,
            latitude: 6.913676,
            longitude: 79.8773048,
            description: 'I am marker 1',
            options: {
                visible: true
            }
        };
        $scope.markersDescription.push(marker1);
        var marker2 = {
            id: 2,
            latitude: 6.913976,
            longitude: 79.8773448,
            description: 'I am marker 2 and I am happy :)',
            options: {
                visible: true
            }
        };
        $scope.markersDescription.push(marker2);

        uiGmapGoogleMapApi.then(function (maps) {
            maps.visualRefresh = true;
            $scope.defaultBounds = new google.maps.LatLngBounds(
                new google.maps.LatLng(9.8731, 81.7718),
                new google.maps.LatLng(6.1731, 79.7718)
            );
            $scope.directionsDisplay = new google.maps.DirectionsRenderer();
            $scope.directionsService = new google.maps.DirectionsService();
            $scope.geocoder = new google.maps.Geocoder();

            $scope.map.bounds = {
                northeast: {
                    latitude: $scope.defaultBounds.getNorthEast().lat(),
                    longitude: $scope.defaultBounds.getNorthEast().lng()
                },
                southwest: {
                    latitude: $scope.defaultBounds.getSouthWest().lat(),
                    longitude: -$scope.defaultBounds.getSouthWest().lng()

                }
            };

            $scope.searchbox.options.bounds = new google.maps.LatLngBounds(
                $scope.defaultBounds.getNorthEast(),
                $scope.defaultBounds.getSouthWest()
            );
        });

        $scope.windowOptions = {
            visible: true
        };

        $scope.map = {
            control: {},
            center: {
                latitude: 6.913676,
                longitude: 79.8773048,
            },
            zoom: 12,
            dragging: false,
            bounds: {},
            markersDescription: [],
            idkey: 'id',
            markers: $scope.markersDescription,
            events: {
                idle: function (map) {

                },
                dragend: function (map) {
                    //update the search box bounds after dragging the map
                    var bounds = map.getBounds();
                    var ne = bounds.getNorthEast();
                    var sw = bounds.getSouthWest();
                    $scope.searchbox.options.bounds = new google.maps.LatLngBounds(sw, ne);
                }
            }
        };

        // directions object -- with defaults
        $scope.directions = {
            origin: "Matara, Sri Lanka",
            destination: "Colombo, Sri Lanka",
            showList: false
        };

        // get directions using google maps api
        $scope.getDirections = function () {
            var request = {
                origin: $scope.directions.origin,
                destination: $scope.directions.destination,
                travelMode: google.maps.DirectionsTravelMode.DRIVING,
                transitOptions: {
                    departureTime: new Date(1337675679473),
                    modes: ['BUS'],
                    routingPreference: 'FEWER_TRANSFERS'
                }

            };
            $scope.directionsService.route(request, function (response, status) {
                if (status === google.maps.DirectionsStatus.OK) {
                    $scope.directionsDisplay.setDirections(response);
                    $scope.directionsDisplay.setMap($scope.map.control.getGMap());
                    $scope.directionsDisplay.setPanel(document.getElementById('directionsList'));
                    $scope.directions.showList = true;
                } else {
                    alert('Google route unsuccesfull!');
                }
            });
        }

        $scope.searchbox = {
            template: 'searchbox.tpl.html',
            options: {
                autocomplete: true,
                types: [],
                componentRestrictions: {country: 'lk'}
            },
            events: {
                place_changed: function (autocomplete) {

                    var place = autocomplete.getPlace()

                    if (place.address_components) {

                        var bounds = new google.maps.LatLngBounds();

                        var marker = {
                            id: -1,
                            option: {
                                icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
                            },
                            name: place.address_components[0].long_name,
                            coords: {
                                latitude: place.geometry.location.lat(),
                                longitude: place.geometry.location.lng()
                            }
                        };

                        bounds.extend(place.geometry.location);

                        $scope.map.bounds = {
                            northeast: {
                                latitude: bounds.getNorthEast().lat() + 0.01,
                                longitude: bounds.getNorthEast().lng() + 0.01
                            },
                            southwest: {
                                latitude: bounds.getSouthWest().lat() - 0.01,
                                longitude: bounds.getSouthWest().lng() - 0.01
                            }
                        };

                        var newMarkers = [];
                        for (var index = 0; index < $scope.markersDescription.length; index++) {
                            newMarkers.push($scope.markersDescription[index]);
                        }
                        $scope.map.markersDescription = newMarkers;
                        $scope.markerLocator = marker;
                    } else {
                        console.log("do something else with the search string: " + place.name);
                    }
                }
            }
        };

        $scope.clickEventsObject = {
            click: markerClick,
            mouseover: markerMouseOver,
            mouseout: markerMouseOut
        };

        function markerClick(gMarker, eventName, model) {
            $scope.display = model;
        };

        function markerMouseOver(gMarker, eventName, model) {
            model.show = true;
            $scope.$apply();
        };

        function markerMouseOut(gMarker, eventName, model) {
            model.show = false;
            $scope.$apply();
        };

        $scope.load = function () {

        };

        $scope.load();
    }
})();
