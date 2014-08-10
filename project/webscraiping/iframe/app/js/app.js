/// <reference path="../typings/tsd.d.ts" />
// var jQuery :JQueryStatic = require('jquery');
//var execSync = require('child_process').execSync;
// yieldによる同期処理の実装（ボタンの押下とともに逐次で実行される）
var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
var init;
(function (init) {
    // yieldによる同期処理のインスタンス宣言（ボタンの押下とともに逐次で実行される）
    // 引数に逐次実行処理の配列を渡す。
    // index.htmlで宣言しているコンポーネントにアクションを追加する。
    // @ToDo
    // 現状では、ボタンのidとiframeのidを関連させることで、ボタンに登録するiframeインスタンスを取得しているが、
    // 本来であれば、<section>内のボタンとiframeを関連づけるべきである（構造により関連付けをするべき）
    init.readFiles = function () {
        //画面の読み込みを待ち、
        jQuery(document).ready(function () {
            var _this = this;
            // 画面リスト
            var pl = new Pages.PageList();
            this.pl = pl;

            // 画面のボタンを取得
            var all_btn = document.querySelectorAll("input[type='button']");

            for (var i = 0; i < all_btn.length; i++) {
                // iframeのid
                var aBtn = all_btn[i];
                var iframe_name = aBtn.id.replace(/_btn$/, "");

                // iframeに指定されたurlを取得
                console.log(i + " " + iframe_name + " " + all_btn[i]);
                if (iframe_name != null) {
                    var key = document.getElementById(iframe_name).src;
                    this.key = key;

                    // 取得したurlからデータ取得したいインスタンスを設定
                    //var page=pages[key];
                    if (aBtn.className == "next") {
                        //ボタンへアクションの追加をする。
                        //var page1 = new Pages.ViewSuica();
                        console.log(this.key);
                        console.log(this.pl);

                        //var page1 = this.pl.getPage(this.key);
                        //this.page1 = page1;
                        //aBtn.addEventListener("click",(e)=>{this.pl.getPage((<any>document.getElementById(iframe_name)).src).next()}, false);
                        aBtn.addEventListener("click", function (e) {
                            _this.pl.getPage(e.target.id.replace(/_btn$/, "")).next();
                        }, false);
                    } else if (aBtn.className == "auto") {
                        //ボタンへアクションの追加をする。
                        //all_btn[i].addEventListener("click",page.auto(), false);
                        aBtn.addEventListener("click", function () {
                            console.log("click");
                        }, false);
                    }
                }
            }
            ;
            DB.DBM.finddata("myframe_viewsuica_data", View.print.onlyShow);
            DB.DBM.finddata("myframe_viewsuica_data", output.txt.do);
            OffLine.pdf2txt.do("/Users/kentarokira/Git/LocalRepo/project/KyuuyoPdf2Txt/maketxt.command");
        });
    };
})(init || (init = {}));

var DB;
(function (DB) {
    // データベースの準備
    var Datastore = require('nedb'), path = require('path'), db = new Datastore({ filename: path.join(require('nw.gui').App.dataPath, 'iframe2.db'), autoload: true });

    var DBM = (function () {
        function DBM() {
        }
        // データを登録する
        DBM.insertdata = function (data) {
            //データを登録
            db.insert(data);
        };

        // データを検索する
        DBM.finddata = function (id, callback) {
            db.find({}, function (err, docs) {
                console.log(docs);
                callback(id, docs);
            });
        };

        // データを削除する
        DBM.dbclear = function () {
            db.remove({}, { multi: true }, function (err, numRemoved) {
                console.log(numRemoved);
            });
        };
        return DBM;
    })();
    DB.DBM = DBM;
    ;
})(DB || (DB = {}));

var View;
(function (View) {
    var print = (function () {
        function print() {
        }
        print.onlyShow = function (id, data) {
            var my_Table = document.getElementById(id);
            for (var ymd in data) {
                for (var row in data[ymd]) {
                    my_Table.insertRow(-1);
                    var newrow = my_Table.rows[my_Table.rows.length - 1];
                    console.log(data[ymd][row]);
                    for (var col in data[ymd][row]) {
                        newrow.insertCell(-1);
                        newrow.cells[newrow.cells.length - 1].innerText = data[ymd][row][col];
                    }
                }
            }
        };
        return print;
    })();
    View.print = print;
})(View || (View = {}));
var Pages;
(function (Pages) {
    // WEBページを著すクラス
    var Page = (function () {
        // 画面遷移ごの操作手順関数群
        function Page(step) {
            this.step = step;
            // 画面遷移回数
            this.counter = 0;
        }
        // 操作手順関数の逐次処理tikujisyori
        Page.prototype.next = function () {
            console.log(this.counter);
            this.step[this.counter](this);
            this.counter++;
        };

        // iframe内の指定したタグを走査し、hrefに指定した文字列を含むコンポーネントをsubmitする。
        Page.prototype.submitHrefIfFound = function (iframe, tag, keyValue) {
            var str = "";
            var search_key = keyValue[0];
            var search_value = keyValue[1];
            var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
            for (var key in nodes) {
                str = "" + nodes[key].action;

                //console.log(nodes[key]);
                console.log(str);
                if (str.indexOf(search_value) >= 0) {
                    nodes[key].submit();
                }
            }
            ;
        };

        // iframe内の指定したタグを走査し、hrefに指定した文字列を含むコンポーネントをクリックする。
        // 前提条件として、コンポーネントはclickイベントが設定されている必要がある。
        // クリックイベントが設定されていない場合は、「iframe.src=href]を実行する必要があるため、
        // 別途用意した共通関数を用いること
        Page.prototype.clickHrefIfFound = function (iframe, tag, href) {
            var str = "";
            var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
            for (var key in nodes) {
                str = "" + nodes[key].href;
                if (str.indexOf(href) >= 0) {
                    nodes[key].click();
                }
            }
            ;
        };
        Page.prototype.clickSrcIfFound = function (iframe, tag, src) {
            var str = "";
            var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
            for (var key in nodes) {
                str = "" + nodes[key].src;
                if (str.indexOf(src) >= 0) {
                    nodes[key].click();
                }
            }
            ;
        };

        // iframe内の指定したタグを走査し、指定した文字列を含むhrefへ画面遷移する。
        Page.prototype.moveHrefByTagsIfFound = function (iframe, tag, href) {
            var str = "";
            var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
            for (var key in nodes) {
                str = "" + nodes[key].href;
                if (str.indexOf(href) >= 0) {
                    iframe.src = str;
                }
            }
            ;
        };

        // iframe内の指定したタグを走査し、指定した文字列を含むsrcへ画面遷移する。
        Page.prototype.moveSrcByTagsIfFound = function (iframe, tag, src) {
            var str = "";
            var nodes = iframe.contentWindow.document.getElementsByTagName(tag);
            for (var key in nodes) {
                str = "" + nodes[key].src;
                if (str.indexOf(src) >= 0) {
                    iframe.src = str;
                }
            }
            ;
        };

        // iframe内のタグをidで検索し、そこに含まれるhrefへ画面遷移をする。
        // 画面遷移後に、callback関数を実行する。
        Page.prototype.moveHrefAndExecCallback = function (iframe, id, callback) {
            // 次ステップで画面遷移したあとに、callbackが実行されることで同期処理となっている。
            iframe.onload = callback;

            // 遷移先のURLを格納のため
            var str = "";

            // idから遷移先のurlを含むコンポーネントを取得
            var nodes = iframe.contentWindow.document.getElementById(id);

            // 遷移先のurlを取得
            str = nodes.href;

            // iframeのsrcに新たにurlを設定し、画面遷移を実行
            iframe.src = str;
        };

        // iframe名からiframeオブジェクトを返す
        Page.prototype.getIframe = function (iframe_name) {
            console.log(iframe_name);
            return document.getElementById(iframe_name);
        };
        return Page;
    })();
    Pages.Page = Page;

    //野村Eプラン用 モジュールパターンで実装
    var EplanNomura = (function (_super) {
        __extends(EplanNomura, _super);
        function EplanNomura() {
            // ボタン押下とともに逐次実行されるアクション（画面遷移）
            this.step = [this.step1, this.step2, this.step3, this.step4];
            _super.call(this, this.step);

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
        EplanNomura.prototype.step1 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // iframeから「企業コード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.getElementById("code").value = "650100";

            // iframeから「パスワード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.getElementById("pass").value = "Hgmochi0320";

            // submitによる画面遷移
            _super.prototype.submitHrefIfFound.call(this, _this.iframe, "form", ["action", "weastdmap.asp"]);
        };

        // 逐次実行される画面遷移処理２（残高照会画面へ遷移）
        EplanNomura.prototype.step2 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);
            _super.prototype.moveHrefByTagsIfFound.call(this, _this.iframe, "iframe", "balancecheck2.html");
        };

        // 逐次実行される画面遷移処理３（残高照会画面へ遷移）
        EplanNomura.prototype.step3 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // submitによる画面遷移
            _super.prototype.submitHrefIfFound.call(this, _this.iframe, "form", ["action", "WEAW1101.jsp"]);
        };

        // 逐次実行される画面遷移処理４（ログイン）
        EplanNomura.prototype.step4 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // iframeから「企業コード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.getElementById("code").value = "353101017";

            // iframeから「パスワード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.getElementById("pass").value = "01ken014uMoka";

            // submitによる画面遷移
            _super.prototype.clickSrcIfFound.call(this, _this.iframe, "input", "btn_login.gif");
        };
        return EplanNomura;
    })(Page);
    Pages.EplanNomura = EplanNomura;

    //jassoプラン用 モジュールパターンで実装
    var Jasso = (function (_super) {
        __extends(Jasso, _super);
        function Jasso() {
            // ボタン押下とともに逐次実行されるアクション（画面遷移）
            this.step = [this.step1, this.step2];
            _super.call(this, this.step);

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
        Jasso.prototype.step1 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // iframeから「企業コード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.querySelector("#login_open_userId").value = "kira1028";

            // iframeから「パスワード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.querySelector("#login_open_password").value = "01ken014uMoka";

            // iframeから「form」コンポーネントを取得
            _this.iframe.contentWindow.document.querySelector("#login_open_login_submit").click();
        };

        // 逐次実行される画面遷移処理１（ログイン）
        Jasso.prototype.step2 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);
            var nodes = _this.iframe.contentWindow.document.querySelector("#contact");
            _super.prototype.clickHrefIfFound.call(this, _this.iframe, "a", "syosaiJoho_open.do");
        };
        return Jasso;
    })(Page);
    Pages.Jasso = Jasso;
    ;

    //ViewSuica用 モジュールパターンで実装
    var ViewSuica = (function (_super) {
        __extends(ViewSuica, _super);
        function ViewSuica() {
            // ボタン押下とともに逐次実行されるアクション（画面遷移）
            this.step = [this.step1, this.step2, this.step3, this.step4, this.step5, this.step6];
            _super.call(this, this.step);

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
        ViewSuica.prototype.step1 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // iframeから「企業コード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.querySelector("input[type='text']").value = "AASB3188";

            // iframeから「パスワード」の入力コンポーネントを取得
            _this.iframe.contentWindow.document.querySelector("input[type='password']").value = "01ken014uMok";

            // iframeから「form」コンポーネントを取得
            _this.iframe.contentWindow.document.querySelector("input[type='image']").click();
        };

        // 逐次実行される画面遷移処理２（明細照会へ）
        ViewSuica.prototype.step2 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // ボタンを押下
            _this.iframe.contentWindow.document.querySelector("#vucServiceMenu_IMGV0600").click();
        };

        // 逐次実行される画面遷移処理３（ログイン）
        ViewSuica.prototype.step3 = function (_this) {
            // index.htmlで宣言したiframeを取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // タグを探して、hrefに記載されたurlに遷移
            _super.prototype.clickHrefIfFound.call(this, _this.iframe, "a", "V0300_002");
        };

        // 逐次実行される画面遷移処理４
        ViewSuica.prototype.step4 = function (_this) {
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);
            var pulldown_option = _this.iframe.contentWindow.document.getElementById("DdlClaimMonth");
            pulldown_option[2].selected = true;
            var btn = _this.iframe.contentWindow.document.getElementById("BtnList");
            btn.click();
        };

        // 逐次実行される画面遷移処理５
        ViewSuica.prototype.step5 = function (_this) {
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // タグを探して、hrefに記載されたurlに遷移
            _super.prototype.clickHrefIfFound.call(this, _this.iframe, "a", "V0300_005");
        };

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
        ViewSuica.prototype.step6 = function (_this) {
            // iframeの取得
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // ターゲットテーブルの取得
            var tbl = _this.iframe.contentWindow.document.querySelector(".listtable2");

            // テーブル行配列の取得
            var rows = tbl.rows;

            for (var i = 3, len = rows.length; i < len; i++) {
                var cols = rows[i].cells.length;

                // i%2の商が1のとき、つまり奇数行のときは、明細１
                if (i % 2 != 0) {
                    // 明細行のカウンターを１増加させる
                    _this.meisai_counter++;

                    // 奇数行の時に、明細を初期化する。
                    _this.meisai[_this.meisai_counter] = {};

                    for (var j = 0; j < cols; j++) {
                        // セルから空白文字列を置換により削除して、連想配列へ格納。
                        // この時、連想配列のキーは、明細１のヘッダーであるrows[0]
                        _this.meisai[_this.meisai_counter][rows[0].cells[j].innerText.replace(/\s+/g, "")] = rows[i].cells[j].innerText.replace(/\s+/g, "");
                    }
                } else {
                    for (var j = 0; j < cols; j++) {
                        // セルから空白文字列を置換により削除して、連想配列へ格納。
                        // この時、連想配列のキーは、明細２のヘッダーであるrows[1]
                        _this.meisai[_this.meisai_counter][rows[1].cells[j].innerText.replace(/\s+/g, "")] = rows[i].cells[j].innerText.replace(/\s+/g, "");
                    }
                }
            }
            _this.step7(_this);
        };

        // 次へボタンがある場合の処理は、再帰処理として実装する。
        ViewSuica.prototype.step7 = function (_this) {
            // iframeの取得
            //var iframe = document.getElementById('myframe');
            _this.iframe = _super.prototype.getIframe.call(this, _this.iframe_name);

            // 次へボタンのid
            var next_btn_id = "LnkNextBottom";

            // 次へボタンがあるか確認
            if (_this.iframe.contentWindow.document.getElementById(next_btn_id) != null) {
                // 次へボタンによる画面遷移.画面遷移後にstepが実行される
                _super.prototype.moveHrefAndExecCallback.call(this, _this.iframe, next_btn_id, _this.step6);
                //次へボタンが無い場合は、明細情報をデータベースへ格納する
            } else {
                DB.DBM.insertdata(_this.meisai);

                //var list = document.getElementById("myframe_viewsuica_li");
                View.print.onlyShow("myframe_viewsuica_li", _this.meisai);
            }
        };
        return ViewSuica;
    })(Page);
    Pages.ViewSuica = ViewSuica;
    ;

    // WEBサイトを管理するクラス
    var PageList = (function () {
        //WEBページの初期
        function PageList() {
            this.jasso = new Jasso();
            this.epn = new EplanNomura();
            this.view = new ViewSuica();
        }
        //WEBページの返却
        PageList.prototype.getPage = function (key) {
            switch (key) {
                case "myframe_jasso":
                    return this.jasso;
                    break;

                case 'myframe_epn':
                    return this.epn;
                    break;

                case "myframe_viewsuica":
                    return this.view;
                    break;

                case "https://scholar-ps.sas.jasso.go.jp/mypage/login_open.do":
                    return this.jasso;
                    break;

                case 'https://www.e-plan.nomura.co.jp/e-forum/html/index.html':
                    return this.epn;
                    break;

                case "https://viewsnet.jp/default.htm":
                    return this.view;
                    break;
            }
        };
        return PageList;
    })();
    Pages.PageList = PageList;
})(Pages || (Pages = {}));

var OffLine;
(function (OffLine) {
    var exec = require('child_process').exec;
    var pdf2txt = (function () {
        function pdf2txt() {
        }
        pdf2txt.do = function (shell) {
            exec(shell, function (err, stdout, stderr) {
                console.log(stdout);
            });
        };
        return pdf2txt;
    })();
    OffLine.pdf2txt = pdf2txt;
})(OffLine || (OffLine = {}));

var output;
(function (output) {
    var fs = require('fs');
    var txt = (function () {
        function txt() {
        }
        txt.do = function (id, data) {
            fs.appendFile('/Users/kentarokira/Git/LocalRepo/project/webscraiping/iframe/app/writetest.txt', JSON.stringify(data), function (err) {
                console.log(err);
            });
        };
        return txt;
    })();
    output.txt = txt;
})(output || (output = {}));

// index.htmlで宣言しているボタンにアクションを追加する。
init.readFiles();
