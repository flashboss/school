'use strict';

var module = angular.module('school.services', [ 'ngResource', 'ngRoute' ]);

module.service('SchoolSearchState', function() {
    this.isFirstSearch = true;
    this.query = {
        max : 20,
        first : 0
    };
});