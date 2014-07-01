(function (){

  var Task = Backbone.Model.extend({
    defaults:{
      title:'do something!',
      completed:false,
      status:false,
      adddate:new Date(),
      fadddate:dateFormat(new Date()),
      spendTime:0,
      lastModifiedTime:new Date(),
      flastModifiedTime:dateFormat(new Date())
    },
    validate: function(attrs){
        if(_.isEmpty(attrs.title)){
          return 'title must not be empty';
        }
    },
    initialize: function(){
      var date = new Date();
      this.set('adddate',date);
      this.set('fadddate',dateFormat(date));
      this.set('lastModifiedTime',date);
      this.set('flastModifiedTime',dateFormat(date));
      this.on('invalid',function(model, error){
        $('#error').html(error);
      });
    }
  });

  var Tasks = Backbone.Collection.extend({
    model:Task,
    localStorage: new Backbone.LocalStorage('todo')
  });

  var TaskView = Backbone.View.extend({
    tagName:'li',
    initialize:function(){
      /*-----------------------------------------------*/
      //object.on(event, callback, [context])Alias: bind
      //オブジェクトに登録したイベントが発生するたびに
      //コールバック関数が呼び出されます。
      /*-----------------------------------------------*/
      //destroyイベントが発生したら、removeを呼び出す
      this.model.on('destroy',this.remove,this);
      //changeイベントが発生したら、renderを呼び出す
      this.model.on('change',this.render,this);
    },
    template:_.template($('#task-template').html()),
    render:function(){
      var template=this.template(this.model.toJSON());
      this.$el.html(template);
      return this;
    },
    /*-----------------------------------------------*/
    //イベントが発生するたびに指定した間数が呼び出される。
    /*-----------------------------------------------*/
    events:{
      'click .delete': 'destroy',
      'click .toggle': 'toggle',
      'click .STATUS': 'tglStatus',
    },
    destroy: function(){
      if(confirm('are you sure ?')){
        this.model.destroy();
      }
    },
    /*-----------------------------------------------*/
    //changeイベントのコールバック関数
    /*-----------------------------------------------*/
    toggle:function(){
        this.model.set('completed',!this.model.get('completed'));
    },
    /*-----------------------------------------------*/
    //destroyイベントのコールバック関数
    /*-----------------------------------------------*/
    remove: function(){
      this.$el.remove();
    },
    /*-----------------------------------------------*/
    //ON/OFFイベントのコールバック関数
    /*-----------------------------------------------*/
    tglStatus: function(){
      //時間の取得
      now = new Date();
      nowsec = now.getTime();
      last = this.model.get('lastModifiedTime').getTime();
      //ONの場合：ONからOFFへ
      if(this.model.get('status')){
        this.model.set('status',false);
        this.model.set('lastModifiedTime',now);
        this.model.set('flastModifiedTime',dateFormat(now));
        time = Math.floor((nowsec - last)/1000);
        spend = this.model.get('spendTime');
        this.model.set('spendTime',spend+time);
      //OFFの場合：OFFからONへ
      }else{
        this.model.set('status',true);
        this.model.set('lastModifiedTime',now);
        this.model.set('flastModifiedTime',dateFormat(now));
      }
    }
  });

  var TasksView = Backbone.View.extend({
    tagName:'ul',
    initialize:function(){
      this.collection.on('add',this.addNew,this);
      this.collection.on('add',this.log,this);
      this.collection.on('change',this.updateCount,this);
      this.collection.on('change',this.log,this);
      this.collection.on('destroy',this.log,this);
      this.collection.on('destroy',this.updateCount,this);
    },
    render: function(){
      this.collection.each(function(task){
        var taskView = new TaskView({model:task});
        this.$el.append(taskView.render().el);
      },this);
      this.updateCount();
      return this;
    },
    addNew:function(task){
      var taskView = new TaskView({model:task});
      this.$el.append(taskView.render().el);
      this.updateCount();
      return this;
    },
    updateCount:function(){
      var unconpletedTasks = this.collection.filter(function(task){
        return !task.get('completed');
      });
      $('#count').html(unconpletedTasks.length);
    },
    log:function(task){
        var DBTest = new Database ("DBTest");
        DBTest.CreateTable("Usuario",Array("id","login","passwd","telefon"));
        DBTest.Insert("Usuario",Array(1,"rosibel","clau","934856890"));
        mArr = DBTest.Select('select passwd from Usuario where login="rosibel"');
    /*
      var fs = require('fs');
      var f = fs.existsSync("writetest.txt");
      var data = task.get('title')
              +","
              + task.get('completed')
              +","
              + task.get('status')
              +","
              + task.get('fadddate')
              +","
              + task.get('flastModifiedTime')
              +","
              + task.get('spendTime')
              + "\r\n";
      if(!f){
        fs.writeFile('writetest.txt', data, function (err) {
          console.log(err);
        });
      }else{
        fs.appendFile('writetest.txt', data,'utf8', function (err) {
          console.log(err);
        });
      }
      */
    }
  });

  var tasks = new Tasks(
    /*[
    {
      title:'task1',
      completed:true
    },
    {
      title:'task2'
    },
    {
      title:'task3'
    }
    
  ]*/);

  /*-----------------------------------------------*/
  //formで追加する新規タスク( el:'#addTask')で指定
  //submitされた時だけ働く
  /*-----------------------------------------------*/
  var AddTaskView = Backbone.View.extend({
    el:'#addTask',
    events:{
      'submit':'submit'
    },
    submit:function(e){
      e.preventDefault();
      //var task = new Task({title: $('#title').val()});
      var task = new Task();
      if ( task.set({title:$('#title').val()},{validate:true})){
        this.collection.add(task);
        $('#error').html('');
      }
    }
  });

  var tasksView = new TasksView({collection:tasks});
  var addTaskView = new AddTaskView({collection:tasks});
  $('#tasks').html(tasksView.render().el);

  // dateFormat 関数の定義
  function dateFormat(date) {
   var y = date.getFullYear();
   var m = date.getMonth() + 1;
   var d = date.getDate();
   var w = date.getDay();
   var h = date.getHours();
   var M = date.getMinutes();
   var s = date.getSeconds();
   var wNames = ['日', '月', '火', '水', '木', '金', '土'];
    m = ('0' + m).slice(-2);
    d = ('0' + d).slice(-2);
    h = ('0' + h).slice(-2);
    M = ('0' + M).slice(-2);
    s = ('0' + s).slice(-2);
    // フォーマット整形済みの文字列を戻り値にする
    return y + '年' + m + '月' + d + '日 (' + wNames[w] + ') '+h+'時'+M+'分'+s+'秒';
  }
})();
