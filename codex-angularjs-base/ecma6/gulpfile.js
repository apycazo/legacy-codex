var gulp     = require('gulp'),
  concat     = require('gulp-concat'),
  uglify     = require('gulp-uglify'),
  browserify = require("browserify")
  source     = require('vinyl-source-stream'),
  buffer     = require('vinyl-buffer');
  babelify   = require('babelify')
  sourcemaps = require('gulp-sourcemaps'),
  rename     = require("gulp-rename"),
  less       = require('gulp-less');;

gulp.task('babelify-and-uglify', function () {

    // ES6 conversion
    var sources = browserify({
		entries: 'dev/js/ecma6.js',
		debug: true // Build source maps
	})
	.transform(babelify.configure({
		presets: ["es2015"]
	}));

    // Minification (including source maps)
    return sources.bundle()
		.pipe(source('app.min.js'))
		.pipe(buffer())
		.pipe(sourcemaps.init({
			loadMaps: true
		}))
		// .pipe(plugins.ngAnnotate())
		.pipe(uglify())
		.pipe(sourcemaps.write('./', {
			includeContent: true
		}))
		.pipe(gulp.dest('dist'));
});

gulp.task('compile-less', function () {
    return gulp.src('./dev/less/*.less')
        .pipe(less())
        .pipe(gulp.dest('./dist'));
});

gulp.task('copy-resources', function () {

    gulp.src('./dev/ecma6.html')
        .pipe(gulp.dest('./dist'));

    gulp.src('./dev/css/cyborg.bootstrap.min.css')
        .pipe(rename('theme.css'))
        .pipe(gulp.dest('./dist'));
});

gulp.task('build', [
    'babelify-and-uglify',
    'compile-less',
    'copy-resources'
]);
