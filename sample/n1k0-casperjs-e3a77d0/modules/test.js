var casper = require('casper').create();
 
casper.start("http://www.yahoo.co.jp",function(){
    var text = this.evaluate(function(){
        return __utils__.getElementByXPath('//*[@id="topicsfb"]/div[1]/ul[1]/li[1]/a').innerHTML;
    });
    this.echo(text);
});
casper.run();