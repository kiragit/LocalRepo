/// <reference path="../typings/tsd.d.ts" />
// var jQuery :JQueryStatic = require('jquery');
//var execSync = require('child_process').execSync;
// yieldによる同期処理の実装（ボタンの押下とともに逐次で実行される）

module init{
	// yieldによる同期処理のインスタンス宣言（ボタンの押下とともに逐次で実行される）
	// 引数に逐次実行処理の配列を渡す。
	// index.htmlで宣言しているコンポーネントにアクションを追加する。
	// @ToDo
	// 現状では、ボタンのidとiframeのidを関連させることで、ボタンに登録するiframeインスタンスを取得しているが、
	// 本来であれば、<section>内のボタンとiframeを関連づけるべきである（構造により関連付けをするべき）
	export var readFiles = () => {
		//画面の読み込みを待ち、
		jQuery(document).ready(function () {
			// 画面リスト
			var pl = new Pages.PageList();
			this.pl =pl;
			// 画面のボタンを取得
			var all_btn : NodeList  = document.querySelectorAll("input[type='button']");
			// 全てのボタンにアクションを追加
			for (var i = 0; i < all_btn.length; i++) {
				// iframeのid
				var aBtn : HTMLElement = <HTMLElement>all_btn[i];
				var iframe_name : string = aBtn.id.replace(/_btn$/, "");
				// iframeに指定されたurlを取得
				console.log(i+" "+iframe_name+" "+all_btn[i]);
				if(iframe_name != null){
					var key:string =(<any>document.getElementById(iframe_name)).src;
					this.key = <string>key;
					// 取得したurlからデータ取得したいインスタンスを設定
					//var page=pages[key];
					if(aBtn.className=="next"){
						//ボタンへアクションの追加をする。
						//var page1 = new Pages.ViewSuica();
						console.log(this.key);
						console.log(this.pl);
						//var page1 = this.pl.getPage(this.key);
						//this.page1 = page1;
						//aBtn.addEventListener("click",(e)=>{this.pl.getPage((<any>document.getElementById(iframe_name)).src).next()}, false);
						aBtn.addEventListener("click",(e)=>{this.pl.getPage((<HTMLElement>e.target).id.replace(/_btn$/, "")).next()}, false);
					}else if(aBtn.className=="auto"){
						//ボタンへアクションの追加をする。
						//all_btn[i].addEventListener("click",page.auto(), false);	
						aBtn.addEventListener("click",function(){console.log("click")}, false);	
					}
				}
			};
			DB.DBM.finddata("myframe_viewsuica_data",View.print.onlyShow);
			DB.DBM.finddata("myframe_viewsuica_data",output.txt.do);
			OffLine.pdf2txt.do("/Users/kentarokira/Git/LocalRepo/project/KyuuyoPdf2Txt/maketxt.command");
		});
	}
}

module DB{

	// データベースの準備
	var Datastore = require('nedb')
	, path = require('path')
	, db = new Datastore({ filename: path.join(require('nw.gui').App.dataPath, 'iframe2.db') , autoload: true});

	export class DBM{
		constructor (){
		}
		// データを登録する
		public static insertdata(data){
			//データを登録
			db.insert(data);
		}

		// データを検索する
		public static finddata(id:string,callback):any{
			db.find({},function(err,docs){
				console.log(docs);
				callback(id,docs);
			});
		}

		// データを削除する
		public static dbclear(){
			db.remove({}, { multi: true }, function (err, numRemoved) {
				console.log(numRemoved);
			});
		}a
	};
}

module View {
	export class print{
		constructor (){
		}
		public static onlyShow(id:string,data):void{
			var my_Table:HTMLTableElement = <HTMLTableElement>document.getElementById(id);
			for( var ymd in data ){
				for (var row in data[ymd]) {
					my_Table.insertRow(-1);
					var newrow:any = my_Table.rows[my_Table.rows.length-1];
					console.log(data[ymd][row]);
					for(var col in data[ymd][row]){
						newrow.insertCell(-1);
						newrow.cells[newrow.cells.length -1].innerText = data[ymd][row][col];
					}
				}
			}
		}
	}
}
module Pages{

	// WEBページを著すクラス
	export class Page{
		// 画面遷移回数
		counter:number=0;
		// 画面遷移ごの操作手順関数群
		constructor (public step : any[]){
		}
		// 操作手順関数の逐次処理tikujisyori
		next () : void{
			console.log(this.counter);
			this.step[this.counter](this);
			this.counter++;
		}

		// iframe内の指定したタグを走査し、hrefに指定した文字列を含むコンポーネントをsubmitする。
		public submitHrefIfFound(iframe,tag,keyValue){
			var str="";
			var search_key=keyValue[0];
			var search_value=keyValue[1];
			var nodes : any[] = iframe.contentWindow.document.getElementsByTagName(tag);
			for (var key in nodes) {
				str=""+nodes[key].action;
				//console.log(nodes[key]);
				console.log(str);
				if(str.indexOf(search_value)>=0){
					nodes[key].submit();
				}
			};
		}

		// iframe内の指定したタグを走査し、hrefに指定した文字列を含むコンポーネントをクリックする。
		// 前提条件として、コンポーネントはclickイベントが設定されている必要がある。
		// クリックイベントが設定されていない場合は、「iframe.src=href]を実行する必要があるため、
		// 別途用意した共通関数を用いること
		public clickHrefIfFound(iframe,tag,href){
			var str="";
			var nodes : any[] = iframe.contentWindow.document.getElementsByTagName(tag);
			for (var key in nodes) {
				str=""+nodes[key].href;
				if(str.indexOf(href)>=0){
					nodes[key].click();
				}
			};
		}
		public clickSrcIfFound(iframe,tag,src){
			var str="";
			var nodes :any[] = iframe.contentWindow.document.getElementsByTagName(tag);
			for (var key in nodes) {
				str=""+nodes[key].src;
				if(str.indexOf(src)>=0){
					nodes[key].click();
				}
			};
		}
		// iframe内の指定したタグを走査し、指定した文字列を含むhrefへ画面遷移する。
		public moveHrefByTagsIfFound(iframe,tag,href){
			var str="";
			var nodes : any[] = iframe.contentWindow.document.getElementsByTagName(tag);
			for (var key in nodes) {
				str=""+nodes[key].href;
				if(str.indexOf(href)>=0){
					iframe.src=str;
				}
			};
		}
		// iframe内の指定したタグを走査し、指定した文字列を含むsrcへ画面遷移する。
		public moveSrcByTagsIfFound(iframe,tag,src){
			var str="";
			var nodes :any[] = iframe.contentWindow.document.getElementsByTagName(tag);
			for (var key in nodes) {
				str=""+nodes[key].src;
				if(str.indexOf(src)>=0){
					iframe.src=str;
				}
			};
		}
		// iframe内のタグをidで検索し、そこに含まれるhrefへ画面遷移をする。
		// 画面遷移後に、callback関数を実行する。
		public moveHrefAndExecCallback(iframe,id,callback){
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
		// iframe名からiframeオブジェクトを返す
		public getIframe(iframe_name) : HTMLElement{
			console.log(iframe_name);
			return <HTMLElement>document.getElementById(iframe_name);
		}
	}

	//野村Eプラン用 モジュールパターンで実装
	export class EplanNomura extends Page{
		
		//step;
		meisai;
		meisai_counter;
		iframe;
		iframe_name;
		
		constructor (){
			// ボタン押下とともに逐次実行されるアクション（画面遷移）
			this.step = [this.step1,this.step2,this.step3,this.step4];
			super(this.step);
			// 明細情報を格納する配列。「次へ」ボタンにより全ての画面を走査し、明細を格納する。
			this.meisai = {};
			// 明細情報のカウンター
			this.meisai_counter = 0;
			// iframe
			this.iframe;
			// iframeのid
			this.iframe_name = "myframe_epn";
		}


		// 逐次実行される画面遷移処理１（ログイン）
		step1(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // iframeから「企業コード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.getElementById("code").value = "650100";
		    // iframeから「パスワード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.getElementById("pass").value = "Hgmochi0320";
		    // submitによる画面遷移
		    super.submitHrefIfFound(_this.iframe,"form",["action","weastdmap.asp"]);
		}

		// 逐次実行される画面遷移処理２（残高照会画面へ遷移）
		step2(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    super.moveHrefByTagsIfFound(_this.iframe,"iframe","balancecheck2.html");
		}
		// 逐次実行される画面遷移処理３（残高照会画面へ遷移）
		step3(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // submitによる画面遷移
		    super.submitHrefIfFound(_this.iframe,"form",["action","WEAW1101.jsp"]);
		}
		// 逐次実行される画面遷移処理４（ログイン）
		step4(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // iframeから「企業コード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.getElementById("code").value = "353101017";
		    // iframeから「パスワード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.getElementById("pass").value = "01ken014uMoka";
		    // submitによる画面遷移
		    super.clickSrcIfFound(_this.iframe,"input","btn_login.gif");
		}
	}

	//jassoプラン用 モジュールパターンで実装
	export class Jasso extends Page{
		
		//step;
		meisai;
		meisai_counter;
		iframe;
		iframe_name;
		
		constructor (){
			// ボタン押下とともに逐次実行されるアクション（画面遷移）
			this.step = [this.step1,this.step2];
			super(this.step);
			// 逐次実行用にyieldを実装したインスタンスを生成
			//this.go = new todo(this.step);
			// 明細情報を格納する配列。「次へ」ボタンにより全ての画面を走査し、明細を格納する。
			this.meisai = {};
			// 明細情報のカウンター
			this.meisai_counter = 0;
			// iframe
			this.iframe;
			// iframeのid
			this.iframe_name = "myframe_jasso";
		}

		// 逐次実行される画面遷移処理１（ログイン）
		step1(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // iframeから「企業コード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.querySelector("#login_open_userId").value = "kira1028";
		    // iframeから「パスワード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.querySelector("#login_open_password").value = "01ken014uMoka";
		    // iframeから「form」コンポーネントを取得
		    _this.iframe.contentWindow.document.querySelector("#login_open_login_submit").click();
		}

		// 逐次実行される画面遷移処理１（ログイン）
		step2(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    var nodes : any[] = _this.iframe.contentWindow.document.querySelector("#contact");
		    super.clickHrefIfFound(_this.iframe,"a","syosaiJoho_open.do");
		}
	};

	//ViewSuica用 モジュールパターンで実装
	export class ViewSuica extends Page{

		//step;
		meisai;
		meisai_counter;
		iframe;
		iframe_name;
		
		constructor (){
			// ボタン押下とともに逐次実行されるアクション（画面遷移）
			this.step = [this.step1,this.step2,this.step3,this.step4,this.step5,this.step6];
			super(this.step);
			// 逐次実行用にyieldを実装したインスタンスを生成
			//this.go = new todo(this.step);
			// 明細情報を格納する配列。「次へ」ボタンにより全ての画面を走査し、明細を格納する。
			this.meisai = {};
			// 明細情報のカウンター
			this.meisai_counter = 0;
			// iframe
			this.iframe;
			// iframeのid
			this.iframe_name = "myframe_viewsuica";
			//this.iframe_name=iframe_name;
		}

		// 逐次実行される画面遷移処理１（ログイン）
		step1(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // iframeから「企業コード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.querySelector("input[type='text']").value = "AASB3188";
		    // iframeから「パスワード」の入力コンポーネントを取得
		    _this.iframe.contentWindow.document.querySelector("input[type='password']").value = "01ken014uMok";
		    // iframeから「form」コンポーネントを取得
		    _this.iframe.contentWindow.document.querySelector("input[type='image']").click();
		}

		// 逐次実行される画面遷移処理２（明細照会へ）
		step2(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // ボタンを押下
		    _this.iframe.contentWindow.document.querySelector("#vucServiceMenu_IMGV0600").click();
		}
		// 逐次実行される画面遷移処理３（ログイン）
		step3(_this){
			// index.htmlで宣言したiframeを取得
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // タグを探して、hrefに記載されたurlに遷移
		    super.clickHrefIfFound(_this.iframe,"a","V0300_002");
		}
		// 逐次実行される画面遷移処理４
		step4(_this){
		   _this.iframe = super.getIframe(_this.iframe_name);
		   var pulldown_option : Node = _this.iframe.contentWindow.document.getElementById("DdlClaimMonth");
	       pulldown_option[2].selected = true;
	       var btn : any = _this.iframe.contentWindow.document.getElementById("BtnList");
	       btn.click();
		}
		// 逐次実行される画面遷移処理５
		step5(_this){
		    _this.iframe = super.getIframe(_this.iframe_name);
		    // タグを探して、hrefに記載されたurlに遷移
		    super.clickHrefIfFound(_this.iframe,"a","V0300_005");
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
		step6(_this){
			// iframeの取得
		    _this.iframe = super.getIframe(_this.iframe_name);
			// ターゲットテーブルの取得
		    var tbl : any = _this.iframe.contentWindow.document.querySelector(".listtable2");
		    // テーブル行配列の取得
			var rows = tbl.rows;
			// 行に対するループ
			for (var i=3, len=rows.length; i<len; i++) {
				var cols = rows[i].cells.length;
				// i%2の商が1のとき、つまり奇数行のときは、明細１
				if(i%2!=0){
					// 明細行のカウンターを１増加させる
					_this.meisai_counter++;
					// 奇数行の時に、明細を初期化する。
					_this.meisai[_this.meisai_counter]={};
					// 列に対するループ（明細１）
			     	for (var j=0; j<cols; j++) {
			     		// セルから空白文字列を置換により削除して、連想配列へ格納。
			     		// この時、連想配列のキーは、明細１のヘッダーであるrows[0]
			     		_this.meisai[_this.meisai_counter][rows[0].cells[j].innerText.replace(/\s+/g, "")] = rows[i].cells[j].innerText.replace(/\s+/g, "");
			     	}
		    	} else　{
					// 列に対するループ（明細２）
			     	for (var j=0; j<cols; j++) {
			     		// セルから空白文字列を置換により削除して、連想配列へ格納。
			     		// この時、連想配列のキーは、明細２のヘッダーであるrows[1]
			     		_this.meisai[_this.meisai_counter][rows[1].cells[j].innerText.replace(/\s+/g, "")] = rows[i].cells[j].innerText.replace(/\s+/g, "");
			     	}
		    	}
			}
			_this.step7(_this);
		}

		// 次へボタンがある場合の処理は、再帰処理として実装する。
		step7(_this){
			// iframeの取得
		    //var iframe = document.getElementById('myframe');
		    _this.iframe = super.getIframe(_this.iframe_name);
			// 次へボタンのid
			var next_btn_id = "LnkNextBottom";
			// 次へボタンがあるか確認
			if(_this.iframe.contentWindow.document.getElementById(next_btn_id) != null){
				// 次へボタンによる画面遷移.画面遷移後にstepが実行される
				super.moveHrefAndExecCallback(_this.iframe,next_btn_id,_this.step6);
			//次へボタンが無い場合は、明細情報をデータベースへ格納する
			}else{
				DB.DBM.insertdata(_this.meisai);
				//var list = document.getElementById("myframe_viewsuica_li");
				View.print.onlyShow("myframe_viewsuica_li",_this.meisai);
			}
		}
	};


	// WEBサイトを管理するクラス
	export class PageList{

		//WEBページ
		jasso;
		epn;
		view;

		//WEBページの初期
		constructor(){
			this.jasso = new Jasso();
			this.epn = new EplanNomura();
			this.view = new ViewSuica();
		}

		//WEBページの返却
		getPage(key):any{
			switch(key){
		// 独立行政法人日本学生支援機構のインスタンスを生成
				case "myframe_jasso":
					return this.jasso;
					break;
		// 持株WEBサービス 野村証券のインスタンスを生成
				case 'myframe_epn':
					return this.epn;
					break;
		// インターネットサービス「VIEW's NET」のインスタンスを生成
				case "myframe_viewsuica":
					return this.view;
					break;
		// 独立行政法人日本学生支援機構のインスタンスを生成
				case "https://scholar-ps.sas.jasso.go.jp/mypage/login_open.do":
					return this.jasso;
					break;
		// 持株WEBサービス 野村証券のインスタンスを生成
				case 'https://www.e-plan.nomura.co.jp/e-forum/html/index.html':
					return this.epn;
					break;
		// インターネットサービス「VIEW's NET」のインスタンスを生成
				case "https://viewsnet.jp/default.htm":
					return this.view;
					break;
			}
		}
	}
}

module OffLine{
	var exec = require('child_process').exec;
	export class pdf2txt{
		constructor(){

		}

		public static do(shell:string){
			exec(shell,function(err, stdout, stderr){
				console.log(stdout);
			});
		}
	}
}

module output{
	var fs = require('fs');
	export class txt{
		constructor(){

		}
		public static do(id,data){
			fs.appendFile('/Users/kentarokira/Git/LocalRepo/project/webscraiping/iframe/app/writetest.txt', JSON.stringify(data), function (err) {
			    console.log(err);
			});
		}
	}
}
// index.htmlで宣言しているボタンにアクションを追加する。
init.readFiles();