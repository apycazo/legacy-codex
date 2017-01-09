// =============================================================================
// ExpressJS web server reference
// =============================================================================

// [ SERVER CONFIGURATION ]
var port = process.env.PORT || 8080;
// Server dependencies
var express          = require('express');
var cookieParser     = require('cookie-parser');
var methodOverride   = require('method-override');
var http             = require('http');
var bodyParser       = require('body-parser');
var morgan           = require('morgan');
// Create and configure server
var app              = express();
// Configure express
app.use(morgan('dev'));       // log every request to the console
app.use(cookieParser());      // read cookies (needed for auth)
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// [ ENDPOINT MAPPING ]
// Simple GET method
var root = function (req, res) {
   res.send({success : true, fn : 'root'});
}
app.get('/', root);

// listen on all methods
var all = function (req, res) {
   res.send({success : true, fn : 'all'});
}
app.all('/all', all);

// multiple callbacks
var logCall = function (req, res, next) {
   console.log('Calling middle function');
   next();
}
var logValue = function (req, res) {
   res.send({success : true, fn : 'logValue'});
}
app.get('/log', logCall, logValue);

// Multiple values using route
app.route('/element')
   .get(function (req, res) {
      res.send({ success : true, fn : 'data', method : 'GET'});
   })
   .post(function (req, res) {
      res.send({ success : true, fn : 'data', method : 'POST'});
   });

// Parameters and query
// Test with GET http://localhost:8080/params/test?mode=x
var getParams = function (req, res) {
   var mode = typeof req.query.mode == 'undefined' ? 'default' : req.query.mode;
   res.send({ success : true, fn : 'data', id : req.params.id, mode : mode });
}
app.get('/params/:id', getParams);

// [ START SERVER ]
app.listen(port, function () { console.log('Listening on port ' + port); } );
