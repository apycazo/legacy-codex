// =============================================================================
// Node schedule reference
// =============================================================================

// dependencies
// {
// "node-schedule" : "0.3.0"
// }
// Cron values
// *    *    *    *    *    *
// ┬    ┬    ┬    ┬    ┬    ┬
// │    │    │    │    │    |
// │    │    │    │    │    └ day of week (0 - 7) (0 or 7 is Sun)
// │    │    │    │    └───── month (1 - 12)
// │    │    │    └────────── day of month (1 - 31)
// │    │    └─────────────── hour (0 - 23)
// │    └──────────────────── minute (0 - 59)
// └───────────────────────── second (0 - 59, OPTIONAL)

var schedule = require('node-schedule');

// Schedule a job every 10 seconds
var ten_seconds = schedule.scheduleJob('*/10 * * * * *', function(){
    console.log('[10s] Scheduled event on ' + new Date().toISOString());
});

// Schedule every minute. Note there one less '*', since seconds are optional
var one_minute = schedule.scheduleJob('*/1 * * * *', function(){
    console.log('[1m ] Scheduled event on ' + new Date().toISOString());
});

console.log('Scheduler started');
