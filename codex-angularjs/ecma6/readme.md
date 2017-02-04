# EcmaScript 6 demo

Sample usage of AngularJS + EcmaScript6 + Gulp.

## Task list

- [ ] Use https://www.npmjs.org/package/gulp-html-replace to switch dependencies on `dist` .
- [ ] Update code layout and include instructions on how to run as `dist` or `dev`.
- [x] Change page layout and bootstrap theme.
- [x] Improve page w/bootstrap to include each result in its own panel w/sources.

## Start

Install resources with `npm install`, or add dependencies with `npm install --save-dev <package>`.

Test with nodejs http-server `http-server .` (To install: `npm install -g http-server`).

## Build with gulp

A working `gulpfile.js` is included to transform ES6 code into ES5, minimize and copy required files
on `/dist`. To run the build using nodejs use the command: `gulp build`.
