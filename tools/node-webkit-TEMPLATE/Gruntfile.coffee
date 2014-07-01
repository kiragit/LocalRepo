module.exports = ( grunt ) ->
  grunt.initConfig
    # TypeScriptコンパイル 
    typescript:
      sourcemap:
        src: [ './ts/**/*.ts' ]
        dest: 'js/app.js'
 
    # コンパイルタスク 
    grunt.registerTask 'compile', [
      'typescript:sourcemap'
    ]
    grunt.registerTask 'default', [
      'watch'
    ]
    # 監視設定 
    watch:
      compile:
        files: [ 'ts/*.ts' ]
        tasks: [ 'compile' ]

  grunt.loadNpmTasks('grunt-typescript');
  grunt.loadNpmTasks('grunt-contrib-watch');