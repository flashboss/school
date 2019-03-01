module.factory('SchoolLoader', function(Loader, School, $route, $q) {
    return Loader.get(School, function() {
        return {
            realm : $route.current.params.realm,
            schoolId : $route.current.params.school
        }
    });
});
