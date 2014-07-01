/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.sensortest;

import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorTest extends Activity implements SensorListener {
   

   
   
   private SensorManager mgr;
   
   private TextView output;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      
      // ...
      mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
      
      output = (TextView) findViewById(R.id.output);
   }
   

   
   @Override
   protected void onResume() {
      super.onResume();
      
      // 1つ以上のセンサーのアップデートを開始する
      int sensors = SensorManager.SENSOR_ALL;
      mgr.registerListener(this, sensors);
      
   }

   @Override
   protected void onPause() {
      super.onPause();
      
      // アプリケーションの停止中はアップデートを停止して電力を節約する
      mgr.unregisterListener(this);
      
   }
   

   public void onAccuracyChanged(int sensor, int accuracy) {
      // TODO Auto-generated method stub
   }

   
   public void onSensorChanged(int sensor, float[] values) {
      
      StringBuilder builder = new StringBuilder();
      builder.append("Sensor number: ");
      builder.append(sensor);
      builder.append("\nValues:\n");
      
      for (int i = 0; i < values.length; i++) {
         // ...
         
         builder.append("[");
         builder.append(values[i]);
         builder.append("] = ");
         builder.append("\n");
         
      }
      
      output.setText(builder);
      
   }
   

   
}
