var app = angular.module("UserManagement", ['ui.bootstrap']);

//Controller Part
app.controller("UserManagementController", function($scope, $http) {
	
    //Initialize page with default data which is blank
    $scope.tasks = [];
    $scope.form = {
        id : -1,
        titulo : "",
		status : "",
		descricao : "",
		descricao : "",
		criacao : "",
		edicao : "",
		remocao : "",
		conclusao : ""
    };

    //Now load the data from server
    _refreshPageData();

    //HTTP POST/PUT methods for add/edit task
    $scope.submitTask = function() {

        var method = "";
        var url = "";
        if ($scope.form.id == -1) {
            //Id is absent so add task - POST operation
            method = "POST";
            url = 'http://localhost:8080/TaskToDO/tasks';
        } else {
            //If Id is present, it's edit operation - PUT operation
            method = "PUT";
            url = 'http://localhost:8080/TaskToDO/tasks/' + $scope.form.id;
        }

        $http({
            method : method,
            url : url,
            data : angular.toJson($scope.form),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( _success, _error );
    };

    //HTTP DELETE- delete task by Id
    $scope.removeTask = function(task) {
        $http({
            method : 'DELETE',
            url : 'http://localhost:8080/TaskToDO/tasks/' + task.id
        }).then(_success, _error);
    };

    //In case of edit task, populate form with task data
    $scope.editTask = function(task) {
    	$scope.form.titulo = task.titulo;
    	$scope.form.status = task.status;
    	$scope.form.descricao = task.descricao;
        $scope.form.id = task.id;
    };

    /* Private Methods */
    //HTTP GET- get all tasks collection
    function _refreshPageData() {
    	$http({
            method : 'GET',
            url : 'http://localhost:8080/TaskToDO/tasks'
        }).then(function successCallback(response) {
        	 console.log(response.data.tasks);
            $scope.tasks = response.data.tasks;
        }, function errorCallback(response) {
        	console.log("Atenção error");
            console.log(response.statusText);
        });
    }

    function _success(response) {
        _refreshPageData();
        _clearForm()
    }

    function _error(response) {
        console.log(response.statusText);
    }

    //Clear the form
    function _clearForm() {
    	$scope.form.titulo = "";
    	$scope.form.status = "";
    	$scope.form.descricao = "";
        $scope.form.id = -1;
    };
});