/// <reference path="../lib/typed/jquery.d.ts" />

module Greeting{
    export class Hello{
        constructor(private text : string){
        }
        say() : string{
            console.log(this.text);
            return this.text;
        }
    }
}
var hello : Greeting.Hello = new Greeting.Hello("Hello, World!");
hello.say();

$(function() { 
    $('#suica').on('click',
    function() {
        alert(hello.say());
    });
});