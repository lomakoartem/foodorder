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
            html: defaultSource + '**/*.html',
            scss: defaultSource + '**/*.scss',
            js: defaultSource + 'resources/**/*.js'
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
        }
    };

export default gulpConfig;