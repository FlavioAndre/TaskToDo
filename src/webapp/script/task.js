var app = angular.module('tasks', ['ngResource', 'ngGrid', 'ui.bootstrap']);

// Create a controller with name tasksListController to bind to the grid section.
app.controller('tasksListController', function ($scope, $rootScope, taskService) {
    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.tasks = {currentPage: 1};

    $scope.gridOptions = {
        data: 'tasks.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,

        columnDefs: [
            { field: 'id', displayName: 'Id' },
            { field: 'titulo', displayName: 'Titulo' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],

        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('taskSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        var listTasksArgs = {
            page: $scope.tasks.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };

        taskService.get(listTasksArgs, function (data) {
            $scope.tasks = data;
        })
    };

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteTask', row.entity.id);
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo', function () {
        $scope.tasks = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a task is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
});

// Create a controller with name tasksFormController to bind to the form section.
app.controller('tasksFormController', function ($scope, $rootScope, taskService) {
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.task = null;
        // For some reason, I was unable to clear field values with type 'url' if the value is invalid.
        // This is a workaroud. Needs proper investigation.
        document.getElementById('imageUrl').value = null;
        // Resets the form validation state.
        $scope.taskForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };

    // Calls the rest method to save a task.
    $scope.updateTask = function () {
        taskService.save($scope.task).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('taskSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // Picks up the event broadcasted when the task is selected from the grid and perform the task load by calling
    // the appropiate rest service.
    $scope.$on('taskSelected', function (event, id) {
        $scope.task = taskService.get({id: id});
    });

    // Picks us the event broadcasted when the task is deleted from the grid and perform the actual task delete by
    // calling the appropiate rest service.
    $scope.$on('deleteTask', function (event, id) {
        taskService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('taskDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('taskSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('taskDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

// Service that provides tasks operations
app.factory('taskService', function ($resource) {
    return $resource('resources/tasks/:id');
});