// =============================================================================
// Module to configure an ExpressJS web server
// =============================================================================

// Requires
// {
//    "express": "4.13.3",
//    "cookie-parser": "1.4.0",
//    "morgan": "1.6.1",
//    "body-parser" : "1.14.1",
//    "express-session" : "1.12.1",
//    "method-override" : "2.3.5"
// }

var express          = require('express');
var cookieParser     = require('cookie-parser');
var methodOverride   = require('method-override');
var http             = require('http');
var bodyParser       = require('body-parser');
var morgan           = require('morgan');
var app              = express();

app.use(morgan('dev'));       // log every request to the console
app.use(cookieParser());      // read cookies (needed for auth)
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

module.exports = {
   app : app
}
