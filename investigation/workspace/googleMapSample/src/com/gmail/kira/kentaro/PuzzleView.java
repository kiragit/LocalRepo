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
		//”wŒi‚ğ•`‰æ‚·‚é
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0,getWidth(),getHeight(),background);
		//”Õ–Ê‚ğ•`‰æ‚·‚é
		//˜gü‚ÌF‚ğ’è‹`‚·‚é
		
		//”’l‚ğ•`‰æ‚·‚é
		//ƒqƒ“ƒg‚ğ•`‰æ‚·‚é
		//‘I‘ğ‚³‚ê‚½ƒ}ƒX‚ğ•`‰æ‚·‚é
		}

}
