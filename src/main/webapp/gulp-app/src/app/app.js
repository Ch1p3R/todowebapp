(function () {

  'use strict';
  require('angular');
  require('angular-ui-router');
  require('./home/home.js');
  angular.module('todoApp', ['ui.router', 'todoApp.home'])

  .config(function($stateProvider, $urlRouterProvider){
      $urlRouterProvider.otherwise('/home');
  })

  //Load controller
  .controller('AppCtrl', ['$rootScope', '$scope', '$state', function($rootScope, $scope, $state){
    if ($rootScope.isLoggedIn){
        $state.go('/todos');
    }
    $scope.$on('$stateChangeSuccess', function(event, data){

        if(angular.isDefined(data.data.pageTitle)){
          $scope.pageTitle = data.data.pageTitle;
        }
    });

  }]);

}());