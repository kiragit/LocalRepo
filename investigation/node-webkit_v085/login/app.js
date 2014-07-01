var A = (function () {
    function A() {
        this.proterty = "A";
    }
    A.prototype.method1 = function () {
        console.log("hello world !!");
    };
    A.prototype.method2 = function () {
    };
    return A;
})();

var a = new A();
a.method1();
//# sourceMappingURL=app.js.map
