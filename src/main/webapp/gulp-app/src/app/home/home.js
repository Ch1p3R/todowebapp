module.exports = angular.module('todoApp.home', [])
    .config(function($stateProvider){
        $stateProvider.state('home', {
            url:'/home',
            views:{
                'main':{
                    controller:'HomeCtrl',
                    templateUrl:'app/home/home.tpl.html'
                }
            },
            data:{ pageTitle: 'Home'}
        });
    })
    .controller('HomeCtrl', function($rootScope){
        $rootScope.isLoggedIn = true;

    })
;