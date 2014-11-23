package com.example.thedrawer;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MainActivity extends Activity {

	ImageButton play, next, stage1, stage2, stage3, stage4, stage5, stage6;
	ImageView background;

	TheDrawing target;
	Drawable hintpic;
	Drawable referencePic;
	String figureName;
	
	int introCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageButton play = (ImageButton) findViewById(R.id.playButton);
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.intro);  // *****IMPORTANT!! UNCOMMENT ME! ******
//				startActivity(new Intent(getApplicationContext(),
//						StageActivity.class));
				doIntro();
			}
		});

	}

	public void doIntro() {

		final ImageButton next = (ImageButton) this
				.findViewById(R.id.nextButton);
		final ImageView background = (ImageView) this
				.findViewById(R.id.introBackground);
		if (next != null)
			Log.d("TAG---------", "next is NOT empty");
		if (background != null)
			Log.d("TAG", "background is NOT empty");
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (introCount) {
				case (0):
					background.setImageResource(R.drawable.intro2);
					introCount++;
					break;

				case (1):
					background.setImageResource(R.drawable.tutorial1);
					introCount++;
					break;

				case (2):
					background.setImageResource(R.drawable.tutorial2);
					introCount++;
					break;

				case (3):
					setContentView(R.layout.scene_picker);
					scenePick();
					break;

				default:
					break;
				}
			}
		});
	}// end intro

	public void scenePick() {
		ImageButton stage1 = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton stage2 = (ImageButton) findViewById(R.id.imageButton2);
		ImageButton stage3 = (ImageButton) findViewById(R.id.imageButton3);
		ImageButton stage4 = (ImageButton) findViewById(R.id.imageButton4);
		ImageButton stage5 = (ImageButton) findViewById(R.id.imageButton5);
		ImageButton stage6 = (ImageButton) findViewById(R.id.imageButton6);

		stage1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), StageActivity.class);
				Bundle bundle = new Bundle();
			    bundle.putInt("target", 1);
			    bundle.putString("figurename", "Shoe Box");
			    I.putExtras(bundle);
				startActivity(I);
			}
		});
		stage2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), StageActivity.class);
				Bundle bundle = new Bundle();
			    bundle.putInt("target", 2);
			    bundle.putString("figurename", "IceCream");
			    I.putExtras(bundle);
				startActivity(I);
			}
		});
		stage3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), StageActivity.class);
				Bundle bundle = new Bundle();
			    bundle.putInt("target", 3);
			    bundle.putString("figurename", "Cat corner");
			    I.putExtras(bundle);
				startActivity(I);
			}
		});
		stage4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), StageActivity.class);
				Bundle bundle = new Bundle();
			    bundle.putInt("target", 4);
			    bundle.putString("figurename", "Sofa");
			    I.putExtras(bundle);
				startActivity(I);
			}
		});
		stage5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), StageActivity.class);
				Bundle bundle = new Bundle();
			    bundle.putInt("target", 5);
			    bundle.putString("figurename", "Slashed sofa");
			    I.putExtras(bundle);
				startActivity(I);
			}
		});
		stage6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			Intent I = new Intent(getApplicationContext(), StageActivity.class);
			Bundle bundle = new Bundle();
		    bundle.putInt("target", 6);
		    bundle.putString("figurename", "Slashed ice cream cone");
		    I.putExtras(bundle);
			startActivity(I);
		}
		});
	}

}
