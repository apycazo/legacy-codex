# EcmaScript 6 demo

TBD

## Note: Gulp example to be fixed:

gulp.task('scripts', function() { 
return gulp.src('app/js/**/*.js')
	.pipe(jshint('.jshintrc'))
	.pipe(jshint.reporter('default'))
	.pipe(concat('main.js'))
	.pipe(ngmin())
	.pipe(gulp.dest('app/dist/js'))
	.pipe(rename({suffix: '.min'}))
	.pipe(uglify({mangle: false}))
	.pipe(gulp.dest('app/dist/js'))
	.pipe(livereload(server))
	.pipe(notify({ message: 'Scripts task complete' }));
	});
