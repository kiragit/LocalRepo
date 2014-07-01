/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.opengl;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GLView extends SurfaceView implements SurfaceHolder.Callback {
   
   
   private GLThread glThread;

   
   
   GLView(Context context) {
      super(context);

      // surfaceが作成、破棄されたときに通知を受けられるように、
      // SurfaceHolder.Callbackを登録する
      getHolder().addCallback(this);
      
      // 使える場合はハードウェアアクセラレーションを使う
      getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
   }

   
   public void surfaceCreated(SurfaceHolder holder) {
      
      // Surfaceが作られたので、描画スレッドを起動する
      glThread = new GLThread(this);
      glThread.start();
      
   }

   public void surfaceDestroyed(SurfaceHolder holder) {
      
      // 描画スレッドを停止する。
      // Surfaceはここからリターンしたときに破棄される
      glThread.requestExitAndWait();
      glThread = null;
      
   }
   

   public void surfaceChanged(SurfaceHolder holder, int format,
         int w, int h) {
      // TODO: ウィンドウサイズの変更の処理
   }
}
