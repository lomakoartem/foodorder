/**
 * Created by lomak on 31.05.2016.
 */
var connect = require('gulp-connect');
var gulp = require('gulp');
var modRewrite = require('connect-modrewrite');
var proxy = require('http-proxy-middleware');


gulp.task('html', function () {
    gulp.src('src/main/webapp/front-app/app/*.html')
        .pipe(connect.reload());
});

gulp.task('js', function () {
    gulp.src('src/main/webapp/front-app/app/*.js')
        .pipe(connect.reload());
});


gulp.task('default', ['html','js' ]);