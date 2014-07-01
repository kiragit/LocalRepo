jQuery(document).ready(function () {
    var fs = require('fs');
    var data = JSON.parse(fs.readFileSync('./conf/data.json', 'utf8'));

    for (var i in data) {
        $("#site").append("<li><strong>" + data[i].division + "</strong></li>");
        for (var j in data[i].site) {
            var newAnchor = document.createElement("a");
            newAnchor.appendChild(document.createTextNode(data[i].site[j].title));
            newAnchor.setAttribute("href", data[i].site[j].url);
            $("#nav_site").append(newAnchor);

            var newlist = document.createElement("li");
            newlist.appendChild(document.createTextNode(data[i].site[j].title));
            newlist.addEventListener("mouseover", targetover, false);
            $("#site").append(newlist);
        }
    }
});

function targetover() {
    var fs = require('fs');
    var data = JSON.parse(fs.readFileSync('./conf/data.json', 'utf8'));

    for (var i in data) {
        for (var j in data[i].site) {
            if (data[i].site[j].title == this.textContent) {
                document.querySelector('#title').value = data[i].site[j].title;
                document.querySelector('#url').value = data[i].site[j].url;
                document.querySelector('#id').value = data[i].site[j].id;
                document.querySelector('#pass').value = data[i].site[j].pass;
            }
        }
    }
}

function getlink() {
    var URL = document.querySelector('#url').value;
    var id = document.querySelector('#id').value;
    var pass = document.querySelector('#pass').value;
    console.log("URL:" + URL + "/id:" + id + "/pass:" + pass);

    if (URL.match(/^https:/)) {
        getlinkhttps(URL, id, pass);
    } else if (URL.match(/^http:/)) {
        getlinkhttp(URL, id, pass);
    }
}

function getlinkhttps(URL, id, pass) {
    var _url = require('url');
    var _https = require('https');
    var URL2 = _url.parse(URL);

    _https.get(URL2, function (res) {
        var _resData = '';
        res.on('data', function (chunk) {
            _resData += chunk;
        });

        res.on('end', function () {
            parseResponseData(URL, id, pass, _resData);
        });
    }).on('error', function (e) {
        console.error(e);
    });
}

function getlinkhttp(URL, id, pass) {
    var _http = require('http');

    _http.get(URL, function (res) {
        var _statusCode = res.statusCode;

        if (_statusCode != 200) {
            return;
        }
        var _resData = '';
        res.on('data', function (chunk) {
            _resData += chunk;
        });
        res.on('end', function () {
            parseResponseData(URL, id, pass, _resData);
        });
    }).on('error', function (e) {
        console.log('Error!');
    });
}

function parseResponseData(URL, id, pass, resData) {
    var _cheerio = require('cheerio');
    var $ = _cheerio.load(resData);
    var formelement = $('login_open');
    login(URL, id, pass);
}

function login(URL, id, pass) {
    console.log("login");

    setTimeout(document.location.href = URL, 5000);
    setTimeout(step1(URL, id, pass), 5000);
}

function step1(URL, id, pass) {
    console.log("start");
}
