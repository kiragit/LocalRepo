package com.gmail.kira.kentaro;

import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.widget.LinearLayout;

public class CopyOfMainActivity extends Activity implements SensorEventListener {

	private XYMultipleSeriesDataset datasetX;
	private XYMultipleSeriesDataset datasetY;
	private XYMultipleSeriesDataset datasetZ;
	private GraphicalView graphicalViewX;
	private GraphicalView graphicalViewY;
	private GraphicalView graphicalViewZ;
	private LinearLayout graph1;
	private LinearLayout graph2;
	private LinearLayout graph3;
	private SensorManager manager;
	// センサーが取得できたか
	private boolean mIsMagSensor;
	private boolean mIsAccSensor;
	// 傾き情報
	private double addT = 0;
	private final long spanT = 50;
	private final long maxT = 15000;
	private ArrayList<Float> Xarr;
	private ArrayList<Float> Yarr;
	private ArrayList<Float> Zarr;
	private double X = 0;
	private double Y = 0;
	private double Z = 0;
	private Handler handler = new Handler();
	// グラフ領域の設定
	private String GraphTitle = "Average temperature";
	private String HAxisLabel = "Horizontal axis";
	private String VAxisLabel = "Vertical axis";
	private int XXmin = 0;
	private int XXmax = (int) (maxT / spanT);
	private int XYmin = -360;// PI/2
	private int XYmax = 360;// PI/2
	private int GraphBackGroundColor = Color.LTGRAY;

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
			datasetX.getSeriesAt(0).add(addT, X);
			datasetY.getSeriesAt(0).add(addT, Y);
			datasetZ.getSeriesAt(0).add(addT, Z);
			addT++;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Xarr = new ArrayList<Float>();
		Yarr = new ArrayList<Float>();
		Zarr = new ArrayList<Float>();

		// Line本数の設定
		String[] titles = new String[] { "Blue"};
		List<double[]> x = new ArrayList<double[]>();
		for (int i = 0; i < titles.length; i++) {
			x.add(new double[] { 0 });
		}
		// Line値の初期値設定
		List<double[]> valuesX = new ArrayList<double[]>();
		List<double[]> valuesY = new ArrayList<double[]>();
		List<double[]> valuesZ = new ArrayList<double[]>();
		valuesX.add(new double[] { 0 });
		valuesY.add(new double[] { 0 });
		valuesZ.add(new double[] { 0 });

		// Lineのスタイル決定
		int[] colorsX = new int[] { Color.BLUE};
		int[] colorsY = new int[] { Color.GREEN};
		int[] colorsZ = new int[] { Color.RED};
		PointStyle[] stylesX = new PointStyle[] { PointStyle.CIRCLE};
		PointStyle[] stylesY = new PointStyle[] { PointStyle.SQUARE};
		PointStyle[] stylesZ = new PointStyle[] { PointStyle.TRIANGLE};
		XYMultipleSeriesRenderer rendererX = buildRenderer(colorsX, stylesX);
		XYMultipleSeriesRenderer rendererY = buildRenderer(colorsY, stylesY);
		XYMultipleSeriesRenderer rendererZ = buildRenderer(colorsZ, stylesZ);
		int length = rendererX.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) rendererX.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		// 最大値設定
		setChartSettings(rendererX, GraphTitle, HAxisLabel, VAxisLabel, XXmin,
				XXmax, XYmin, XYmax, GraphBackGroundColor, GraphBackGroundColor);
		setChartSettings(rendererY, GraphTitle, HAxisLabel, VAxisLabel, XXmin,
				XXmax, XYmin, XYmax, GraphBackGroundColor, GraphBackGroundColor);
		setChartSettings(rendererZ, GraphTitle, HAxisLabel, VAxisLabel, XXmin,
				XXmax, XYmin, XYmax, GraphBackGroundColor, GraphBackGroundColor);

		datasetX = buildDataset(titles, x, valuesX);
		graphicalViewX = ChartFactory.getLineChartView(getApplicationContext(),
				datasetX, rendererX);
		graph1 = (LinearLayout) findViewById(R.id.graph1);
		graph1.addView(graphicalViewX);
		
		datasetY = buildDataset(titles, x, valuesY);
		graphicalViewY = ChartFactory.getLineChartView(getApplicationContext(),
				datasetY, rendererY);
		graph2 = (LinearLayout) findViewById(R.id.graph2);
		graph2.addView(graphicalViewY);
		
		datasetZ = buildDataset(titles, x, valuesZ);
		graphicalViewZ = ChartFactory.getLineChartView(getApplicationContext(),
				datasetZ, rendererZ);
		graph3 = (LinearLayout) findViewById(R.id.graph3);
		graph3.addView(graphicalViewZ);
		
		handler.postDelayed(updateRunnable, spanT);
	}

	private XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
	}

	private XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {

		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO 自動生成されたメソッド・スタブ

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
