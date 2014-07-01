var timerCallback = function(cb) {
    setTimeout(
               function() {
               cb(100);
               },
               2500
               );
};

describe("describe1", function() {
         
         beforeEach(function() {
                    console.log("beforeEach");
                    });
         
         before(function() {
                console.log("before");
                });
         
         afterEach(function() {
                   console.log("afterEach");
                   });
         
         after(function() {
               console.log("after");
               });
         
         it("should 1+1 = 2", function() {
            expect(1 + 1).to.equal(2);
            });
         
         it("should 2*2 = 4", function(done) {
            setTimeout(
                       function() {
                       expect(2 * 2).to.equal(4);
                       
                       done();
                       },
                       1000
                       );
            });
         
         it("timerCallbackを実行してみる", function(done) {
            this.timeout(3000); // 非同期テストの際のタイムアウト値を設定。設定しない場合にはデフォルトで2000msな模様
            
            timerCallback(function(x) {
                          expect(x).to.equal(100);
                          
                          done();
                          });
            });
         
         it("XMLHttpRequestしてみる", function(done) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "http://localhost:5000/", true);
            xhr.onload = function() {
            expect(this.status).to.equal(200);
            
            done();
            };
            xhr.send(null);
            });
         
         it("後で");
         
         it("適当なオブジェクトのテスト", function() {
            var o = { "name": "hoge" };
            
            expect(o).to.be.a("object");
            expect(o).to.have.property("name");
            expect(o.name).to.have.length(4);
            expect(o.name).to.equal("hoge");
            });
         });