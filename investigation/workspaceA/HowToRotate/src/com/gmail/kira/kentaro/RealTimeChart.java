package com.gmail.kira.kentaro;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Paint.Align;
import android.view.View;

public class RealTimeChart extends View{

	//データ
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer; 
	private int LineType = 1;
	private LineStyle lineStyle;
	private int[] color; 
	private PointStyle[] style; 
	private int dataSize=1;
	
	//デフォルトコンストラクタ
	public RealTimeChart(Context context) {
		super(context);
		lineStyle = new LineStyle();
		color=lineStyle.getLineColorType(LineType);
		style=lineStyle.getLinePointStyle(LineType);
	}
	//ラインタイプを決定したコンストラクタ
	public RealTimeChart(Context context,int line) {
		super(context);
		this.LineType=line;
		lineStyle = new LineStyle();
		color=lineStyle.getLineColorType(LineType);
		style=lineStyle.getLinePointStyle(LineType);
	}
	//データ設定(タイトル、x値、y値)
	public void buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		dataSize = titles.length;
		dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		buildRenderer(color,style);
	}
	//コンストラクタ内で定義しているが、外部から呼び出して設定も可能
	public void buildRenderer(int[] colors,
			PointStyle[] styles) {
		renderer = new XYMultipleSeriesRenderer();
		setRenderer(colors, styles);
	}
	private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		for (int i = 0; i < dataSize; i++) {
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

	private void setRenderer(int[] colors, PointStyle[] styles) {

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
		for (int i = 0; i < dataSize; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	public void setChartSettings(String title, String xTitle, String yTitle, double xMin,
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
	
	public GraphicalView getGraphicalView(Context context){
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
	
	public XYMultipleSeriesDataset getDataset(){
		return dataset;
	}
}
