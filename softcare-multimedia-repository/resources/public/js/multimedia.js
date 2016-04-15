angular.module('angular-app')

.controller('multimediaController', function($rootScope, $scope, $log, $http, showAlertSrvc) {
    // FUNCTION: get elements by type generic function
    $scope.getElementsByType = function(api_method, type_name) {
        $rootScope.loading = true;
        $scope.docs = [];
        $http.get($rootScope.approot + api_method).success(function(data) {
            if ((data != null) && (data.code == 1)) {
                $rootScope.updateToken(data);

                $scope.docs = data.content;
                $scope.mcontent.type = type_name;
            }
            else if ((data != null) && (data.code == 0))
                $rootScope.logout();
            else
                $rootScope.showmessage('error', 'Error getting data: ' + data.content[0].message, data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error getting data', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });
    };


    ///////////////////////////////////////////////////////////////////////////
    // INITIAL CONFIGURATION //////////

    // FORM
    $scope.formDataMedia = {
        name: "",
        desc: "",
        url: "",
        type: "",
        tags: "",
        stored: ""
    };
    var originalForm = angular.copy($scope.formDataMedia);

    // form disabled
    $scope.formEnabled = false;

    $scope.selectedID = null;
    $scope.new_doc = false;

    // scope vars:
    $scope.mcontent= {
        type: 'ALL'
    };
    $scope.docs = [];

    // pagination / sort
    $scope.currentPage = 1;
    $scope.pageSize = 5;
    $scope.orderByField = 'name';
    $scope.reverseSort = false;


    ///////////////////////////////////////////////////////////////////////////
    // 1st LOADING / RELOAD ... ///////
    $rootScope.updateClientIp();
    //         Check user-token
    $rootScope.loading = true;
    $http.get($rootScope.approot + '/api/user/validate').success(function(data) {
        if ((data != null) && (data.code == 1)) {
            $rootScope.updateToken(data);

            // Load all videos
            $http.get($rootScope.approot + '/api/videos').success(function(data) {
                $scope.docs = data.content;
                $scope.mcontent.type = "VIDEOS";
            })
            .error(function(data) {
                $scope.showmessage('error', 'Error getting videos', data);
            });
        }
        else if ((data != null) && (data.code == 0))
            $rootScope.logout();
        else
            $scope.showmessage('error', 'Error getting videos: ' + data.content[0].message, data);
    })
    .error(function(data) {
        $scope.showmessage('error', 'Error validating user', data);
    })
    .finally(function() {
        $rootScope.loading = false;
    });


    ///////////////////////////////////////////////////////////////////////////
    // FUNCTION:
    $scope.enableForm = function() {
        $scope.formDataMedia = angular.copy(originalForm);
        $scope.selectedID = null;
        $scope.formEnabled = true;
        $scope.new_doc = true;
        $scope.formDataMedia.type = "video";
    };


    // FUNCTION: cancel and close form
    $scope.cancel = function() {
        $scope.clearForm();
        $scope.formEnabled = false;
        $scope.new_doc = false;
    };


    // FUNCTION: clear form
    $scope.clearForm = function() {
        $scope.formDataMedia = angular.copy(originalForm);
        $scope.selectedID = null;
        //$scope.usersForm.$setPristine();
        //$scope.usersForm.$setValidity();
    };


    // FUNCTION: Creates new document
    $scope.createDocument = function() {
        $rootScope.loading = true;

        var valStored = ($scope.formDataMedia.stored === "true");
        $scope.formDataMedia.name = $scope.formDataMedia.name.replace(/\s/g, '_');

        $http.post($rootScope.approot + '/api/documents', $scope.formDataMedia).success(function(data) {
            if ((data != null) && (data.code == 1))
            {
                $log.debug(data);
                $rootScope.updateToken(data);

                $scope.formDataMedia = {};
                $scope.docs = data.content;
                $scope.cancel();
                $scope.formEnabled = false;

                $rootScope.showmessage('success', 'Document created / uploaded', data);
            }
            else
                $rootScope.showmessage('error', 'Error creating document: ' + data.content[0].message, data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error creating document', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });
    };


    // FUNCTION:
    $scope.showDocumentData = function(id) {
        $rootScope.loading = true;

        $http.get($rootScope.approot + '/api/documents/' + id).success(function(data) {
            if ((data != null) && (data.code == 1)) {
                $rootScope.updateToken(data);

                $scope.selectedID = id;
                $scope.formDataMedia = data.content;

                $scope.formEnabled = true;
            }
            else if ((data != null) && (data.code == 0))
                $rootScope.logout();
            else
                $rootScope.showmessage('error', 'Error getting data from document: ' + data.content[0].message, data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error getting data from document', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });
    }


    // FUNCTION:
    $scope.updateDocument = function() {
        if ($scope.selectedID == null) {
            $rootScope.showmessage('error', 'Error updating document', '>> Error: $scope.selectedID == null');
        }
        else {
            $rootScope.loading = true;

            $http.put($rootScope.approot + '/api/documents/' + $scope.selectedID, $scope.formDataMedia).success(function(data) {
                if ((data != null) && (data.code == 1))
                {
                    $rootScope.updateToken(data);

                    $scope.formDataMedia = {};
                    $scope.docs = data.content;
                    $scope.cancel();
                    $scope.formEnabled = false;

                    $rootScope.showmessage('success', 'Document updated', data);
                }
                else if ((data != null) && (data.code == 0))
                    $rootScope.logout();
                else
                    $rootScope.showmessage('error', 'Error updating document: ' + data.content[0].message, data);
            })
            .error(function(data) {
                $rootScope.showmessage('error', 'Error updating document', data);
            })
            .finally(function() {
                $rootScope.loading = false;
            });
        }
    }


    // FUNCTION:
    $scope.deleteDocument = function(id) {
        $rootScope.loading = true;

        $http.delete($rootScope.approot + '/api/documents/' + id).success(function(data) {
            if ((data != null) && (data.code == 1)) {
                $rootScope.updateToken(data);

                $scope.docs = data.content;
                $scope.formEnabled = false;

                $scope.formDataMedia = {};
                $scope.selectedID = null;

                $rootScope.showmessage('success', 'Document deleted', data);
            }
            else if ((data != null) && (data.code == 0))
                $rootScope.logout();
            else
                $rootScope.showmessage('error', 'Error getting deleting document: ' + data.content[0].message, data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error getting deleting document', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });
    }


    // FUNCTION: get all documents
    $scope.getAll = function(id) {
        $rootScope.loading = true;
        $scope.docs = [];

        $http.get($rootScope.approot + '/api/records/all').success(function(data) {
            if ((data != null) && (data.code == 1)) {
                $rootScope.updateToken(data);

                $scope.docs = data.content;
                $scope.mcontent.type = "ALL";
            }
            else if ((data != null) && (data.code == 0))
                $rootScope.logout();
            else
                $rootScope.showmessage('error', 'Error getting data: ' + data.content[0].message, data);
        })
        .error(function(data) {
            $rootScope.showmessage('error', 'Error getting data from documents', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });
    };


    // FUNCTION: getVideos
    $scope.getVideos = function(id) {
        $scope.getElementsByType("/api/videos", "VIDEOS");
    }


    // FUNCTION: getImages
    $scope.getImages = function(id) {
        $scope.getElementsByType("/api/images", "IMAGES");
    }


    // FUNCTION: getBooks
    $scope.getBooks = function(id) {
        $scope.getElementsByType("/api/books", "BOOKS");
    }


    // FUNCTION: getOthers
    $scope.getOthers = function(id) {
        $scope.getElementsByType("/api/others", "OTHER");
    }

     // FUNCTION: getMusic
    $scope.getMusic = function(id) {
        $scope.getElementsByType("/api/music", "MUSIC");
    }
})

.filter('custom', function() {
    return function(input, search) {
        if (!input) return input;
        if (!search) return input;
        var expected = ('' + search).toLowerCase();
        var result = {};
        angular.forEach(input, function(value, key) {
            var actual = ('' + value).toLowerCase();
            if (actual.indexOf(expected) !== -1) {
                result[key] = value;
            }
        });
        return result;
    }
});
