
    var Datastore = require('nedb')
      , path = require('path')
      , db = new Datastore({ filename: path.join(require('nw.gui').App.dataPath, 'easy.db') , autoload: true });


function getlink() {
    var URL = document.querySelector('#url').value;
    console.log(URL);
//  console.log('args: ', process.argv);
    if ( URL.match(/^https:/) ) {
        getlinkhttps(URL);
    }else if ( URL.match(/^http:/) )  {
        getlinkhttp(URL);
    }
}

function dbinsert(title,url,linkname,linkurl){

    console.log('insert :' + path.join(require('nw.gui').App.dataPath));  
    db.insert({title:title,url:url,linkname:linkname,linkurl:linkurl}, function (err, newDoc) {   // Callback is optional
      // newDoc is the newly inserted document, including its _id
      // newDoc has no key called notToBeSaved since its value was undefined
    });
}
function dbfind(){
    var list = document.querySelector('#dblist');
    db.find({}, function (err, docs) {
        for (var i = 0; i < docs.length; i++) {
            console.log(docs[i].title);
            var newLi = document.createElement("li");
            newLi.appendChild( document.createTextNode(docs[i].title));
            list.appendChild(newLi);
        };
    });
}

function getlinkhttps(URL){
    var _url = require('url');
    var _https = require('https');
    var URL2=_url.parse(URL);

    _https.get(URL2, function(res) {
      console.log("statusCode: ", res.statusCode);
      console.log("headers: ", res.headers);

      var _resData = '';
      res.on('data', function(chunk) {
        _resData += chunk;
      });

    　res.on('end', function () {
        console.log('Get Data Finish!')
        console.log(_resData);
      　parseResponseData(URL,_resData);
    　});

    }).on('error', function(e) {
      console.error(e);
    });
}

function getlinkhttp(URL){
    var _http = require('http');

    _http.get(URL, function(res){
        var _statusCode = res.statusCode;
        console.log(_statusCode)
        if(_statusCode != 200) {
            // 200ではない場合は失敗
            return;
        }
        var _resData = '';
        res.on('data', function (chunk) {
            _resData += chunk;
        });
        res.on('end', function () {
            console.log('Get Data Finish!')
            //console.log(_resData);
            parseResponseData(URL,_resData);
        });
    }).on('error', function(e) {
        console.log('Error!')
    });
}

function parseResponseData(URL,resData) {
    var list = document.querySelector('#output');
    var _cheerio = require("cheerio");  // スクレイピングするためにHTMLをjQueryのように使えるモジュール
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
    $("a").each(function() { //パースした内容にはjQuery風のセレクタでアクセスできる
        var link = $(this);
        var text = link.text();
        var href = link.attr("href");

        var newAnchor = document.createElement("a");
        var newTxt = document.createTextNode(text);
        newAnchor.appendChild( newTxt );
        newAnchor.setAttribute("href", href);
        newAnchor.setAttribute("target", "_blanc" );
       
        // li 要素の作成
        var newLi = document.createElement("li");
        newLi.appendChild ( newAnchor );
       
        // リストに追加
        //var list = document.getElementById("FavList");
        list.appendChild( newLi );
        dbinsert(URL,_title,text,href); 
        //ele.appendChild(text + " -> " + href);
        //console.log(text + " -> " + href);
        });
    dbfind();
return _ret;
}
