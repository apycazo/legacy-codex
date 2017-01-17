// Core module
var core = angular.module('test',[]);

// Service
function CommonSrvc () {

  // Creates the service instance
  return {
    showAlert : function (text) {
      text = angular.isDefined(text) ? text : 'An alert has been thrown';
      alert(text);
    }
  }
}

core.factory('CommonSrvc', CommonSrvc);

// Controller
function CoreCtrl ($scope, commonSrvc) {

  function processValue (x, y = 10, ...extras) {
    var total = x+y;
    extras.forEach(v => total += v);
    return total;
  }

  $scope.text = `Angular library loaded and working (value = ${processValue(5,1,2,3)})`;

  var values = [..."123"];
  values.forEach(v => console.log('value: ' + v));
  var multilineString = `
    some
    multiline
    string:
    ${values[0]}: ${processValue(5)}
  `;
  console.log(multilineString);

  // class sample

  class Tester {
    init(y = 5) {
      this.x = 10;
      this.y = y;
    }
    log(text = '') {
      console.log(`text: '${text}', x = ${this.x}, y = ${this.y}`);
    }
  }

  var tester1 = new Tester();
  tester1.init(2);

  var tester2 = new Tester();
  tester2.init(5);

  tester1.log('instance 1');
  tester2.log('instance 2');

  $scope.alert = function (text) {
    commonSrvc.showAlert(text);
  }

}

core.controller('CoreCtrl', ['$scope', 'CommonSrvc', CoreCtrl]);
