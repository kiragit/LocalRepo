(function() {
    var _http = require('http');
    var _cheerio = require("cheerio");  // スクレイピングするためにHTMLをjQueryのように使えるモジュール
    var _URL = process.argv[2];
//  console.log('args: ', process.argv);
 
    // Webページを取得
    _http.get(_URL, function(res){
        var _statusCode = res.statusCode;
//        console.log(_statusCode)
        if(_statusCode != 200) {
            // 200ではない場合は失敗
            return;
        }
        var _resData = '';
        res.on('data', function (chunk) {
            _resData += chunk;
        });
        res.on('end', function () {
 //           console.log('Get Data Finish!')
            parseResponseData(_resData);
        });
    }).on('error', function(e) {
        cosole.log('Error!')
    });
 
    function parseResponseData(resData) {
        var _ret = {};
        // ページデータを読み込む
        var $ = _cheerio.load(resData);
        // <head>を取り出す
        var _$head = $('head');
        if(_$head.length < 1) {
            console.log('Error: not found head tag.');
            return _ret;
        }
        var _$body = $('body');
        if(_$body.length < 1) {
            console.log('Error: not found head tag.');
            return _ret;
        }
        // <head>の中の<title>を取り出す
        var _$title = _$head.find('title');
        if(_$title.length == 1) {
            var _title = _$title.text();    // これが取りたいデータ
            console.log('title : "' + _title + '"');
            _ret.title = _title;
        }
        $(".entry-title > a").each(function() { //パースした内容にはjQuery風のセレクタでアクセスできる
            var link = $(this);
            var text = link.text();
            var href = link.attr("href");

            console.log(text + " -> " + href);
            });
return _ret;
    };
})();
