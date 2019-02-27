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
    return $resource(authUrl + '/admin/realms/:realm/users/:schoolId', {
        realm : '@realm',
        schoolId : '@schoolId'
    }, {
        update : {
            method : 'PUT'
        }
    });
});