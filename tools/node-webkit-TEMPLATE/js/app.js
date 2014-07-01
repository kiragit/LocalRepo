var Greeting;
(function (Greeting) {
    var Hello = (function () {
        function Hello(text) {
            this.text = text;
        }
        Hello.prototype.say = function () {
            console.log(this.text);
            return this.text;
        };
        return Hello;
    })();
    Greeting.Hello = Hello;
})(Greeting || (Greeting = {}));
var hello = new Greeting.Hello("Hello, World!");
hello.say();

$(function () {
    $('#suica').on('click', function () {
        alert(hello.say());
    });
});
