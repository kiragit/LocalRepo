/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class GLCube {
   private final IntBuffer mVertexBuffer;
   
   
   private final IntBuffer mTextureBuffer;

   
   public GLCube() {
      
      int one = 65536;
      int half = one / 2;
      int vertices[] = { 
            // 全面
            -half, -half, half, half, -half, half,
            -half, half, half, half, half, half,
            // 背面
            -half, -half, -half, -half, half, -half,
            half, -half, -half, half, half, -half,
            // 左面
            -half, -half, half, -half, half, half,
            -half, -half, -half, -half, half, -half,
            // 右面
            half, -half, -half, half, half, -half,
            half, -half, half, half, half, half,
            // 上面
            -half, half, half, half, half, half,
            -half, half, -half, half, half, -half,
            // 底面
            -half, -half, half, -half, -half, -half,
            half, -half, half, half, -half, -half, };
      

      
      int texCoords[] = {
            // 全面
            0, one, one, one, 0, 0, one, 0,
            // 背面
            one, one, one, 0, 0, one, 0, 0,
            // 左面
            one, one, one, 0, 0, one, 0, 0,
            // 右面
            one, one, one, 0, 0, one, 0, 0,
            // 上面
            one, 0, 0, 0, one, one, 0, one,
            // 底面
            0, 0, 0, one, one, 0, one, one, };
      

      
      // gl*Pointer()関数に渡すバッファは、ダイレクトでなければならない。
      // つまりガーベジコレクタが機能しないネーティブヒープに
      // 配置しなければならない
      //
      // マルチバイトデータ型（short, int, floatなど）データを格納する
      // バッファは、バイトオーダーをネーティブのものにしなければならない
      ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
      vbb.order(ByteOrder.nativeOrder());
      mVertexBuffer = vbb.asIntBuffer();
      mVertexBuffer.put(vertices);
      mVertexBuffer.position(0);
      

      
      // ...
      ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
      tbb.order(ByteOrder.nativeOrder());
      mTextureBuffer = tbb.asIntBuffer();
      mTextureBuffer.put(texCoords);
      mTextureBuffer.position(0);
      
   }
   

   public void draw(GL10 gl) { 
      gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
      
      
      gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);
      
      

      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(0, 0, 1);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
      gl.glNormal3f(0, 0, -1);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);

      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(-1, 0, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
      gl.glNormal3f(1, 0, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);

      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(0, 1, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
      gl.glNormal3f(0, -1, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
   }
   
   

   static void loadTexture(GL10 gl, Context context, int resource) {
      Bitmap bmp = BitmapFactory.decodeResource(
            context.getResources(), resource);

      ByteBuffer bb = extract(bmp);

      load(gl, bb, bmp.getWidth(), bmp.getHeight());
   }

   private static ByteBuffer extract(Bitmap bmp) { 
      ByteBuffer bb = ByteBuffer.allocateDirect(bmp.getHeight()
            * bmp.getWidth() * 4);
      bb.order(ByteOrder.BIG_ENDIAN);
      IntBuffer ib = bb.asIntBuffer();

      // ARGB -> RGBA変換
      for (int y = bmp.getHeight() - 1; y > -1; y--)
         for (int x = 0; x < bmp.getWidth(); x++) {
            int pix = bmp.getPixel(x, bmp.getHeight() - y - 1);
            // int alpha = ((pix >> 24) & 0xFF);
            int red = ((pix >> 16) & 0xFF);
            int green = ((pix >> 8) & 0xFF);
            int blue = ((pix) & 0xFF);

            // 面白い効果を出すためにアルファをブレンドする
            ib.put(red << 24 | green << 16 | blue << 8
                  | ((red + blue + green) / 3));
         }
      bb.position(0);
      return bb;
   }

   private static void load(GL10 gl, ByteBuffer bb, 
         int width, int height) {
      // 新しいテクスチャの名前を取得
      int[] tmp_tex = new int[1];
      gl.glGenTextures(1, tmp_tex, 0);
      int tex = tmp_tex[0];

      // テクスチャをロード
      gl.glBindTexture(GL10.GL_TEXTURE_2D, tex);
      gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA,
            width, height, 0, GL10.GL_RGBA,
            GL10.GL_UNSIGNED_BYTE, bb);
      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
   }
   
}

