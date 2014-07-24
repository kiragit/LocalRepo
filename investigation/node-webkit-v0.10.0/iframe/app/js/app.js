// index.htmlで宣言しているボタンにアクションを追加する。
readFiles();

//野村Eプランのインスタンスを生成
var epn = new EplanNomura;
var jasso = new Jasso;
var viewsuica = new ViewSuica;

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
	var step = [step1,step2,step3,step4,step5,step6];
	var go = todo(step);

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
	    //iframe.src="https://viewsnet.jp/V0100/V0100_010.aspx?Pid=V0300_002&sv=w14";
	    //clickATag(iframe,"a","");
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
		console.log("click a tag "+str);
		if(str.indexOf(href)>=0){
			iframe.src=str;
		}
	};
}

// yieldによる同期処理の実装（ボタンの押下とともに逐次で実行される）
function* todo(step) {
	var i=0;
	//逐次実行されるイベントを可変に設定できるようforにてまわす
	for (i = 0; i < step.length; i++) {
		yield i;
		step[i]();
	};
	return i;
}