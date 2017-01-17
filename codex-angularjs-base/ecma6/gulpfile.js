var gulp     = require('gulp'),
  concat     = require('gulp-concat'),
  uglify     = require('gulp-uglify'),
  browserify = require("browserify")
  rename     = require("gulp-rename"),
  // source     = require('vinyl-source-stream'),
  fs         = require("fs"),
  babelify   = require('babelify');

gulp.task('dist', function () {

    gulp.src('js/ecma2015.js')
        // .pipe(concat('ecma6-all.js'))
        .pipe(rename('app.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('dist'))
});

gulp.task('es2015', function () {

    browserify({entries: 'js/ecma6.js', debug:true})
    .transform("babelify", { presets: ["es2015"] })
    .bundle()
    .pipe(fs.createWriteStream("js/ecma2015.js"));
});
