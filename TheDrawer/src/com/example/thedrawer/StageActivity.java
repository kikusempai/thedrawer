package com.example.thedrawer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.thedrawer.TheDrawing;

public class StageActivity extends Activity {

	PaintView paintView;
	ToggleButton dashB, curveB;
	Button undoB, hintB, submitB;
	boolean result; //figure comparison result
	Animation leHint, leResult;

	TextView figureName;
	TheDrawing targetFigure;
	ImageView referencePic, hintChibi, resultChibi;
	ImageView sceneHint;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene);
		paintView = (PaintView) findViewById(R.id.paintview);
		TextView figureName = (TextView)findViewById(R.id.nameText);
		
		final ImageView resultChibi = (ImageView)findViewById(R.id.resultChibi);
		
		final Bundle bundle = getIntent().getExtras();
		figureName.setText(bundle.getString("figurename"));
		
		referencePic = (ImageView)findViewById(R.id.referenceImage);
		final ImageView sceneHint = (ImageView)findViewById(R.id.scenehint);
		
		switch (bundle.getInt("target")){ //here we determine which level was requested
			case (1):
				referencePic.setImageResource(R.drawable.shoebox_ref);
				sceneHint.setImageResource(R.drawable.shoebox_hint);
				TheDrawing targetFigure = new TheDrawing(1, paintView);
				paintView.setTargetFigurePV(targetFigure);
			break;
			case (2):
				referencePic.setImageResource(R.drawable.icecream_ref);
				sceneHint.setImageResource(R.drawable.icecream_hint);
				targetFigure = new TheDrawing(2, paintView);
				paintView.setTargetFigurePV(targetFigure);
			break;
			case (3):
				referencePic.setImageResource(R.drawable.catcorner_ref);
				sceneHint.setImageResource(R.drawable.catcorner_hint);
				targetFigure = new TheDrawing(3, paintView);
				paintView.setTargetFigurePV(targetFigure);
			break;
			case (4):
				referencePic.setImageResource(R.drawable.sofa_ref);
				sceneHint.setImageResource(R.drawable.sofa_hint);
				targetFigure = new TheDrawing(4, paintView);
				paintView.setTargetFigurePV(targetFigure);
			break;
			case (5):
				referencePic.setImageResource(R.drawable.sofa_slash_ref);
				sceneHint.setImageResource(R.drawable.sofa_slash_hint);
				targetFigure = new TheDrawing(5, paintView);
				paintView.setTargetFigurePV(targetFigure);
			break;
			case (6):
				referencePic.setImageResource(R.drawable.iceceramcone_ref);
				sceneHint.setImageResource(R.drawable.icecreamcone_hint);
				targetFigure = new TheDrawing(6, paintView);
				paintView.setTargetFigurePV(targetFigure);
			break;
				
		}//end switch 'target'
		
		final ToggleButton dashB = (ToggleButton) findViewById(R.id.toggleDash);
		dashB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// Toast.makeText(MainActivity.this, isChecked+"",
				// Toast.LENGTH_SHORT).show();
				paintView.setDash(isChecked);
			}
		});

		ToggleButton curveB = (ToggleButton) findViewById(R.id.toggleCurve);
		curveB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				paintView.setCurve(isChecked);
			}
		});

		final Button hintB = (Button) findViewById(R.id.hintButton);
		final ImageView hintChibi = (ImageView)findViewById(R.id.hintChibi);

		hintB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(
						StageActivity.this, R.anim.fadein);
				anim.setDuration(5000);
				anim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						sceneHint.setVisibility(View.VISIBLE);

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						sceneHint.setVisibility(View.GONE);

					}
				});
				sceneHint.startAnimation(anim);
				
				Animation leHint = AnimationUtils.loadAnimation(
						StageActivity.this, R.anim.fadeout);
				leHint.setDuration(5000);
				leHint.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						hintChibi.setVisibility(View.VISIBLE);

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						hintChibi.setVisibility(View.GONE);

					}
				});
				hintChibi.startAnimation(leHint);
			}
		});

		Button undoB = (Button) findViewById(R.id.undoButton);

		undoB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				paintView.getCurrentFigure().deleteLast(paintView.isCurve());
				paintView.invalidate();
			}
		});
		
		Button submitB = (Button) findViewById(R.id.submitButton);
		final Animation leResult = AnimationUtils.loadAnimation(
				StageActivity.this, R.anim.fadeout);
		leResult.setDuration(5000);
		leResult.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				resultChibi.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				resultChibi.setVisibility(View.GONE);

			}
		});

		submitB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 result = paintView.getCurrentFigure().compare(paintView.getTargetFigurePV());
				 if (result){
					 resultChibi.setImageResource(R.drawable.win_chibi);
					 resultChibi.startAnimation(leResult);
					 Toast.makeText(StageActivity.this, "Correct", Toast.LENGTH_SHORT).show();
				 }
				 else  {
					 resultChibi.setVisibility(View.VISIBLE);
					 resultChibi.startAnimation(leResult);
					 Toast.makeText(StageActivity.this, "Incorrect. Try again or use Hint", Toast.LENGTH_SHORT).show();
				 }
			}
		});
		
		Button clearB = (Button) findViewById(R.id.clearButton);

		clearB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				paintView.currentFigure.lines.clear();
				paintView.invalidate();
			}
		});
	}// end onCreate


}
