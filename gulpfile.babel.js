'use strict';

import gulpConfig from './gulp-config';
import gulp from 'gulp';
import gulpIf from 'gulp-if';
import browserify from 'browserify';
import babelify from 'babelify';
import source from 'vinyl-source-stream';
import scss from 'gulp-sass';
import autoPrefixer from 'gulp-autoprefixer';
import sourceMaps from 'gulp-sourcemaps';
import browserSync from 'browser-sync';

const resetScss = require('node-reset-scss');

let isProd = false;

gulp.task('condition', () => {
    return isProd = true;
});

gulp.task('scss', () => {
    return gulp.src(gulpConfig.src.scss)
               .pipe(gulpIf(!isProd, sourceMaps.init()))
               .pipe(scss({
                   includePaths: resetScss.includePath,
                   outputStyle: gulpIf(isProd, 'compressed')
               }).on('error', scss.logError))
               .pipe(autoPrefixer(gulpConfig.autoPrefixer))
               .pipe(gulpIf(!isProd, sourceMaps.write(gulpConfig.mapsDest)))
               .pipe(gulp.dest(gulpConfig.dest.scss))
               .pipe(gulpIf(!isProd, browserSync.reload(gulpConfig.browserSyncStream)));
});

gulp.task('js', () => {
    return browserify(gulpConfig.src.js, gulpIf(!isProd, gulpConfig.browserify))
    .transform(babelify, gulpConfig.babelify)
    // .transform(gulpIf(isProd, 'uglifyify', babelify), gulpConfig.uglifyify)
    .bundle()
    .pipe(source(gulpConfig.dest.nameJsBuildFile))
    .pipe(gulp.dest(gulpConfig.dest.js))
    .pipe(gulpIf(!isProd, browserSync.reload(gulpConfig.browserSyncStream)));
});

gulp.task('watch', ['scss', 'js'], () => {
    browserSync(gulpConfig.browserSyncConfig);
    gulp.watch(gulpConfig.watch.html).on('change', browserSync.reload);
    gulp.watch(gulpConfig.watch.scss, ['scss']);
    gulp.watch(gulpConfig.watch.js, ['js']);
});

gulp.task('default', ['watch']);

gulp.task('prod', ['condition', 'scss', 'js']);