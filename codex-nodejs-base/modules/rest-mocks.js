// =============================================================================
// Module to configure an ExpressJS web server with some rest services
// =============================================================================

module.exports = function (app) {

   var rootPath = '/mocks';
   var store = {};

   app.get(rootPath, function (req, res) {

      var baseUrl = req.protocol + '://' + req.get('host') + req.originalUrl;

      res.json({
         success:true,
         api : {
            findAll : baseUrl + '/store',
            findById : baseUrl + '/store/:id',
            create : baseUrl + '/store',
            update : baseUrl + '/store/:id',
            remove : baseUrl + '/store/:id'
         }
      });
   });

   // ID with optional route for an id
   app.route(rootPath + '/store(/:id)?')
      .get(function (req, res) {
         var id = req.params.id;
         if (typeof id == 'undefined') res.send(store);
         else if (typeof store[id] == 'undefined') {
            res.status(404).json({success : false, error : 'ID does not exist'});
         }
         else res.send(store[id]);
      })
      .post(function (req, res) {
         var id = req.body.id;
         if (typeof store[id] != 'undefined') {
            res.status(400).json({success : false, error : 'ID already exists'});
         }
         else {
            store[id] = req.body;
         }
         res.status(201).send({success:true, id : id});
      })
      .put(function (req, res) {
         var id = req.params.id;
         if (typeof id == 'undefined') {
            res.status(400).json({success : false, error : 'Missing ID on path'});
         }
         else if (typeof store[id] == 'undefined') {
            res.status(404).json({ success : false, error : 'ID ' + id + ' not found'});
         }
         else {
            store[id] = req.body;
            delete store[id].id;
            res.send({success:true});
         }
      })
      .delete(function (req, res) {
         delete store[req.params.id];
         res.send({success:true});
      });
}
