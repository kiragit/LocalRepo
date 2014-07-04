// タスクランナーにgulpを使用する。  
// インストール方法
// 1 npm init  
// 2 npm install gulp  
// 3 gulpfile.jsを作ってプロジェクトのルートに置く(本ファイル)
//
var gulp = require("gulp");

// APIドキュメントの作成にdoccoを利用する。  
// インストール方法
// 1 npm install --save-dev gulp-docco  
// 記述はMarkdown形式が使える。  
// #### H4  
// ##### H5
// ###### H6  
// ~~ 打ち消し ~~  
// ** ボールド **  
// _ イタリック _  
// [リンク](http://wwww.google.com)  
// - - -   
// | Left align | Right align | Center align |
// |:-----------|------------:|:------------:|
// | This       | This        |     This     |
// | column     |      column |    column    |
// | will       |        will |     will     |
// | be         |          be |      be      |
// | left       |       right |    center    |
// | aligned    |     aligned |   aligned    |
//
// 数式は以下の様に記載もできるし、$ x^2 + y^2 = 1 $のように埋め込むこともできる。  
//
// 
var docco = require("gulp-docco");

// doccoを使用して、フォルダ内の全てのjavascriptファイル
// のAPIをdocsフォルダ内に作成するため、タスクを登録する。
gulp.task('docco', function () {
	//ファイルは配下の全てのjsファイル
	gulp.src("./**/*.js")
	  //doccoによりhtmlを
	  .pipe(docco())
	  //./doc配下に作成
	  .pipe(gulp.dest('./docs'))
});