package com.gmail.kira.kentaro;

import org.achartengine.chart.PointStyle;
import android.graphics.Color;

public class LineStyle {

	// Lineのスタイル
	private int[] colors1; 
	private int[] colors2; 
	private int[] colors3; 
	private int[] colors4; 
	private int[] colors5; 
	private int[][] colors;
	private PointStyle[] styles1; 
	private PointStyle[] styles2; 
	private PointStyle[] styles3; 
	private PointStyle[] styles4;
	private PointStyle[] styles5;
	private PointStyle[][] styles;

	
	public LineStyle(){
		colors1 = new int[] { Color.BLUE,Color.RED,Color.GREEN,Color.MAGENTA,Color.BLACK};
		colors2 = new int[] { Color.RED,Color.GREEN,Color.MAGENTA,Color.BLACK,Color.BLUE};
		colors3 = new int[] { Color.GREEN,Color.MAGENTA,Color.BLACK,Color.BLUE,Color.RED};
		colors4 = new int[] { Color.MAGENTA,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN};
		colors5 = new int[] { Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.MAGENTA};
		colors = new int[][]{colors1,colors2,colors3,colors4,colors5};
		styles1 = new PointStyle[] { PointStyle.CIRCLE,PointStyle.DIAMOND,PointStyle.SQUARE,PointStyle.TRIANGLE,PointStyle.POINT};
		styles2 = new PointStyle[] { PointStyle.DIAMOND,PointStyle.SQUARE,PointStyle.TRIANGLE,PointStyle.POINT,PointStyle.CIRCLE};
		styles3 = new PointStyle[] { PointStyle.SQUARE,PointStyle.TRIANGLE,PointStyle.POINT,PointStyle.CIRCLE,PointStyle.DIAMOND};
		styles4 = new PointStyle[] { PointStyle.TRIANGLE,PointStyle.POINT,PointStyle.CIRCLE,PointStyle.DIAMOND,PointStyle.SQUARE};
		styles5 = new PointStyle[] { PointStyle.POINT,PointStyle.CIRCLE,PointStyle.DIAMOND,PointStyle.SQUARE,PointStyle.TRIANGLE};
		styles = new PointStyle[][]{styles1,styles2,styles3,styles4,styles5};
	
		
	}

	public int[] getLineColorType(int TypeNo){
		return colors[TypeNo];
	}
	
	public PointStyle[] getLinePointStyle(int TypeNo){
		return styles[TypeNo];
	}
}
