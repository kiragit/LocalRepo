package com.gmail.kira.kentaro;

import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.GraphicalView;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements SensorEventListener {

	//チャートの作成
	private RealTimeChart chart1;
	private RealTimeChart chart2;
	private RealTimeChart chart3;
	//グラフビュー
	private GraphicalView graphicalViewX;
	private GraphicalView graphicalViewY;
	private GraphicalView graphicalViewZ;
	//レイアウト
	private LinearLayout graph1;
	private LinearLayout graph2;
	private LinearLayout graph3;
	private SensorManager manager;
	// センサーが取得できたか
	private boolean mIsMagSensor;
	private boolean mIsAccSensor;
	// 傾き情報
	private double addT = 0;
	private final long spanT = 100;
	private final long maxT = 15000;
	private ArrayList<Float> Xarr;
	private ArrayList<Float> Yarr;
	private ArrayList<Float> Zarr;
	//LineType
	private int LineType1=1;
	private int LineType2=2;
	private int LineType3=3;
	//データ
	private double X = 0;
	private double Y = 0;
	private double Z = 0;
	private Handler handler = new Handler();
	// グラフ領域の設定
	private int XXmin = 0;
	private int XXmax = (int) (maxT / spanT);

	private static final int MATRIX_SIZE = 16;
	/* 回転行列 */
	float[] inR = new float[MATRIX_SIZE];
	float[] outR = new float[MATRIX_SIZE];
	float[] I = new float[MATRIX_SIZE];

	/* センサーの値 */
	float[] orientationValues = new float[3];
	float[] magneticValues = new float[3];
	float[] accelerometerValues = new float[3];

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		// センサーマネージャのリスナ登録破棄
		if (mIsMagSensor || mIsAccSensor) {
			manager.unregisterListener(this);
			mIsMagSensor = false;
			mIsAccSensor = false;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// センサの取得
		List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
		// センサマネージャへリスナーを登録(implements SensorEventListenerにより、thisで登録する)
		for (Sensor sensor : sensors) {
			if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				manager.registerListener(this, sensor,
						SensorManager.SENSOR_DELAY_UI);
				mIsMagSensor = true;
			}
			if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				manager.registerListener(this, sensor,
						SensorManager.SENSOR_DELAY_GAME);
				mIsAccSensor = true;
			}
		}
	}

	private Runnable updateRunnable = new Runnable() {
		@Override
		public void run() {
			X = averageRadius(Xarr);
			Y = averageRadius(Yarr);
			Z = averageRadius(Zarr);
			Xarr.clear();
			Yarr.clear();
			Zarr.clear();
			//データの更新
			chart1.getDataset().getSeriesAt(0).add(addT, X);
			chart2.getDataset().getSeriesAt(0).add(addT, Y);
			chart3.getDataset().getSeriesAt(0).add(addT, Z);
			//グラフのプロットを進める
			addT++;
			//再描画
			graphicalViewX.repaint();
			graphicalViewY.repaint();
			graphicalViewZ.repaint();
			if (addT < XXmax) {
				handler.postDelayed(updateRunnable, spanT);
			} else {
				onPause();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		/*-----初期設定-----*/
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Xarr = new ArrayList<Float>();
		Yarr = new ArrayList<Float>();
		Zarr = new ArrayList<Float>();

		// タイトル
		String[] titles1 = new String[] { "方位"};
		String[] titles2 = new String[] { "Pich"};
		String[] titles3 = new String[] { "Roll"};
		// X初期値設定
		List<double[]> x1 = new ArrayList<double[]>();
		List<double[]> x2 = new ArrayList<double[]>();
		List<double[]> x3 = new ArrayList<double[]>();
		for (int i = 0; i < titles1.length; i++) {
			x1.add(new double[] { 0 });
		}
		for (int i = 0; i < titles2.length; i++) {
			x2.add(new double[] { 0 });
		}
		for (int i = 0; i < titles3.length; i++) {
			x3.add(new double[] { 0 });
		}
		// Y初期値設定
		List<double[]> valuesX = new ArrayList<double[]>();
		List<double[]> valuesY = new ArrayList<double[]>();
		List<double[]> valuesZ = new ArrayList<double[]>();
		valuesX.add(new double[] { 0 });
		valuesY.add(new double[] { 0 });
		valuesZ.add(new double[] { 0 });
		//RealTImeChatクラスの生成
		chart1 = new RealTimeChart(this,LineType1);
		chart2 = new RealTimeChart(this,LineType2);
		chart3 = new RealTimeChart(this,LineType3);
		//データセットの用意
		chart1.buildDataset(titles1, x1, valuesX);
		chart2.buildDataset(titles2, x2, valuesY);
		chart3.buildDataset(titles3, x3, valuesZ);
		//グラフの書式設定
		chart1.setChartSettings("方位", "時間", "角度", XXmin, XXmax, 0, 360, Color.BLACK, Color.BLACK);
		chart2.setChartSettings("Pich", "時間", "角度", XXmin, XXmax, -90, 90, Color.BLACK, Color.BLACK);
		chart3.setChartSettings("Roll", "時間", "角度", XXmin, XXmax, -180, 180, Color.BLACK, Color.BLACK);
		//GraphicalViewの用意
		graphicalViewX = chart1.getGraphicalView(getApplicationContext());
		graphicalViewY = chart2.getGraphicalView(getApplicationContext());
		graphicalViewZ = chart3.getGraphicalView(getApplicationContext());
		graph1 = (LinearLayout) findViewById(R.id.graph1);
		graph2 = (LinearLayout) findViewById(R.id.graph2);
		graph3 = (LinearLayout) findViewById(R.id.graph3);
		graph1.addView(graphicalViewX);
		graph2.addView(graphicalViewY);
		graph3.addView(graphicalViewZ);
		
		handler.postDelayed(updateRunnable, spanT);
	}



	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
			return;

		switch (event.sensor.getType()) {
		case Sensor.TYPE_MAGNETIC_FIELD:
			magneticValues = event.values.clone();
			break;
		case Sensor.TYPE_ACCELEROMETER:
			accelerometerValues = event.values.clone();
			break;
		}

		if (magneticValues != null && accelerometerValues != null) {

			SensorManager.getRotationMatrix(inR, I, accelerometerValues,
					magneticValues);

			// Activityの表示が縦固定の場合。横向きになる場合、修正が必要です
			SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X,
					SensorManager.AXIS_Z, outR);
			SensorManager.getOrientation(outR, orientationValues);

			//ラジアンから度への変換 及び方位の範囲を-180～180度から0～359度に変換
            float angle = radianToDegree(orientationValues[0]);
            if(angle >= 0)
                orientationValues[0] = angle;
            else if(angle < 0)
                orientationValues[0] = 360 + angle;
            orientationValues[1] = radianToDegree(orientationValues[1]);
            orientationValues[2] = radianToDegree(orientationValues[2]);
            
			Xarr.add(orientationValues[0]);
			Yarr.add(orientationValues[1]);
			Zarr.add(orientationValues[2]);

		}
	}
	
	/* ***** ラジアンから度への変換 ***** */
    int radianToDegree(float rad){
        return (int) Math.floor( Math.toDegrees(rad) ) ;
    }


	private double averageRadius(List<Float> arr) {
		int count = 0;
		float ave = 0;
		float thisave=0;
		
		for(float f:arr){
			thisave = thisave + f;
		}
		thisave = thisave/arr.size();
		
		for (float f : arr) {
			if (Math.abs(f-thisave) < 45) {
				ave = ave + f;
				count++;
			}
		}
		// Log.v("count",String.valueOf(count));
		if (count == 0)
			count = 1;
		return ave / count;
	}

}
