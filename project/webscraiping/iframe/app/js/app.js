// index.htmlで宣言しているボタンにアクションを追加する。
readFiles();

// 持株WEBサービス 野村証券のインスタンスを生成
var epn = new EplanNomura;
// 独立行政法人日本学生支援機構のインスタンスを生成
var jasso = new Jasso;
// インターネットサービス「VIEW's NET」のインスタンスを生成
var viewsuica = new ViewSuica;
// データベースの準備
var Datastore = require('nedb')
, path = require('path')
, db = new Datastore({ filename: path.join(require('nw.gui').App.dataPath, 'iframe2.db') , autoload: true});

// 対象サイト毎に画面遷移の仕方がことなるため、別々のインスタンスとして用意
var pages = {
	// 独立行政法人日本学生支援機構
	'https://scholar-ps.sas.jasso.go.jp/mypage/login_open.do':jasso,
	// 持株WEBサービス 野村証券
	'https://www.e-plan.nomura.co.jp/e-forum/html/index.html':epn,
	// インターネットサービス「VIEW's NET」
	'https://viewsnet.jp/default.htm':viewsuica
}

// yieldによる同期処理のインスタンス宣言（ボタンの押下とともに逐次で実行される）
// 引数に逐次実行処理の配列を渡す。
// var doing=todo(epn.getStep());

// index.htmlで宣言しているコンポーネントにアクションを追加する。
// @ToDo
// 現状では、ボタンのidとiframeのidを関連させることで、ボタンに登録するiframeインスタンスを取得しているが、
// 本来であれば、<section>内のボタンとiframeを関連づけるべきである（構造により関連付けをするべき）
function readFiles(){
	//画面の読み込みを待ち、
	jQuery(document).ready(function () {
		// 画面のボタンを取得
		all_btn = document.querySelectorAll("input[type='button']");
		// 全てのボタンにアクションを追加
		for (var i = 0; i < all_btn.length; i++) {
			// iframeのid
			var iframe_name=all_btn[i].id.replace(/_btn$/, "");
			// iframeに指定されたurlを取得
			var key=document.getElementById(iframe_name).src;
			// 取得したurlからデータ取得したいインスタンスを設定
			var page=pages[key];
			//ボタンへアクションの追加をする。
			all_btn[i].addEventListener("click",page.next, false);
		};
	});
}

//野村Eプラン用 モジュールパターンで実装
function EplanNomura(){
	
	//-------------以下　プライベート　スコープ---------------
	// ボタン押下とともに逐次実行されるアクション（画面遷移）
	var step = [step1,step2];
	// 逐次実行用にyieldを実装したインスタンスを生成
	var go = todo(step);
	// iframe
	var iframe;
	// iframeのid
	var iframe_name = "myframe_epn";

	// 逐次実行される画面遷移処理１（ログイン）
	function step1(){
		// index.htmlで宣言したiframeを取得
	    iframe = getIframe(iframe_name);
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
	    iframe = getIframe(iframe_name);
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
	// 逐次実行用にyieldを実装したインスタンスを生成
	var go = todo(step);
	// iframe
	var iframe;
	// iframeのid
	var iframe_name = "myframe_jasso";

	// 逐次実行される画面遷移処理１（ログイン）
	function step1(){
		// index.htmlで宣言したiframeを取得
	    iframe = getIframe(iframe_name);
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
	    iframe = getIframe(iframe_name);
	    var nodes = iframe.contentWindow.document.querySelector("#contact");
	    clickHrefIfFound(iframe,"a","syosaiJoho_open.do");
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
	var step = [step1,step2,step3,step4,step5,step6];
	// 逐次実行用にyieldを実装したインスタンスを生成
	var go = todo(step);
	// 明細情報を格納する配列。「次へ」ボタンにより全ての画面を走査し、明細を格納する。
	var meisai = {};
	// 明細情報のカウンター
	var meisai_counter = 0;
	// iframe
	var iframe;
	// iframeのid
	var iframe_name = "myframe_viewsuica";

	// 逐次実行される画面遷移処理１（ログイン）
	function step1(){
		// index.htmlで宣言したiframeを取得
	    iframe = getIframe(iframe_name);
	    // iframeから「企業コード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("input[type='text']").value = "AASB3188";
	    // iframeから「パスワード」の入力コンポーネントを取得
	    iframe.contentWindow.document.querySelector("input[type='password']").value = "01ken014uMok";
	    // iframeから「form」コンポーネントを取得
	    iframe.contentWindow.document.querySelector("input[type='image']").click();
	}

	// 逐次実行される画面遷移処理２（明細照会へ）
	function step2(){
		// index.htmlで宣言したiframeを取得
	    iframe = getIframe(iframe_name);
	    // ボタンを押下
	    iframe.contentWindow.document.querySelector("#vucServiceMenu_IMGV0600").click();
	}
	// 逐次実行される画面遷移処理３（ログイン）
	function step3(){
		// index.htmlで宣言したiframeを取得
	    iframe = getIframe(iframe_name);
	    // タグを探して、hrefに記載されたurlに遷移
	    moveHrefByTagsIfFound(iframe,"a","id=V0300_002");
	}
	// 逐次実行される画面遷移処理４
	function step4(){
	    iframe = getIframe(iframe_name);
	   pulldown_option = iframe.contentWindow.document.getElementById("DdlClaimMonth");
       pulldown_option[1].selected = true;
       btn = iframe.contentWindow.document.getElementById("BtnList");
       btn.click();
	}
	// 逐次実行される画面遷移処理５
	function step5(){
	    iframe = getIframe(iframe_name);
	    // タグを探して、hrefに記載されたurlに遷移
	    moveHrefByTagsIfFound(iframe,"a","V0300_005");
	}
	// 逐次実行される画面遷移処理６
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
	function step6(){
		// iframeの取得
	    iframe = getIframe(iframe_name);
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
		step7();
	}

	// 次へボタンがある場合の処理は、再帰処理として実装する。
	function step7(){
		// iframeの取得
	    //var iframe = document.getElementById('myframe');
	    iframe = getIframe(iframe_name);
		// 次へボタンのid
		var next_btn_id = "LnkNextBottom";
		// 次へボタンがあるか確認
		if(iframe.contentWindow.document.getElementById(next_btn_id) != null){
			// 次へボタンによる画面遷移.画面遷移後にstep7が実行される
			moveHrefAndExecCallback(iframe,next_btn_id,step6);
		//次へボタンが無い場合は、明細情報をデータベースへ格納する
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
function clickHrefIfFound(iframe,tag,href){
	var str="";
	var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
	for (var key in nodes) {
		str=""+nodes[key].href;
		if(str.indexOf(href)>=0){
			nodes[key].click();
		}
	};
}
// iframe内の指定したタグを走査し、指定した文字列を含むhrefへ画面遷移する。
function moveHrefByTagsIfFound(iframe,tag,href){
	var str="";
	var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
	for (var key in nodes) {
		str=""+nodes[key].href;
		if(str.indexOf(href)>=0){
			iframe.src=str;
		}
	};
}
// iframe内のタグをidで検索し、そこに含まれるhrefへ画面遷移をする。
// 画面遷移後に、callback関数を実行する。
function moveHrefAndExecCallback(iframe,id,callback){
	// 次ステップで画面遷移したあとに、callbackが実行されることで同期処理となっている。
	iframe.onload=callback;
	// 遷移先のURLを格納のため
	var str="";
	// idから遷移先のurlを含むコンポーネントを取得
	var nodes = iframe.contentWindow.document.getElementById(id);
	// 遷移先のurlを取得
	str = nodes.href;
	// iframeのsrcに新たにurlを設定し、画面遷移を実行
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

// iframe名からiframeオブジェクトを返す。.
function getIframe(iframe_name){
	return document.getElementById(iframe_name);
}

// データを登録する
function insertdata(data){
	console.log("--insert--");
	db.insert(data);
}

// データを検索する
function finddata(){
	db.find({},function (err, docs) {
		console.log("--find--");
		console.log(docs);
	});
}

// データを削除する
function dbclear(){
	db.remove({}, { multi: true }, function (err, numRemoved) {
		console.log(numRemoved);
	});
}