var g=N();
console.log(g);
console.log(g.next());
console.log(g.next());
console.log(g.next());

function* N() {
yield 1;
console.log("after 1");
n1();
yield 2;
console.log("after 2");
n1();
return 3;
}

function n1(){
  i = 0;
  while (i < 1000000000) {
    i = i + 1;
    if(i%100000000==0){
      console.log(i);
    }
  }
  console.log(i);
}
