'use strict';

/* easy example
// -------------------------
// controllers.js
// -------------------------
var app = angular.module('App.controllers', []);

function TodoCtrl(TodoFactory) { //$routeParams, 
    this.todoFromJAXRS = TodoFactory.query(); //{todo_id: $routeParams.todo_id}
}
app.controller('TodoCtrl', TodoCtrl);

function TodoListCtrl(TodoListFactory) {        
    this.todoListFromJAXRS = TodoListFactory.query();        
}
app.controller('TodoListCtrl', TodoListCtrl);

*/


// -------------------------
// controllers.js
// -------------------------
var todosControllers = angular.module('App.controllers', ['App.services']);

//新規 ToDo 作成、登録を受け持つ RegisterController
function RegisterController(todos, TodoListFactory) {
    this.newTitle = '';
    this.addTodo = function (){
	
        // DBに反映
        var result = TodoListFactory.create({"todoTitle" : this.newTitle});
        // リスト追加
        result.$promise.then(function(){
            todos.add(result);
        });
         
        // リスト追加が終了したらnewTitleを初期化する
	this.newTitle = '';   
    };
}
todosControllers.controller('RegisterController', RegisterController); // as registerCtrl



//件数モデルとフィルタ切替や状態切替などのボタン類を受け持つ ToolbarController
function ToolbarController($scope, todos, TodoListFactory, TodoFactory){
    
    var vm = this;
    
    vm.filter = todos.filter;
    
    $scope.$on('change:list', function (evt, list) {
        var length = list.length;
        var doneCount = todos.getDone().length;

        vm.allCount = length;
        vm.doneCount = doneCount;
        vm.remainingCount = length - doneCount;
    });


    vm.checkAll = function () {
        
        // !!vm.remainingCount: not complete(=false) or complete(=true)
        // if vm.remainingCount = 0, ! makes the argument "true", and !! makes it "false"
        var booleanVal = !!vm.remainingCount;
        
        var doneList = todos.getList();
        doneList.forEach(function(elem){
            // renew value and reflect to DB
            var todoObtained = TodoFactory.query({"todoId" : elem.id});
            todoObtained.$promise.then(function(obtained){
                // update value
                obtained.finished = booleanVal;
                // reflect to DB
                TodoFactory.update({"todoId" : elem.id}, todoObtained);
            });
        });

        // change list in todos service
        todos.changeState(booleanVal);
    };

    vm.changeFilter = function (filter) {
        $scope.$emit('change:filter', filter);
    };

    vm.removeDoneTodo = function () {
        
        var doneTodo = todos.getDone();
        doneTodo.forEach(function(elem){
            // DBに反映
            TodoFactory.delete({"todoId" : elem.id});
        });
        
        todos.removeDone();
        
    };

    
    vm.listAll = function(){
        var resultList = TodoListFactory.query();
        resultList.$promise.then(function(){
            // 処理が完了した時点でresultListに値が挿入される
            // todosサービスを介してresultListを加工
            todos.syncAll(resultList);
        });
    };
        
}
todosControllers.controller('ToolbarController', ToolbarController); // as toolbarCtrl


// ToDo リストと ToDo モデルの編集を受け持つ TodoListController
todosControllers.controller('TodoListController', ['$scope', 'todos', 'TodoFactory',
    function ($scope, todos, TodoFactory) {

        $scope.$on('change:list', function (evt, list) {
            $scope.todoList = list;
        });
        
        var originalTitle;
        var originalDone;
        $scope.editing = null;

        $scope.editTodo = function (todo) {
            // originalTitleに編集前の要件内容を保存しておく
            originalTitle = todo.title;
            originalDone = todo.done;
            
            $scope.editing = todo;
        };

        $scope.doneEdit = function (todoForm, todo) {
            // validation: <form name="todoForm">
            if (todoForm.$invalid) {
                // if form is invalid, rollback to preserved content
                $scope.editing.title = originalTitle;
            }else{
                // comparison before and after editing
                var isSameTitle = (originalTitle === todo.title);
                if(!isSameTitle){
                    // if form is correct, renew value and reflect to DB
                    var todoObtained = TodoFactory.query({"todoId" : todo.id});
                    todoObtained.$promise.then(function(obtained){
                        // update value
                        obtained.todoTitle = todo.title;
                        // reflect to DB
                        TodoFactory.update({"todoId" : todo.id}, todoObtained);
                    });                    
                }
                
            }
            $scope.editing = originalTitle = null;
        };
        
        $scope.doneCheckbox = function (todo) {
            var todoObtained = TodoFactory.query({"todoId" : todo.id});
            todoObtained.$promise.then(function(obtained){
                // update value
                obtained.finished = todo.done;
                // reflect to DB
                TodoFactory.update({"todoId" : todo.id}, todoObtained);
            });
        };

        $scope.removeTodo = function (todo) {

            // DBに反映
            TodoFactory.delete({"todoId" : todo.id});
            todos.remove(todo);
            
        };
    }
]);


// フィルタリング状態モデルを受け持つ MainController

function MainController($scope) {
    var vm = this;
    vm.currentFilter = null;
    $scope.$on('change:filter', function (evt, filter) {
        vm.currentFilter = filter;
    });
}
todosControllers.controller('MainController', MainController); // as mainCtrl
