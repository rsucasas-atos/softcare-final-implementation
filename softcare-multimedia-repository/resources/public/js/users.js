angular.module('angular-app')

.controller('usersController', function($rootScope, $scope, $log, $http, $localStorage) {

    // selected user id
    $scope.selectedID = null;
    // show users list
    $scope.show_users_list = false;
    // show new user form
    $scope.new_user = false;
    // form disabled
    $scope.formEnabled = false;
    // FORM
    $scope.formData = {
        name: "",
        username: "",
        password: "",
        rol: "",
        location: ""
    };
    var originalForm = angular.copy($scope.formData2);


    ///////////////////////////////////////////////////////////////////////////
    // 1st LOADING / RELOAD ... ///////
    $rootScope.updateClientIp();
    //         Check user-token
    $rootScope.loading = true;
    $http.get($rootScope.approot + '/api/user/validate').success(function(data) {
        if ((data != null) && (data.code == 1)) {
            $rootScope.updateToken(data);

            // Load all users
            $http.get($rootScope.approot + '/api/users').success(function(data) {
                $scope.users = data;
                $scope.show_users_list = true;
            })
            .error(function(data) {
                $rootScope.showmessage('error', 'Error loading users', data);
            });
        }
        else if ((data != null) && (data.code == 0))
            $rootScope.logout();
        else
            $rootScope.showmessage('error', 'Error validating user: ' + data.content[0].message, data);
    })
    .error(function(data) {
        $rootScope.showmessage('error', 'Error validating user', data);
    })
    .finally(function() {
        $rootScope.loading = false;
    });


    ///////////////////////////////////////////////////////////////////////////
    // FUNCTION: enableForm
    $scope.enableForm = function() {
        $scope.formData = angular.copy(originalForm);
        $scope.selectedID = null;
        $scope.formEnabled = true;
        $scope.new_user = true;
    };

    // FUNCTION: cancel and close form
    $scope.cancel = function() {
        $scope.clearForm();
        $scope.formEnabled = false;
        $scope.new_user = false;
    };


    // FUNCTION: clear form
    $scope.clearForm = function() {
        $scope.formData = angular.copy(originalForm);
        $scope.selectedID = null;
        $scope.usersForm.$setPristine();
        $scope.usersForm.$setValidity();
    };


    // FUNCTION: Show data from user
    $scope.showUserData = function(id) {
        $rootScope.loading = true;

        console.log(id);
        try
        {
            id = id.replace(/^"(.*)"$/, '$1');
        }
        catch (err) {
            console.log(err);
        }
        //console.log(_id);

        $http.get($rootScope.approot + '/api/users/' + id).success(function(data) {
            if ((data != null) && (data.code == 1)) {
                $rootScope.updateToken(data);

                $scope.formData = data.content;
                $scope.selectedID = id;
            }
            else if ((data != null) && (data.code == 0))
                $rootScope.logout();
            else
                $rootScope.showmessage('error', 'Error getting data from user: ' + data.content[0].message, data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error getting data from user', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });

        $scope.formEnabled = true;
    };


    // FUNCTION: Creates new user
    $scope.createUser = function() {
        $rootScope.loading = true;

        $http.post($rootScope.approot + '/api/users', $scope.formData).success(function(data) {
            if ((data != null) && (data.code == 1))
            {
                $rootScope.updateToken(data);

                $scope.formData = {};
                $scope.users = data.content;
                $scope.cancel();
                $scope.formEnabled = false;

                $rootScope.showmessage('success', 'User created', data);
            }
            else if ((data != null) && (data.code == 0))
                $rootScope.logout();
            else
                $rootScope.showmessage('error', 'Error creating user', data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error creating new user', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });
    };


    // FUNCTION: Updates user
    $scope.updateUser = function() {
        if ($scope.selectedID == null) {
            $rootScope.showmessage('error', 'Error updating user', '>> Error: $scope.selectedID == null');
        }
        else {
            $rootScope.loading = true;


            console.log($scope.selectedID);
            try
            {
                $scope.selectedID = $scope.selectedID.replace(/^"(.*)"$/, '$1');
            }
            catch (err) {
                console.log(err);
            }

            $http.put($rootScope.approot + '/api/users/' + $scope.selectedID, $scope.formData).success(function(data) {
                if ((data != null) && (data.code == 1))
                {
                    $rootScope.updateToken(data);

                    $scope.formData = {};
                    $scope.users = data.content;
                    $scope.cancel();
                    $scope.formEnabled = false;

                    $rootScope.showmessage('success', 'User updated', data);
                }
                else if ((data != null) && (data.code == 0))
                    $rootScope.logout();
                else
                    $rootScope.showmessage('error', 'Error updating user: ' + data.content[0].message, data);
            })
            .error(function(data) {
                $rootScope.showmessage('error', 'Error updating user', data);
            })
            .finally(function() {
                $rootScope.loading = false;
            });
        }
    };


    // FUNCTION: Deletes user by id
    $scope.deleteUser = function(id) {
        $rootScope.loading = true;

        console.log(id);
        try
        {
            id = id.replace(/^"(.*)"$/, '$1');
        }
        catch (err) {
            console.log(err);
        }

        $http.delete($rootScope.approot + '/api/users/' + id).success(function(data) {
            if ((data != null) && (data.code == 1)) {
                $rootScope.updateToken(data);

                $scope.users = data.content;
                $scope.formEnabled = false;

                $rootScope.showmessage('success', 'User deleted', data);
            }
            else
                $rootScope.showmessage('error', 'Error deleting user: ' + data.content[0].message, data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error deleting user', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });

        $scope.formData = {};
        $scope.selectedID = null;
    };

});
