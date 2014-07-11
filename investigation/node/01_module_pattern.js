var sample01=(function(){

  var name="no name";
  var privateVar = 0;

  function privateFunc(){
    console.log("this is private function. count is " + privateVar);
  }

  return{
    pub : "publice",
    publicFunc : function (){
      privateVar++;
      privateFunc();
    },
    getName : function(){
      console.log("I am "+name);
    },
    setName : function(args){
      name = args;
    }
  };
});

var sample = new sample01();
sample.publicFunc();
sample.publicFunc();
sample.publicFunc();
sample.publicFunc();
sample.publicFunc();
sample.publicFunc();
sample.getName();
sample.setName("kentaro");
sample.getName();
