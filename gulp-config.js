const defaultSource = 'src/main/webapp/',
    gulpConfig = {
        src: {
            scss: defaultSource + 'resources/config/scss/main.scss',
            js: defaultSource + 'resources/app.js'
        },
        dest: {
            scss: defaultSource + 'build/css',
            nameJsBuildFile: 'main.js',
            js: defaultSource + 'build/js'
        },
        watch: {
            main: [
                defaultSource + '**/*.html',
                defaultSource + 'resources/**/*.js'
            ],
            scss: defaultSource + '**/*.scss'
        },
        autoPrefixer: {
            browsers: [
                'last 2 versions'
            ],
            cascade: false
        },
        browserify: {
            debug: true
        },
        stringify: {
            appliesTo: {
                includeExtensions: [
                    '.html'
                ]
            },
            minify: true
        },
        babelify: {
            compact: false,
            presets: [
                'es2015',
                'react'
            ],
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
        },
        replace: {
            regex: /http:\/\/'\+location.host/,
            string: 'http://10.17.8.61:8000'
        }
    };

export default gulpConfig;