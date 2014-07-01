(function (){

  /*Model*/
  var Task = Backbone.Model.extend({
    //初期値設定
    defaults: {
      title:"do something!",
      completed:false
    },  
    //バリデーション
    validate: function(attrs){
      if(_.isEmpty(attrs.title)){
        return "title must not be empty!";
      }
    },
    //自作関数
    toggle:function() {
      this.set('completed', !this.get('completed'));
    }
  });

  //モデルの生成
  var task = new Task();

  //test: getter setter
  task.set('title','newTitle');
  var title = task.get('title');
  console.log(task.toJSON());

  //test: toggle
  task.toggle(); 
  console.log(task.toJSON());
  task.toggle(); 
  console.log(task.toJSON());

  //test: validate
  task.set({title:''},{validate : true});
  console.log(task.toJSON());
  
  /*View*/
  var TaskView = Backbone.View.extend({
    tagName:'li',
    //イベント定義
    events:{
      //イベントを発生させるセレクタを選べる
      //"click .command":"sayHello"

      "click":"sayHello"
    },
    //自作イベント関数
    sayHello:function(){
      alert('hello');
    },
    //index.htmlに定義したtemplateを取得
    template: _.template( $('#task-template').html()),
    render:function(){
      //point
      //TaskViewは生成時に、modelを受け取る
      //今回受けとったmodelはtask
      //taskのdefault値は = {title:newtitle},{completed:false}
      //なので
      //this.model.toJSON = {title:newtitle},{completed:false}
      //index.htmlで宣言したtemplateはtitleを持つ
      //json形式で流し込まれたtitleがtemplateに設定される
      var template = this.template( this.model.toJSON());
      //templateで指定した形式のhtmlへ整形する
      this.$el.html(template);
      return this;
    }
  });

  var taskView = new TaskView({ model:task});
  console.log(taskView.render().el);
  $('body').append(taskView.render().el);

  //collection

  var Tasks = Backbone.Collection.extend({
    model:Task
  });
  var tasks = new Tasks([
    {
      title: 'task1',
      completed:true
    },
    {
      title: 'task2',
    },
    {
      title: 'task3',
    }
  ]);

  //console.log(tasks.toJSON());

  //collectionを作成
  var TasksView = Backbone.View.extend({
    tagName:'ul',
    render:function(){
      this.collection.each(function(task){
        var taskView = new TaskView({model:task});
        this.$el.append(taskView.render().el);
      },this);
      return this;
    }
  });

  //collectionをindex.htmlへ追加
  var tasksView = new TasksView({collection:tasks});
  $('#tasks').html(tasksView.render().el);
})();
