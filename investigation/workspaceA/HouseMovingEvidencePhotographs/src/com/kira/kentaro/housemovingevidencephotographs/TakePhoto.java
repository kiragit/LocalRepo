package com.kira.kentaro.housemovingevidencephotographs;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

public class TakePhoto extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//カメラ本体
	private Camera mCam = null;
	//カメラビュー
	private CameraPreview mCamPreview = null;
    // 画面タッチの2度押し禁止用フラグ
    private boolean mIsTake = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		try {
			mCam = Camera.open();
		} catch (Exception e) {
			
			this.finish();
		}

		// FrameLayout CameraPreview
		FrameLayout preview = (FrameLayout) findViewById(R.id.photo);
		mCamPreview = new CameraPreview(this, mCam);
		preview.addView(mCamPreview);
		
		// mCamPreview に タッチイベントを設定
        mCamPreview.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!mIsTake) {
                        // 撮影中の2度押し禁止用フラグ
                        mIsTake = true;
                        // オートフォーカス
                        mCam.autoFocus(mAutoFocusListener);

                    }
                }
                return true;
            }
        });
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mCam != null) {
			mCam.release();
			mCam = null;
		}
	}
	
	/**
	 * オートフォーカス完了のコールバック
	 */
	private Camera.AutoFocusCallback mAutoFocusListener = new Camera.AutoFocusCallback() {
	    public void onAutoFocus(boolean success, Camera camera) {
	        // 撮影
	    	if(success){
	    		mCam.takePicture(null, null, mPicJpgListener);
	    	}
	    }
	};
	/**
     * JPEG データ生成完了時のコールバック
     */
    private Camera.PictureCallback mPicJpgListener = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            if (data == null) {
                return;
            }

            String saveDir = Environment.getExternalStorageDirectory().getPath() + "/test";

            // SD カードフォルダを取得
            File file = new File(saveDir);

            // フォルダ作成
            if (!file.exists()) {
                if (!file.mkdir()) {
                    Log.e("Debug", "Make Dir Error");
                }
            }

            // 画像保存パス
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String imgPath = saveDir + "/" + sf.format(cal.getTime()) + ".jpg";

            // ファイル保存
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(imgPath, true);
                fos.write(data);
                fos.close();

                // アンドロイドのデータベースへ登録
                // (登録しないとギャラリーなどにすぐに反映されないため)
                registAndroidDB(imgPath);
    
            } catch (Exception e) {
                Log.e("Debug", e.getMessage());
            }

            fos = null;

            // takePicture するとプレビューが停止するので、再度プレビュースタート
            mCam.startPreview();

            mIsTake = false;
        }
    };

    /**
     * アンドロイドのデータベースへ画像のパスを登録
     * @param path 登録するパス
     */
    private void registAndroidDB(String path) {
        // アンドロイドのデータベースへ登録
        // (登録しないとギャラリーなどにすぐに反映されないため)
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = TakePhoto.this.getContentResolver();
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        values.put("_data", path);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

}
