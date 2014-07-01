package com.gmail.kira.kentaro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Game extends Activity {

	private static final String TAG = "Sudoku Game";
	
	public static final String KEY_DIFFICULTY = "difficulty";
	public static final int DIFFCULTY_EASY = 0;
	public static final int DIFFCULTY_MEDIUM = 1;
	public static final int DIFFCULTY_HARD = 2;
	
	private int puzzle[] = new int[9 * 9];
	
	private PuzzleView puzzleView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG","onCreate");
        
        //ÉQÅ[ÉÄìÔà’ìxÇÃê›íË
        int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFCULTY_EASY);
        puzzle = getPuzzle(diff);
        calculateUsedTiles();
        
        //ÉQÅ[ÉÄâÊñ ÇÃï`âÊ
        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
        
        setContentView(R.layout.main);
	}
}
