
// Create app
var app = require('../modules/express-cfg.js').app;
// Configure app with the mocked api
require('../modules/rest-mocks')(app);
// Start app
app.listen(8080, function () { console.log('Listening on port 8080'); });
