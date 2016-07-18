angular.module('starter.controllers', ['firebase'])

.controller('DashCtrl', function($scope) {})

.controller('ChatsCtrl',['$scope','$firebase','$rootScope',
  function($scope,$firebase,$rootScope){
    var ref = new Firebase('https://appTest.firebaseio.com/');

    //firebase 서버와 동기화를 한다.
    var sync = $firebase(ref);

    $scope.chats = sync.$asArray();

    $scope.sendChat = function(chat){
      //$add는 firebase에서 제공 되는 메소드
      $scope.chats.$add({
        user:'Guest',
        message: chat.message
      });

      chat.message = "";
    }

  }
])


.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});
