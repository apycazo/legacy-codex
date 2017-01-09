// =============================================================================
// LokiJS database reference
// =============================================================================

// dependencies
// {
// "lokijs" : "1.3.10"
// }

// options: results.offset(10).limit(5).data() [to limit queries]
// find one: col.findOne("age", 30);

var loki = require('lokijs');
var db = new loki('db.json');

// Create indexed collection
var users = db.addCollection('users', { indices: ['gid'] });

// Create some entries on the DB
var john_doe = users.insert( { name : 'john doe', email: 'john.doe@test.org', gid: 1, age: 30 } );
var mr_smith = users.insert( { name : 'mr smith', email: 'mr.smith@test.org', gid: 1, age: 45 } );
var jane_doe = users.insert( { name : 'jane_doe', email: 'jane.doe@test.org', gid: 2, age: 25 } );

// Show current db
console.log('\nInitial DB\n' + db.serialize());

// Update user and sync db index
john_doe.age = 31;
users.update(john_doe);
console.log('\nUpdated john doe and removed mr smith\n' + db.serialize());

// Exact match
var result = users.find({age:25});
console.log('\nFind user with age=25\n' + JSON.stringify(result));

// Query for young users
console.log('\nFetch users younger than 40');
var result = users.find({age: { $lt: 40 }});
result.forEach(function (entry){ console.log(JSON.stringify(entry)); });

console.log('\nFetch users older than 35');
var result = users.find({age: { $gt: 35 }});
result.forEach(function (entry){ console.log(JSON.stringify(entry)); });

// Fetch by ID
var result = users.get(3);
console.log('\nFetch user with id: 3\n' + JSON.stringify(result));

// Where clause
var user_is_less_than_30 = function (obj) {
   return obj.age < 30;
}
var result = users.where(user_is_less_than_30);
console.log('\nWhere clause\n' + JSON.stringify(result));

// Save DB
function load_and_test_after_saving () {

   console.log('\nDatabase persisted!');
   // Reload DB (config is an example)

   // var db_config = {
   //      autosave: true,
   //      autosaveInterval: 10000 // 10 seconds
   // }
   // var db2 = new loki('db.json', db_config);
   var db2 = new loki('db.json');
   console.log('\nLoaded DB\n' + db.serialize());

   var users2 = db.getCollection('users');
   var result = users2.get(2);
   console.log('\nFetch (again) user with id: 3\n' + JSON.stringify(result));

   // Count (sort of)
   console.log('\nCollection users has ' + users2.find().length + ' entries');

   // Done
   console.log('Demo test complete');
}

db.save(load_and_test_after_saving);
