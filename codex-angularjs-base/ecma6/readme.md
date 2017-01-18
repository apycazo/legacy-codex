# EcmaScript 6 demo

Sample usage of angular + EcmaScript6 + gulp.

*TODO*: Improve page w/bootstrap to include each result in its own panel w/sources

## Start

Install resources with `npm install`, or add dependencies with `npm install --save-dev <package>`.

Test w/node http-server `http-server .` (To install: `npm install -g http-server`).

## Example Gulpfile

```javascript
var gulp     = require('gulp'),
  concat     = require('gulp-concat'),
  uglify     = require('gulp-uglify'),
  browserify = require("browserify")
  source     = require('vinyl-source-stream'),
  buffer     = require('vinyl-buffer');
  babelify   = require('babelify')
  sourcemaps = require('gulp-sourcemaps');

gulp.task('build', function () {

    // ES6 conversion
    var sources = browserify({
		entries: 'js/ecma6.js',
		debug: true // Build source maps
	})
	.transform(babelify.configure({
		presets: ["es2015"]
	}));

    // Minification
    sources.bundle()
		.pipe(source('app.min.js'))
		.pipe(buffer())
		.pipe(sourcemaps.init({
			loadMaps: true // Load the sourcemaps from
		}))
		// .pipe(plugins.ngAnnotate())
		.pipe(uglify())
		.pipe(sourcemaps.write('./', {
			includeContent: true
		}))
		.pipe(gulp.dest('dist'));

    // copy html into dist (example)
    // gulp.src('./html/ecma6.html').pipe(gulp.dest('./dist'));
});
```
