// -----------------------------------------------------------------------------
// Angular module init
// -----------------------------------------------------------------------------
var core = angular.module('test',[]);

// -----------------------------------------------------------------------------
// Service
// -----------------------------------------------------------------------------
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

// -----------------------------------------------------------------------------
// Controller
// -----------------------------------------------------------------------------
function CoreCtrl ($scope, $window, commonSrvc) {

    angular.extend($scope, {
        alert: function (text) {
            commonSrvc.showAlert(text);
        },
        goToGithub: function () {
            $window.open('https://github.com/apycazo/codex/tree/master/codex-angularjs-base/ecma6', '_blank');
        }
    });

    // --- Parameter defaults ---

    var inc = function (value, delta = 1) {
        return value + delta;
    }

    angular.extend($scope, {
        parameterDefaults: {
            src: inc.toString(),
            val: inc(9)
        }
    });

    // --- Unknown parameters ---

    var sum = function (...values) {
        result = 0;
        values.forEach(value => result += value);
        console.log(`Final value = ${result}`);
        return result;
    }

    angular.extend($scope, {
        parametersUnknown: {
            src: sum.toString(),
            val1: sum(1,2,3),
            val2: sum()
        }
    });

    // --- Class init ---

    class Foo {
        init(v = 1) {
            this.value = v;
            return this;
        }
        get() {
            return this.value;
        }
    }

    var foo1 = new Foo();
    var foo2 = new Foo(2);

    angular.extend($scope, {
        classInit: {
            src: Foo.toString(),
            val1: new Foo().init().get(),
            val2: new Foo().init(2).get(),
        }
    });

    // --- Array init
    angular.extend($scope, {
        arrayInit: {
            src: 'var values = [..."123"]',
            val: [..."123"]
        }
    });

    var values = [..."123"];
    values.forEach(v => console.log('value: ' + v));
    var multilineString = `
        some
        multiline
        string:
        ${values[0]}: ${sum(5)}
    `;

    console.log(multilineString);


}

core.controller('CoreCtrl', ['$scope', '$window', 'CommonSrvc', CoreCtrl]);
