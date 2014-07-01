/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.mymap;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyMap extends MapActivity {
   private FrameLayout frame;
   private MapView map;
   private MapController controller;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      initMapView();
      initZoomControls();
      initMyLocation();
   }
   

   
   /** Mapviewを探して初期化する */
   private void initMapView() {
      frame = (FrameLayout) findViewById(R.id.frame);
      map = (MapView) findViewById(R.id.map);
      controller = map.getController();
      map.setSatellite(true);
   }
   

   
   /** ズームコントロールを取得し、マップの下部に追加する */
   private void initZoomControls() {
      View zoomControls = map.getZoomControls();
      FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL);
      frame.addView(zoomControls, p);
   }
   

   
   /** 地図上での位置の追跡を開始する */
   private void initMyLocation() {
      final MyLocationOverlay overlay = new MyLocationOverlay(this, map);
      overlay.enableMyLocation();
      overlay.enableCompass(); // エミュレータでは効果なし
      overlay.runOnFirstFix(new Runnable() {
         public void run() {
            // 現在の位置にズームイン
            controller.setZoom(8);
            controller.animateTo(overlay.getMyLocation());
         }
      });
      map.getOverlays().add(overlay);
   }
   

   
   @Override
   protected boolean isRouteDisplayed() {
      // MapActivityが必要とする
      return false;
   }
}
