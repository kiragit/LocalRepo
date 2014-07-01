var koa = require('koa');
var app = koa();
 
//1
app.use(function *(next){
  console.log('>> one');
  yield next;
  console.log('<< one');
});
//2
app.use(function *(next){
  console.log('>> two');
  yield next;
  console.log('<< two');
});
//3
app.use(function *(next){
  console.log('>> three');
  yield next;
  console.log('<< three');
});
//4
app.use(function *(){
  console.log("set body!!");
  this.body = 'Hello World';
});
 
app.listen(3000);