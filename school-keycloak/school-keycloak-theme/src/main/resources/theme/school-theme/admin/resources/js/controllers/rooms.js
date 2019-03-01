module.controller('RoomListCtrl', function($scope, realm, School, SchoolSearchState, Notifications, $route, Dialog) {
    
    $scope.init = function() {
        $scope.realm = realm;
        
        SchoolSearchState.query.realm = realm.realm;
        $scope.query = SchoolSearchState.query;
        $scope.query.briefRepresentation = 'true';
        
        if (!SchoolSearchState.isFirstSearch) $scope.searchQuery();
    };


    $scope.firstPage = function() {
        $scope.query.first = 0;
        $scope.searchQuery();
    }

    $scope.previousPage = function() {
        $scope.query.first -= parseInt($scope.query.max);
        if ($scope.query.first < 0) {
            $scope.query.first = 0;
        }
        $scope.searchQuery();
    }

    $scope.nextPage = function() {
        $scope.query.first += parseInt($scope.query.max);
        $scope.searchQuery();
    }

    $scope.searchQuery = function() {
        console.log("query.search: " + $scope.query.search);
        $scope.searchLoaded = false;

        $scope.schools = School.query($scope.query, function() {
            $scope.searchLoaded = true;
            $scope.lastSearch = $scope.query.search;
            SchoolSearchState.isFirstSearch = false;
        });
    };

    $scope.removeSchool = function(school) {
        Dialog.confirmDelete(school.id, 'school', function() {
        	school.$remove({
                realm : realm.realm,
                schoolId : school.id
            }, function() {
                $route.reload();
                
                if ($scope.schools.length === 1 && $scope.query.first > 0) {
                    $scope.previousPage();
                } 
                
                Notifications.success("The school has been deleted.");
            }, function() {
                Notifications.error("School couldn't be deleted");
            });
        });
    };
});


module.controller('SchoolTabCtrl', function($scope, $location, Dialog, Notifications, Current) {
    $scope.removeSchool = function() {
        Dialog.confirmDelete($scope.school.id, 'school', function() {
            $scope.school.$remove({
                realm : Current.realm.realm,
                schoolId : $scope.school.id
            }, function() {
                $location.url("/realms/" + Current.realm.realm + "/rooms");
                Notifications.success("The school has been deleted.");
            }, function() {
                Notifications.error("School couldn't be deleted");
            });
        });
    };
});

module.controller('SchoolDetailCtrl', function($scope, realm, school, School,
                                             Components,
                                             RequiredActions,
                                             $location, $http, Dialog, Notifications) {
    $scope.realm = realm;
    $scope.create = !school.id;
    $scope.editName = $scope.create || $scope.realm.editUsernameAllowed;

    if ($scope.create) {
        $scope.school = { enabled: true, attributes: {} }
    } else {
        if (!school.attributes) {
        	school.attributes = {}
        }
        convertAttributeValuesToString(school);


        $scope.school = angular.copy(school);
        console.log('realm brute force? ' + realm.bruteForceProtected)
    }

    $scope.changed = false; // $scope.create;
    if (school.requiredActions) {
        for (var i = 0; i < school.requiredActions.length; i++) {
            console.log("school require action: " + school.requiredActions[i]);
        }
    }
    // ID - Name map for required actions. IDs are enum names.
    RequiredActions.query({realm: realm.realm}, function(data) {
        $scope.schoolReqActionList = [];
        for (var i = 0; i < data.length; i++) {
            console.log("listed required action: " + data[i].name);
            if (data[i].enabled) {
                var item = data[i];
                $scope.schoolReqActionList.push(item);
            }
        }
    console.log("---------------------");
    console.log("ng-model: school.requiredActions=" + JSON.stringify($scope.school.requiredActions));
    console.log("---------------------");
    console.log("ng-repeat: schoolReqActionList=" + JSON.stringify($scope.schoolReqActionList));
    console.log("---------------------");
    });
    $scope.$watch('school', function() {
        if (!angular.equals($scope.school, school)) {
            $scope.changed = true;
        }
    }, true);

    $scope.save = function() {
        convertAttributeValuesToLists();

        if ($scope.create) {
            School.save({
                realm: realm.realm
            }, $scope.school, function (data, headers) {
                $scope.changed = false;
                convertAttributeValuesToString($scope.school);
                school = angular.copy($scope.school);
                var l = headers().location;

                console.debug("Location == " + l);

                var id = l.substring(l.lastIndexOf("/") + 1);


                $location.url("/realms/" + realm.realm + "/rooms/" + id);
                Notifications.success("The school has been created.");
            });
        } else {
        	School.update({
                realm: realm.realm,
                schoolId: $scope.school.id
            }, $scope.school, function () {
                $scope.changed = false;
                convertAttributeValuesToString($scope.school);
                school = angular.copy($scope.school);
                Notifications.success("Your changes have been saved to the school.");
            });
        }
    };

    function convertAttributeValuesToLists() {
        var attrs = $scope.school.attributes;
        for (var attribute in attrs) {
            if (typeof attrs[attribute] === "string") {
                var attrVals = attrs[attribute].split("##");
                attrs[attribute] = attrVals;
            }
        }
    }

    function convertAttributeValuesToString(school) {
        var attrs = school.attributes;
        for (var attribute in attrs) {
            if (typeof attrs[attribute] === "object") {
                var attrVals = attrs[attribute].join("##");
                attrs[attribute] = attrVals;
            }
        }
    }

    $scope.reset = function() {
        $scope.school = angular.copy(school);
        $scope.changed = false;
    };

    $scope.cancel = function() {
        $location.url("/realms/" + realm.realm + "/rooms");
    };

    $scope.addAttribute = function() {
        $scope.school.attributes[$scope.newAttribute.key] = $scope.newAttribute.value;
        delete $scope.newAttribute;
    }

    $scope.removeAttribute = function(key) {
        delete $scope.school.attributes[key];
    }
});