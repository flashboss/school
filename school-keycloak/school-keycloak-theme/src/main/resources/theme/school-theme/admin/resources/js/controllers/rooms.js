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

    $scope.removeSchool = function(user) {
        Dialog.confirmDelete(user.id, 'school', function() {
            user.$remove({
                realm : realm.realm,
                schoolId : user.id
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
        Dialog.confirmDelete($scope.user.id, 'school', function() {
            $scope.user.$remove({
                realm : Current.realm.realm,
                schoolId : $scope.user.id
            }, function() {
                $location.url("/realms/" + Current.realm.realm + "/rooms");
                Notifications.success("The school has been deleted.");
            }, function() {
                Notifications.error("School couldn't be deleted");
            });
        });
    };
});

module.controller('SchoolDetailCtrl', function($scope, realm, user, School,
                                             Components,
                                             RequiredActions,
                                             UserStorageOperations,
                                             $location, $http, Dialog, Notifications) {
    $scope.realm = realm;
    $scope.create = !user.id;
    $scope.editUsername = $scope.create || $scope.realm.editUsernameAllowed;

    if ($scope.create) {
        $scope.user = { enabled: true, attributes: {} }
    } else {
        if (!user.attributes) {
            user.attributes = {}
        }
        convertAttributeValuesToString(user);


        $scope.user = angular.copy(user);
        if(user.origin) {
            if ($scope.access.viewRealm) {
                Components.get({realm: realm.realm, componentId: user.origin}, function (link) {
                    $scope.originName = link.name;
                    $scope.originLink = "#/realms/" + realm.realm + "/user-storage/providers/" + link.providerId + "/" + link.id;
                })
            }
            else {
                // KEYCLOAK-4328
                UserStorageOperations.simpleName.get({realm: realm.realm, componentId: user.origin}, function (link) {
                    $scope.originName = link.name;
                    $scope.originLink = $location.absUrl();
                })
             }
        } else {
            console.log("origin is null");
        }
        console.log('realm brute force? ' + realm.bruteForceProtected)
    }

    $scope.changed = false; // $scope.create;
    if (user.requiredActions) {
        for (var i = 0; i < user.requiredActions.length; i++) {
            console.log("user require action: " + user.requiredActions[i]);
        }
    }
    // ID - Name map for required actions. IDs are enum names.
    RequiredActions.query({realm: realm.realm}, function(data) {
        $scope.userReqActionList = [];
        for (var i = 0; i < data.length; i++) {
            console.log("listed required action: " + data[i].name);
            if (data[i].enabled) {
                var item = data[i];
                $scope.userReqActionList.push(item);
            }
        }
    console.log("---------------------");
    console.log("ng-model: user.requiredActions=" + JSON.stringify($scope.user.requiredActions));
    console.log("---------------------");
    console.log("ng-repeat: userReqActionList=" + JSON.stringify($scope.userReqActionList));
    console.log("---------------------");
    });
    $scope.$watch('user', function() {
        if (!angular.equals($scope.user, user)) {
            $scope.changed = true;
        }
    }, true);

    $scope.save = function() {
        convertAttributeValuesToLists();

        if ($scope.create) {
            School.save({
                realm: realm.realm
            }, $scope.user, function (data, headers) {
                $scope.changed = false;
                convertAttributeValuesToString($scope.user);
                user = angular.copy($scope.user);
                var l = headers().location;

                console.debug("Location == " + l);

                var id = l.substring(l.lastIndexOf("/") + 1);


                $location.url("/realms/" + realm.realm + "/rooms/" + id);
                Notifications.success("The school has been created.");
            });
        } else {
        	School.update({
                realm: realm.realm,
                schoolId: $scope.user.id
            }, $scope.user, function () {
                $scope.changed = false;
                convertAttributeValuesToString($scope.user);
                user = angular.copy($scope.user);
                Notifications.success("Your changes have been saved to the user.");
            });
        }
    };

    function convertAttributeValuesToLists() {
        var attrs = $scope.user.attributes;
        for (var attribute in attrs) {
            if (typeof attrs[attribute] === "string") {
                var attrVals = attrs[attribute].split("##");
                attrs[attribute] = attrVals;
            }
        }
    }

    function convertAttributeValuesToString(user) {
        var attrs = user.attributes;
        for (var attribute in attrs) {
            if (typeof attrs[attribute] === "object") {
                var attrVals = attrs[attribute].join("##");
                attrs[attribute] = attrVals;
            }
        }
    }

    $scope.reset = function() {
        $scope.user = angular.copy(user);
        $scope.changed = false;
    };

    $scope.cancel = function() {
        $location.url("/realms/" + realm.realm + "/rooms");
    };

    $scope.addAttribute = function() {
        $scope.user.attributes[$scope.newAttribute.key] = $scope.newAttribute.value;
        delete $scope.newAttribute;
    }

    $scope.removeAttribute = function(key) {
        delete $scope.user.attributes[key];
    }
});