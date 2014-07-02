var doing=todo();
var fs = require('fs');
var Datastore = require('nedb')
  , path = require('path')
  , db = new Datastore({ filename: path.join(require('nw.gui').App.dataPath, 'yeild.db') , autoload: true });

function* todo() {
	yield 1;
	console.log("DB CLEAR");
	dbclear();
	yield 2;
	console.log("READ FILE");
	readFiles();
	yield 3;
	console.log("SET KEYS");
	setData();
	yield 4;
	console.log("DRAW GRAPH");
	//showFile();
	return 5;
}

doing.next();
doing.next();
doing.next();
doing.next();
doing.next();


function readFiles(){
	var rootpath;
	jQuery(document).ready(function () {

		document.querySelector("#setkeys").addEventListener("click",getKey , false);
    	document.querySelector("#graph").addEventListener("click",showFile , false);

		rootpath=document.querySelector('#income1').value;

		fs.readdir(rootpath,function(err,files){
			files.filter(function(file){
				return /.*\.txt$/.test(file); //絞り込み
			}).forEach(function (file) {
				readFile(rootpath +　file);
			});
		});	
	});


}

function readFile(filepath){

	var data = fs.readFileSync(filepath, 'utf8');
	var text  = data.replace(/\r\n|\r/g, "\n");
	var lines = text.split( '\n' );

	var yyyymmdd = filepath.match(/20\d\d\d\d/).toString();
	var outArray = {};
	readFileLogic1(outArray,"年月日:"+yyyymmdd);
 
	for (var lineNum in lines) {
		// 空行は無視する
		if ( lines[lineNum] == '' ) {
			continue;
		}
    	readFileLogic1(outArray,lines[lineNum]);
	}
	db.insert(outArray);
}

function setData(){
	getKey();
}

function showFile(){
	drowGraph("年月日","支給金合計");
}

/*
* 給与明細行の分割ロジック
*/
function readFileLogic1(dataset,line){

	newline=line.replace(/￥|,| |　/g,'');
	newline=newline.replace(/ *： */g,':');
	newlines=newline.split(' ');

	for (var col in newlines){
		//空行は無視するmusisuru
		if ( newlines[col].indexOf(":") > 0 ) {
			//dataset is hosting datasetは巻き上げ
			var keyvalue = newlines[col].split(':');
			dataset[keyvalue[0]]=keyvalue[1];
		}
	}
	return dataset;
}

	var checkDuplicate = function(array, str){
	    for(var i =0; i < array.length; i++){
	        if(str == array[i]){
	            return true;
	        }
	    }
	    return false;
	};

function getKey(){
	//グラフに描画するし対象をkeyに集約
	var keys = [];
	// 配列array 内に 変数str と同じ値が存在するかを確認
	// 同じ値が存在したら true, 存在しなかったら false を返す


	//dbからキーを抽出し、重複を排除した上で、配列に挿入
	db.find({},function (err, docs) {
		for (var doc in docs) {
			for(var col in docs[doc]){
				if( !checkDuplicate(keys, col) ){
				    keys.push(col);
			        var newlist = document.createElement("li");
			        newlist.appendChild(document.createTextNode(col));
			        newlist.addEventListener("click",setY(col),false);
			        newlist.addEventListener("dblclick",setX(col),false);
			        document.querySelector('#keys').appendChild(newlist);
				}
			}
		}
		console.log("show keys");
		console.log(keys);
	});
}

//xの値を設定
function setX(tagValue) {
	return function(ev){
		document.querySelector('#xLine').value = tagValue;
	}
}
//yの値を設定
function setY(tagValue) {
	return function(ev){
		document.querySelector('#yLine').value = tagValue;
	}
}

function dbclear(){
	db.remove({}, { multi: true }, function (err, numRemoved) {
		console.log(numRemoved);
	});
}

function drowGraph(x,y){

	var xLineKey = document.querySelector('#xLine').value;
	var yLineKey = document.querySelector('#yLine').value;
	var yMax = Number.MIN_VALUE;
	var yMin = Number.MAX_VALUE;
	var xMax = Number.MIN_VALUE;
	var xMin = Number.MAX_VALUE;
	var title = xLineKey+" - "+yLineKey;

	//グラフの描画設定
	ccchart.base('', {config : {
	    "type" : "line", //チャート種類
	    "useVal" : "no", //値を表示
	    "xScaleFont" : "100 11px 'meiryo'", //水平軸目盛フォント
	    "xScaleRotate": 90, //角度 (0で水平、-45で右肩上がり、-90で直角)
	    "yScaleFont" : "100 11px 'meiryo'", //垂直軸目盛フォント
	    "hanreiFont" : "bold 11px 'meiryo'", //凡例フォント
	    "valFont" : "bold 10px 'meiryo'", //値フォント
	    //"paddingTop" : "25", //上パディング
	    //"paddingRight" : "10", //右パディング
	    //"paddingBottom" : "20", //下パディング
	    //"paddingLeft" : "10", //左パディング
	    "colorSet" : ["blue"], //データ列の色
	    "useShadow" : "no", //影
	    "height" : "300", //チャート高さ
	    "width" : "570", //チャート幅
	    //"useMarker" : "arc", //マーカー種類
	    "markerWidth" : "8", //マーカー大きさ
	    "valYOffset" : "8", //値オフセット
	    "valXOffset" : "-8", //値オフセット
	    "bg" : "#fff", //背景色
	    "textColor" : "#333", //テキスト色
	    //"lineWidth" : "1", //ラインの太さ
	    //"hanreiXOffset" : "0",
	    //"hanreiXOffset" : "0",
	    "hanreiAlign" : "center",
	    "useMarker" : "maru",
	    "xLines": "none",
    	"xScaleSkip": 0,
    	"maxWsColLen": 120,
  	}});

  	//グラフの個別設定
	var chart_sample1 = {
		"config" : {
			"colorSet" : ["red"], //データ列の色
			"minY" : 0, //Y軸最小値
			"maxY" : 0, //Y軸最大値
			"minX" : 0, //Y軸最小値
			"maxX" : 0, //Y軸最大値
			"axisXLen" : 10, //水平目盛線の本数
			"roundDigit":0, // 軸目盛値の端数処理
		},
		"data" : [
		 	[xLineKey],
		 	[yLineKey],
		]
	};

	//描画データの設定
	db.find({}).sort({ "年月日" : 1 }).exec(function (err, docs) {
		var i=0;
		for (var doc in docs) {
			i++;
			//console.log(docs[doc][xLineKey]+":"+docs[doc][yLineKey]);
     		chart_sample1["data"][0][i] = docs[doc][xLineKey];
  			chart_sample1["data"][1][i] = docs[doc][yLineKey];
  			//描画領域の設定
  			if(yMax<1*docs[doc][yLineKey]){
  				yMax=docs[doc][yLineKey];
  			}
  			if(yMin>1*docs[doc][yLineKey]){
  				yMin=docs[doc][yLineKey];
  			}
  			if(xMax<1*docs[doc][yLineKey]){
  				xMax=docs[doc][yLineKey];
  			}
  			if(xMin>1*docs[doc][yLineKey]){
  				xMix=docs[doc][yLineKey];
  			}
		}
		//縦軸の最大値・最小値の設定
		if(yMin==yMax){
			yMin=0.9*yMin
			yMax=1.1*yMax
		}
		if(xMax==xMin){
			xMax=1.1*xMax
			xMin=0.9*xMin
		}
		chart_sample1["config"]["minY"]=yMin;
		chart_sample1["config"]["maxY"]=yMax;
		chart_sample1["config"]["minX"]=xMin;
		chart_sample1["config"]["maxX"]=xMax;
		//グラフの描画
		ccchart.init("chart_sample1", chart_sample1); 
	});
}
/*-------------------------------------------*/

//スクレイピング





