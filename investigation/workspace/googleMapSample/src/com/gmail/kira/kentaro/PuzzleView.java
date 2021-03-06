package com.gmail.kira.kentaro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class PuzzleView extends View {
	
	private static final String TAG = "Sudoku PuzzleView";
	private final Game game;
	
	private float width;
	private float height;
	private int selX;
	private int selY;
	private final Rect selRect = new Rect();
	

	public PuzzleView(Context context) {
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		
	}
	
	@Override
	protected void onSizeChanged(int w,int h,int oldw,int oldh){
		width = w/9f;
		width = h/9f;
		getRect(selX,selY,selRect);
		Log.d(TAG, "onSizeChanged: width " + width+",height "+height);
		super.onSizeChanged(w, h, oldw, oldh);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		//背景を描画する
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0,getWidth(),getHeight(),background);
		//盤面を描画する
		//枠線の色を定義する
		
		//数値を描画する
		//ヒントを描画する
		//選択されたマスを描画する
		}

}
