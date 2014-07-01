/// <reference path="../typings/tsd.d.ts" />

/*
    var Datastore = require('nedb')
      , path = require('path')
      , db = new Datastore({ filename: path.join(require('nw.gui').App.dataPath, 'easy.db') , autoload: true });
*/

jQuery(document).ready(function () {
    var fs = require('fs');
    var data = JSON.parse(fs.readFileSync('./conf/data.json', 'utf8'));

    for(var i in data){
    $("#site").append("<li><strong>" + data[i].division + "</strong></li>");
        for(var j in data[i].site){
            var newAnchor = document.createElement("a");
            newAnchor.appendChild( document.createTextNode(data[i].site[j].title) );
            newAnchor.setAttribute("href", data[i].site[j].url );
            $("#nav_site").append(newAnchor);

            var newlist = document.createElement("li");
            newlist.appendChild( document.createTextNode(data[i].site[j].title) );
            newlist.addEventListener("mouseover", targetover, false);
            $("#site").append(newlist);
        }
    }
});

function targetover(){

    var fs = require('fs');
    var data = JSON.parse(fs.readFileSync('./conf/data.json', 'utf8'));

    for(var i in data){
        for(var j in data[i].site){
            if(data[i].site[j].title == this.textContent){
                (<HTMLInputElement>document.querySelector('#title')).value=data[i].site[j].title;
                (<HTMLInputElement>document.querySelector('#url')).value=data[i].site[j].url;
                (<HTMLInputElement>document.querySelector('#id')).value=data[i].site[j].id;
                (<HTMLInputElement>document.querySelector('#pass')).value=data[i].site[j].pass; 
            }
        }
    }

}

function getlink() {
    var URL:string = (<HTMLInputElement>document.querySelector('#url')).value;
    var id:string = (<HTMLInputElement>document.querySelector('#id')).value;
    var pass:string = (<HTMLInputElement>document.querySelector('#pass')).value;
    console.log("URL:"+URL+"/id:"+id+"/pass:"+pass);

    if ( URL.match(/^https:/) ) {
        getlinkhttps(URL,id,pass);
    }else if ( URL.match(/^http:/) )  {
        getlinkhttp(URL,id,pass);
    }
}

function getlinkhttps(URL,id,pass){
    var _url = require('url');
    var _https = require('https');
    var URL2=_url.parse(URL);

    _https.get(URL2, function(res) {
      //console.log("statusCode: ", res.statusCode);

      var _resData = '';
      res.on('data', function(chunk) {
        _resData += chunk;
      });

    　res.on('end', function () {
      　parseResponseData(URL,id,pass,_resData);
    　});

    }).on('error', function(e) {
      console.error(e);
    });
}

function getlinkhttp(URL,id,pass){
    var _http = require('http');

    _http.get(URL, function(res){
        var _statusCode = res.statusCode;
        //console.log(_statusCode)
        if(_statusCode != 200) {
            // 200ではない場合は失敗
            return;
        }
        var _resData = '';
        res.on('data', function (chunk) {
            _resData += chunk;
        });
        res.on('end', function () {
            parseResponseData(URL,id,pass,_resData);
        });
    }).on('error', function(e) {
        console.log('Error!')
    });
}

function parseResponseData(URL,id,pass,resData) {
    var _cheerio = require('cheerio');
    var $ = _cheerio.load(resData);
    var formelement = <HTMLInputElement>$('login_open');
    login(URL,id,pass);
}

function login(URL,id,pass){
    console.log("login");
    
    setTimeout(document.location.href=URL, 5000);
    setTimeout(step1(URL,id,pass), 5000);

    //basic認証突破
    /*
    var xhr = new XMLHttpRequest();
    var form_data = new FormData(formelement);
    form_data.append("userId",id);
    form_data.append("password",pass);
    xhr.open("post", URL);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(form_data);
    xhr.onreadystatechange = function() {
    　　if (xhr.readyState==4) {
    　　　　if (xhr.status==200) {
                console.log(xhr.responseText);
                    //basic認証突破
                    //xhr.open("post", "");
                    //xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    //xhr.send(form_data);
                    //var list = document.querySelector('#output');
                    //var _cheerio = require("cheerio");  // スクレイピングするためにHTMLをjQueryのように使えるモジュール
                    // ページデータを読み込む
                    //var $ = _cheerio.load(resData);
                    // <head>を取り出す
                    /*
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
                    *//*
    　　　　}
    　　}
    }
    */
}

function step1(URL,id,pass){
    console.log("start");
    //(<HTMLInputElement>document.getElementsByName('userId')[0]).value = id;
    //(<HTMLInputElement>document.getElementsByName('password')[0]).value = pass;
    //(<HTMLInputElement>document.getElementsByName('login_open')[0]).form.submit();
    //focus();
}
