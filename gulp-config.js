const defaultSource = 'src/main/webapp/',
    gulpConfig = {
        src: {
            scss: defaultSource + 'resources/config/scss/main.scss',
            js: defaultSource + 'front-app/app/app.js'
        },
        dest: {
            scss: defaultSource + 'build/css',
            nameJsBuildFile: 'main.js',
            js: defaultSource + 'build/js'
        },
        watch: {
            html: defaultSource + '**/*.html',
            scss: defaultSource + '**/*.scss',
            js: defaultSource + '**/*.js'
        },
        autoPrefixer: {
            browsers: ['last 2 versions'],
            cascade: false
        },
        browserify: {
            debug: true
        },
        babelify: {
            sourceMaps: false
        },
        uglifyify: {
            global: true
        },
        mapsDest: '../maps',
        browserSyncConfig: {
            server: defaultSource,
            host: 'localhost',
            port: 3000
        },
        browserSyncStream: {
            stream: true
        }
    };

export default gulpConfig;