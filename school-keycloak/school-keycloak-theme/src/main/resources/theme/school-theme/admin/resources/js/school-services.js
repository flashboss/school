'use strict';

var module = angular.module('school.services', [ 'ngResource', 'ngRoute' ]);

module.service('SchoolSearchState', function() {
    this.isFirstSearch = true;
    this.query = {
        max : 20,
        first : 0
    };
});

module.factory('School', function($resource) {
    return $resource(authUrl + '/admin/realms/:realm/users/:userId', {
        realm : '@realm',
        userId : '@userId'
    }, {
        update : {
            method : 'PUT'
        }
    });
});