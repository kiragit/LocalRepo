var request = require("request");
var cheerio = require("cheerio");

request(
{
uri: process.argv[2]
},
function(error, response, body) {
var $ = cheerio.load(body); //取得したページのbody部をパース

$("a").each(function() { //パースした内容にはjQuery風のセレクタでアクセスできる
var link = $(this);
var text = link.text();
var href = link.attr("href");

//console.log(text + " -> " + href);
console.log(text);
});
}
);
