// =============================================================================
// WinstonJS async logger reference
// =============================================================================

// Dependencies
// {
//   "winston": "2.0.1"
// }

var winston = require('winston');

// Simple usage, using console transport
winston.info('Initial log message');
// Log message with metadata
winston.log('info', 'Configured log level and content', {content : 'lorem ipsum'});
// Log format for json. Note: without the last '{}' it wont be printed as json
winston.info('info', 'Json content: %j', {content : 'lorem ipsum'}, {});
// Level configuration: { error: 0, warn: 1, info: 2, verbose: 3, debug: 4, silly: 5 }
winston.debug('Hidden message');
winston.level = 'debug';
winston.debug('Visible message');

// function to check log levels
var iterateLogs = function (logger, headerValue)
{
   var metadata = { extra : false };
   logger.log('error', '%s [error message]', headerValue, metadata);
   logger.log('warn', '%s [warn message]', headerValue, metadata);
   logger.log('info', '%s [info message]', headerValue, metadata);
   logger.log('verbose', '%s [verbose message]', headerValue, metadata);
   logger.log('debug', '%s [debug message]', headerValue, metadata);
   logger.log('silly', '%s [silly message]', headerValue, metadata);
}

winston.log('info', 'Testing log levels');
iterateLogs(winston, 'Test');

// Create a logger with two transports, one for console and another for files.
// File will use a json format like:
// {"level":"info","message":"Multiple test [info message]","timestamp":"2015-11-03T19:26:13.374Z"}
// By default, json is set to false for console, and true for file (show here
// for reference only).
var logger = new (winston.Logger)({
   transports: [
      new (winston.transports.Console)({ level: 'debug', json : false }),
      new (winston.transports.File)({
        filename: 'winston-json.log',
        level: 'info',
        json : true
      })
    ]
  });

winston.log('info', 'Testing log levels on multiple transports');
iterateLogs(logger, 'Multiple test');

winston.log('Complete');
