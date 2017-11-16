(function() {
    'use strict';

    angular
        .module('ticketingSystemApp')
        .config(googleMapsConfig);

    googleMapsConfig.$inject = ['uiGmapGoogleMapApiProvider'];

    function googleMapsConfig(uiGmapGoogleMapApiProvider) {
        uiGmapGoogleMapApiProvider.configure({
            key: 'AIzaSyCsuJXhJqaGirug_Mfn0lD4fu20Q7l6ELw',
            v: '3.28',
            libraries: 'weather,geometry,visualization,places'
        });
    }
})();
