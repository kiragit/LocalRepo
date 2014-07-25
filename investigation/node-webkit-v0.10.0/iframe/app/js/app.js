// index.htmlで宣言しているボタンにアクションを追加する。
readFiles();

//野村Eプランのインスタンスを生成
var epn = new EplanNomura;
var jasso = new Jasso;
var viewsuica = new ViewSuica;
var Datastore = require('nedb')
, path = require('path')
, db = new Datastore({ filename: path.join(require('nw.gui').App.dataPath, 'iframe2.db') , autoload: true});

finddata();

var pages = {
	'https://scholar-ps.sas.jasso.go.jp/mypage/login_open.do':jasso,
	'https://www.e-plan.nomura.co.jp/e-forum/html/index.html':epn,
	'https://viewsnet.jp/default.htm':viewsuica
}

// yieldによる同期処理のインスタンス宣言（ボタンの押下とともに逐次で実行される）
// 引数に逐次実行処理の配列を渡す。
//var doing=todo(epn.getStep());

// index.htmlで宣言しているコンポーネントにアクションを追加する。
function readFiles(){
	//画面の読み込みを待ち、
	jQuery(document).ready(function () {
		var key=document.getElementById('myframe').src;
		var page=pages[key];
		//ボタンへアクションの追加をする。
		document.querySelector("#setkeys").addEventListener("click",page.next, false);
	});
}

//野村Eプラン用 モジュールパターンで実装
function EplanNomura(){
	
	//-------------以下　プライベート　スコープ---------------
	// ボタン押下とともに逐次実行されるアクション（画面遷移）
	var step = [step1,step2];
	var go = todo(step);

	// 逐次実行される画面遷移処理１（ログイン）
	function step1(){
		// index.htmlで宣言したiframeを取得
	    var iframe = document.getElementById('myframe');
	    // iframeから「企業コード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("#code").value = "650100";
	    // iframeから「パスワード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("#pass").value = "Hgmochi0320";
	    // iframeから「form」コンポーネントを取得
	    var nodes = iframe.contentWindow.document.getElementsByTagName("form");
	    // submitするためのノードを取得
	    var form_node = nodes[0];
	    //サブミットを実行し、画面遷移
	    form_node.submit();
	}

	// 逐次実行される画面遷移処理１（ログイン）
	function step2(){
		// index.htmlで宣言したiframeを取得
	    var iframe = document.getElementById('myframe');
	    var nodes = iframe.contentWindow.document.querySelector("#teroparea");
	    var node = nodes.getElementsByTagName("a");
	}
	//-------------以上　プライベート　スコープ---------------
	//-------------以下　パブリック　スコープ---------------
	return {
		next:function(){
			go.next();
		}
	};
	//-------------以上　パブリック　スコープ---------------
};

//jassoプラン用 モジュールパターンで実装
function Jasso(){
	
	//-------------以下　プライベート　スコープ---------------
	// ボタン押下とともに逐次実行されるアクション（画面遷移）
	var step = [step1,step2];
	var go = todo(step);

	// 逐次実行される画面遷移処理１（ログイン）
	function step1(){
		// index.htmlで宣言したiframeを取得
	    var iframe = document.getElementById('myframe');
	    // iframeから「企業コード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("#login_open_userId").value = "kira1028";
	    // iframeから「パスワード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("#login_open_password").value = "01ken014uMoka";
	    // iframeから「form」コンポーネントを取得
	    iframe.contentWindow.document.querySelector("#login_open_login_submit").click();
	}

	// 逐次実行される画面遷移処理１（ログイン）
	function step2(){
		// index.htmlで宣言したiframeを取得
	    var iframe = document.getElementById('myframe');
	    var nodes = iframe.contentWindow.document.querySelector("#contact");
	    clickATag(iframe,"a","https://scholar-ps.sas.jasso.go.jp/mypage/syosaiJoho_open.do");
	}
	//-------------以上　プライベート　スコープ---------------
	//-------------以下　パブリック　スコープ---------------
	return {
		next:function(){
			go.next();
		}
	};
	//-------------以上　パブリック　スコープ---------------
};

//ViewSuica用 モジュールパターンで実装
function ViewSuica(){
	
	//-------------以下　プライベート　スコープ---------------
	// ボタン押下とともに逐次実行されるアクション（画面遷移）
	var step = [step1,step2,step3,step4,step5,step6,step7];
	var go = todo(step);
	// 明細情報を格納する配列
	var meisai = {};
	// 明細情報のカウンター
	var meisai_counter = 0;


	// 逐次実行される画面遷移処理１（ログイン）
	function step1(){
		// index.htmlで宣言したiframeを取得
	    var iframe = document.getElementById('myframe');
	    // iframeから「企業コード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("input[type='text']").value = "AASB3188";
	    // iframeから「パスワード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("input[type='password']").value = "01ken014uMok";
	    // iframeから「form」コンポーネントを取得
	    iframe.contentWindow.document.querySelector("input[type='image']").click();
	}

	// 逐次実行される画面遷移処理１（明細照会へログイン）
	function step2(){
		// index.htmlで宣言したiframeを取得
	    var iframe = document.getElementById('myframe');
	    var nodes = iframe.contentWindow.document.querySelector("#vucServiceMenu_IMGV0600");
	    nodes.click();
	}
	// 逐次実行される画面遷移処理１（ログイン）
	function step3(){
		// index.htmlで宣言したiframeを取得
	    var iframe = document.getElementById('myframe');
	    clickATag(iframe,"a","id=V0300_002");
	}
	//
	function step4(){
	   var iframe = document.getElementById('myframe');
	   pulldown_option = iframe.contentWindow.document.getElementById("DdlClaimMonth");
       pulldown_option[1].selected = true;
       btn = iframe.contentWindow.document.getElementById("BtnList");
       btn.click();
	}
	//
	function step5(){
		var iframe = document.getElementById('myframe');
	    clickATag(iframe,"a","V0300_005");
	}
	//
	function step6(){
		var iframe = document.getElementById('myframe');
	    clickATag(iframe,"a","HlDetail");
	}
	// WEB画面のテーブルから、利用明細を取得する。
	// 対象のテーブルはclassが”listtable2”
	// テーブルの構造は、以下
	// 明細は２行で１組となっているため、i%2の商で処理を分岐している。
	// ---------------------
	// 1列目:テーブルタイトル１
	// 2列目:テーブルタイトル２
	// 3列目:不要列（利用者名とIDを表示）
	// 4列目:明細１
	// 5列目:明細２
	// ---------------------
	function step7(){
		// iframeの取得
		var iframe = document.getElementById('myframe');
		// ターゲットテーブルの取得
	    var tbl = iframe.contentWindow.document.querySelector(".listtable2");
	    // テーブル行配列の取得
		var rows = tbl.rows;
		// 行に対するループ
		for (var i=3, len=rows.length; i<len; i++) {
			var cols = rows[i].cells.length;
			// i%2の商が1のとき、つまり奇数行のときは、明細１
			if(i%2!=0){
				// 明細行のカウンターを１増加させる
				meisai_counter++;
				// 奇数行の時に、明細を初期化する。
				meisai[meisai_counter]={};
				// 列に対するループ（明細１）
		     	for (var j=0; j<cols; j++) {
		     		// セルから空白文字列を置換により削除して、連想配列へ格納。
		     		// この時、連想配列のキーは、明細１のヘッダーであるrows[0]
		     		meisai[meisai_counter][rows[0].cells[j].innerText.replace(/\s+/g, "")] = rows[i].cells[j].innerText.replace(/\s+/g, "");
		     	}
	    	} else　{
				// 列に対するループ（明細２）
		     	for (var j=0; j<cols; j++) {
		     		// セルから空白文字列を置換により削除して、連想配列へ格納。
		     		// この時、連想配列のキーは、明細２のヘッダーであるrows[1]
		     		meisai[meisai_counter][rows[1].cells[j].innerText.replace(/\s+/g, "")] = rows[i].cells[j].innerText.replace(/\s+/g, "");
		     	}
	    	}
		}
		step8();
	}

	// 次へボタンがある場合の処理は、再帰処理として実装する。
	function step8(){
		// iframeの取得
		var iframe = document.getElementById('myframe');
		// 次へボタンのid
		var next_btn_id = "LnkNextBottom";
		// 次へボタンがあるか確認
		if(iframe.contentWindow.document.getElementById(next_btn_id) != null){
			iframe.onload=step7;
			// 次へボタンによる画面遷移
			clickId(iframe,next_btn_id);
			//jQuery(document.getElementById('myframe').contentWindow.document).ready(step7());
			//setTimeout(step7(),3000);
				
		}else{
			insertdata(meisai);
			finddata();
		}
	}
	//-------------以上　プライベート　スコープ---------------
	//-------------以下　パブリック　スコープ---------------
	return {
		next:function(){
			go.next();
		}
	};
	//-------------以上　パブリック　スコープ---------------
};

// 
function clickATag(iframe,tag,href){
	var str="";
	var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
	for (var key in nodes) {
		str=""+nodes[key].href;
		if(str.indexOf(href)>=0){
			iframe.src=str;
		}
	};
}

function clickId(iframe,id){
	var str="";
	var nodes = iframe.contentWindow.document.getElementById(id);
	str = nodes.href;
	iframe.src=str;
}
// yieldによる同期処理の実装（ボタンの押下とともに逐次で実行される）
function* todo(step) {
	var i=0;
	//逐次実行されるイベントを可変に設定できるようforにてまわす
	for (i = 0; i < step.length; i++) {
		step[i]();
		yield i;
	};
	return i;
}

function insertdata(data){
	db.insert(data);
}

function finddata(){
	db.find({},function (err, docs) {
		console.log("show keys");
		console.log(docs);
	});
}

function dbclear(){
	db.remove({}, { multi: true }, function (err, numRemoved) {
		console.log(numRemoved);
	});
}