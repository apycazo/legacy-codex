var gulp     = require('gulp'),
  concat     = require('gulp-concat'),
  uglify     = require('gulp-uglify'),
  browserify = require("browserify")
  source     = require('vinyl-source-stream'),
  buffer     = require('vinyl-buffer');
  babelify   = require('babelify')
  sourcemaps = require('gulp-sourcemaps');

gulp.task('build', function () {

    var sources = browserify({
		entries: 'js/ecma6.js',
		debug: true // Build source maps
	})
	.transform(babelify.configure({
		presets: ["es2015"]
	}));

    return sources.bundle()
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
});
