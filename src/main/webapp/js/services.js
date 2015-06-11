'use strict';

/* easy example
// -------------------------
// services.js
// -------------------------
var services = angular.module('App.services', ['ngResource']);
var baseUrl = 'http://localhost\\:8080';

function TodoFactory($resource) {
    return $resource(baseUrl + '/todoMavenGlassfish/webresources/todos/3', {}, {
        query: {method: 'GET', isArray: false} // params: {}, 
    })
}

function TodoListFactory($resource) {
    return $resource(baseUrl + '/todoMavenGlassfish/webresources/todos', {}, {
        query: {method: 'GET', isArray: true}
    })
}

services.factory('TodoFactory', TodoFactory);
services.factory('TodoListFactory', TodoListFactory);

*/


// -------------------------
// services.js
// -------------------------
var todosServices = angular.module('App.services', ['ngResource']);
var baseUrl = 'http://localhost\\:8080';


function TodoListFactory($resource) {
    return $resource(baseUrl + '/todoMavenGlassfish/webresources/todos', {}, {
        query: {method: 'GET', isArray: true},
        create: { method: 'POST' }
    });    
}
todosServices.factory('TodoListFactory', TodoListFactory);


function TodoFactory($resource) {
    return $resource(baseUrl + '/todoMavenGlassfish/webresources/todos/:todoId', {}, {
        query: {method: 'GET', isArray: false}, 
        update: { method: 'PUT'}, 
        delete: { method: 'DELETE'}
    })
}
todosServices.factory('TodoFactory', TodoFactory);


todosServices.service('todos', ['$rootScope', '$filter', function ($scope, $filter){
    
    var list = []; // list of Todos
    
    // ToDoリストの変更を監視し 全 $scope に対して change:list イベントを発行する
    $scope.$watch(function (){
	return list;
    }, function (newValue, oldValue){
	$scope.$broadcast('change:list', newValue);
    }, true);

    var where = $filter('filter');

    var done = {done: true};
    var remaining = {done: false};

    this.filter = {
	done: done,
	remaining: remaining
    };

    this.getDone = function (){
	return where(list, done);
    };
    
    this.getList = function(){
        return list;
    };

    this.add = function (todo){
	list.push({
            id: todo.todoId,
            title: todo.todoTitle,
            done: false
	});
    };

    // TODO: used in TodoListController.
    this.syncAll = function (resultList){
        list = [];
        resultList.forEach(function(elem){
            list.push({
                id: elem.todoId,
                title: elem.todoTitle,
                done: elem.finished                
            });
        });
    };

    // remove selected ToDo
    this.remove = function (currentTodo){
	// （ボタンによって）選択したcurrentTodo以外のtodoを抽出してlistに代入
	list = where(list, function (todo){
            return currentTodo !== todo;
	});
    };

    // 完了した ToDo を全て削除
    this.removeDone = function (){
	// = 完了していないToDoのみを抽出してlistに代入
	list = where(list, remaining);
    };

    this.changeState = function (state){
        angular.forEach(list, function(todo){
            todo.done = state;
	});
    };

}]);
