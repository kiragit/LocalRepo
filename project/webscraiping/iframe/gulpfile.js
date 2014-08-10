// ストリーミングビルドシステム
var gulp = require("gulp");
// ドキュメントの生成用ライブラリ
// コメントとソースが１対１に並んだHTMLを生成できる
var docco = require("gulp-docco");
// TypeScriptコンパイル用モジュール
// JavaScriptソースを生成する
var typescript = require('gulp-tsc');
//ファイルパス
var PATHS = {
    "ts": ["app/ts/*.ts"],
    "js": ["app/js/*.js"]
};

gulp.task('build', function () {

});

// doccoを使用して、フォルダ内の全てのjavascriptファイル
// のAPIをdocsフォルダ内に作成するため、タスクを登録する。
gulp.task('docco', function () {
	//ファイルは配下の全てのjsファイル
	gulp.src("./app/**/*.js")
	  //doccoによりhtmlを
	  .pipe(docco())
	  //./doc配下に作成
	  .pipe(gulp.dest('./docs'))
});

// TypeScriptソースからJavaScriptソースを生成
gulp.task('compile', function () {
  gulp.src(PATHS.ts)
    .pipe(typescript())
    .pipe(gulp.dest('./app/js'));
});