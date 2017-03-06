var gulp = require('gulp'),
	browserSync = require('browser-sync'),
	concat = require('gulp-concat'),
	uglify = require('gulp-uglifyjs'),
	del = require('del'),
	minifyCSS = require('gulp-minify-css'),
	cssnano = require('gulp-cssnano'),
	rename = require('gulp-rename'),
	browserify = require('browserify'),
/*	saveAnnotate = require('gulp-ng-annotate')*/
	runSequence = require('run-sequence'),
	watchify = require('watchify'),
	source = require('vinyl-source-stream');

/*gulp.task('sass', function(){
	return gulp.src('app/sass/main.sass')
	.pipe(sass())
	.pipe(gulp.dest('app/css'))
	.pipe(browserSync.reload({stream:true}))
});*/

gulp.task('browserify', function() {
  return browserify({
  	entries : ['src/app/app.js'],
  	insertGlobals : true
  })
  .bundle()
/*  .pipe(saveAnnotate())*/
  .pipe(source('bundledByBrowserify.js'))
  .pipe(gulp.dest('src/app'));
});

gulp.task('watchifyB', function() {
  var bundler = watchify(browserify({
  	insertGlobals:true,
    cache: {},
    packageCache: {},
    debug: true,
    entries: ['src/app/app.js'],
 	paths: ['./node_modules','./src/app/**/*.js']
 }));

  bundler.on('update', rebundle);
 
  function rebundle() {
    return bundler.bundle()
      .pipe(source('bundledByWatchify.js'))
      .pipe(gulp.dest('src/app'));
  }
 
  return rebundle();
});


gulp.task('scripts', function(){
	return gulp.src([
		'node_modules/jquery/dist/jquery.min.js',
		'node_modules/bootstrap/dist/js/bootstrap.min.js'
		])
	.pipe(concat('jqueryBootstrap.min.js'))
	.pipe(uglify())
	.pipe(gulp.dest('src/js'));
});
gulp.task('minify-angular', function(){
	return gulp.src('src/app/**/*.js')
	.pipe(concat('app.min.js'))
/*	.pipe(saveAnnotate()*/
	.pipe(uglify())
	.pipe(gulp.dest('src/app'));
});
gulp.task('styles', function(){
	return gulp.src([
		'node_modules/bootstrap/dist/css/bootstrap.min.css',
		'src/css/custom-cover.css'
	])
	.pipe(concat('main.css'))
	.pipe(cssnano())
	.pipe(rename({suffix:'.min'}))
	.pipe(gulp.dest('src/css'));
});

gulp.task('browser-sync', function(){
	browserSync({
		server:{
			baseDir:['dest','src']
		},
		notify:false
	});
});

gulp.task('clean', function(){
	return del.sync(['dist', 'src/app/bundled.js'])
});

gulp.task('watch', ['browser-sync'], function(){
	gulp.watch('src/*.html', browserSync.reload);
	gulp.watch('src/js/**/*.js', browserSync.reload);
	gulp.watch('src/css/**/*.css', browserSync.reload);
});

gulp.task('build', function(){

	var buildMainAppFolder = gulp.src(['src/app/**/*'])
	.pipe(gulp.dest('dist/app/'));

	var buildCss = gulp.src('src/css/**/*')
	.pipe(gulp.dest('dist/css'));

	var buildFonts = gulp.src('src/css/fonts/**/*')
	.pipe(gulp.dest('dist/css/fonts'));

	var buildJs = gulp.src('src/js/**/*')
	.pipe(gulp.dest('dist/js'));

	var buildHtml = gulp.src('src/*html')
	.pipe(gulp.dest('dist'));

});

gulp.task('default', function(){
	runSequence('clean', ['scripts', 'styles'], 
		['browserify','watchifyB'], 'sync', 'build', 'watch');

});
//Even when I've started app throuht runSequence. Browserify doesn't have enough
// time for creating bundled.js when task 'build' building without that js..
gulp.task('sync', function (cb) {  
    setTimeout(function () {
    	cb();
    }, 1000);
});

