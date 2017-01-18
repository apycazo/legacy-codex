var gulp     = require('gulp'),
  concat     = require('gulp-concat'),
  uglify     = require('gulp-uglify'),
  browserify = require("browserify")
  source     = require('vinyl-source-stream'),
  buffer     = require('vinyl-buffer');
  babelify   = require('babelify')
  sourcemaps = require('gulp-sourcemaps'),
  less       = require('gulp-less');;

gulp.task('babelify-and-uglify', function () {

    // ES6 conversion
    var sources = browserify({
		entries: 'js/ecma6.js',
		debug: true // Build source maps
	})
	.transform(babelify.configure({
		presets: ["es2015"]
	}));

    // Minification
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

    // copy html into dist (example)
    // gulp.src('./html/ecma6.html').pipe(gulp.dest('./dist'));
});

gulp.task('compile-less', function () {
    return gulp.src('./less/**/*.less')
        .pipe(less())
        // .pipe(less({
        //     paths: [ path.join(__dirname, 'less', 'includes') ]
        // }))
        .pipe(gulp.dest('./dist'));
});

gulp.task('build', ['babelify-and-uglify', 'compile-less']);
