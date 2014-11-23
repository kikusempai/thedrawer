package com.example.thedrawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.thedrawer.TheDrawing;

//import android.widget.Toast;

public class PaintView extends View implements OnTouchListener {

	TheDrawing currentFigure, targetFigurePV;

	int dotFrequency = 40;

	float touchX; // up
	float touchY; // up
	float downX; // down
	float downY; // down
	theLine current; // new line in the drawing
	theLine movement; // line that illustrates movement
	boolean move = false;
	boolean curve;
	boolean dash;
	boolean bendPoint1 = false;
	int state = 0; // 0-start 1-bend 2-finish
	Point benPo, benPo1, upPo, regPo, movPo;

	theLine thecurve; // the line that is initialized globally and will be added
						// to list only after being bent
	theLine unbent;

	List<Point> points = new ArrayList<Point>();
	List<theLine> lines = new ArrayList<theLine>();
	List<Path> paths = new ArrayList<Path>();
	Path subPath = new Path();

	Paint paint = new Paint();
	Paint subPaint = new Paint();//paint for temporary lines
	Paint curvePaint = new Paint();//paint for curves
	Paint background = new Paint();
	Path path;

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setOnTouchListener(this);
		paint.setAntiAlias(true);
		lines = new ArrayList<theLine>();
		paths = new ArrayList<Path>();
		paint.setStyle(Paint.Style.STROKE);
		subPaint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);

//		path = new Path(); // not sure if it will be helpful
		thecurve = new theLine();
		unbent = new theLine();

		currentFigure = new TheDrawing();
		targetFigurePV = new TheDrawing();


	}

	// -------------------------getter and setters for variables------------------------
	public boolean isCurve() {
		return curve;
	}

	public void setCurve(boolean curve) {
		this.curve = curve;
	}

	public boolean isDash() {
		return dash;
	}

	public void setDash(boolean dash) {
		this.dash = dash;
	}

	public TheDrawing getCurrentFigure() {
		return currentFigure;
	}

	public TheDrawing getTargetFigurePV() {
		return targetFigurePV;
	}

	public void setTargetFigurePV(TheDrawing targetFigurePV) {
		this.targetFigurePV = targetFigurePV;
	}


	@Override
	// --------------------------------on draw--------------------------------------
	public void onDraw(Canvas canvas) {

		paint.setStrokeWidth(3);


		currentFigure.paths.clear();
		paint.setColor(Color.WHITE);
		for (theLine line : currentFigure.lines) {
			// --------------draw all straight lines---------
			if (!line.dash && !line.curve) {
				canvas.drawLine(line.startX, line.startY, line.endX, line.endY, paint);
//				Log.d("Straight", " " + line.startX + line.startY + line.endX + line.endY);
			}
			if (line.dash) {// --------------draw all dashed lines---------										
//				Log.d("Dash", " " + line.startX + line.startY + line.endX + line.endY);
				dashDraw(line, canvas);
			}
			if (line.curve && line.bent) {// --------------draw curve---------
				unbent = null;
				path = new Path();
				path.moveTo(line.startX, line.startY);
				
				// path.quadTo(line.bend.x, line.bend.y, line.endX, line.endY);
				
				path.cubicTo(line.bend.x, line.bend.y, line.bend1.x, line.bend1.y, line.endX, line.endY);
//				paths.add(path);
				currentFigure.paths.add(path);
//				Log.d("currentFigure size is ", ""+currentFigure.paths.size());
//				Log.d("Curve", "start " + line.startX + line.startY +" bend1 " + line.bend.x
//						+ " " + line.bend.y + " bend2 " + line.bend1.x + " "
//						+ line.bend1.y + " end " + line.endX + line.endY);
				paint.setStyle(Paint.Style.STROKE);
				// canvas.drawPath(path, paint);
			}
		}
		paint.setColor(Color.WHITE);
		for (Path drawMe : currentFigure.paths) {
			canvas.drawPath(drawMe, paint);
		}

		paint.setColor(Color.MAGENTA);
		if (((thecurve != null) && bendPoint1) || (thecurve != null)
				&& (state == 1)) {
			unbent = thecurve;
			if (curve)
				canvas.drawLine(unbent.startX, unbent.startY, unbent.endX,
						unbent.endY, paint);
			unbent = null;
		}

		paint.setColor(Color.LTGRAY);
		if ((move && !curve) || (move && curve && state == 0))
			
			canvas.drawLine(movement.startX, movement.startY, movement.endX, movement.endY, paint);
		if (move && curve && state == 1 && !bendPoint1){//when we're setting first bendPoint
			subPath.moveTo(thecurve.startX, thecurve.startY);
			subPath.quadTo(movement.endX, movement.endY, thecurve.endX, thecurve.endY);
			canvas.drawPath(subPath, subPaint);
			subPath.reset();
		}

		if (!move && curve && state == 1 && bendPoint1){//when first bendPoint is set
			subPath.moveTo(thecurve.startX, thecurve.startY);
			subPath.quadTo(thecurve.bend.x, thecurve.bend.y, thecurve.endX, thecurve.endY);
			canvas.drawPath(subPath, subPaint);
			subPath.reset();
		}
		if (move && curve && state == 1 && bendPoint1){//when we're setting second point
			subPath.moveTo(thecurve.startX, thecurve.startY);
			subPath.cubicTo(thecurve.bend.x, thecurve.bend.y, movement.endX, movement.endY, thecurve.endX, thecurve.endY);
			canvas.drawPath(subPath, subPaint);
			canvas.drawPaint(subPaint);
			subPath.reset();
		}


	}
//--------end on Draw-----------------------------------------------------
	public boolean onTouch(View view, MotionEvent event) {
		if (((event.getX() < 1200) && (event.getY() < 560))
				|| ((event.getX() < 600) && (event.getY() > 560))) { // only area of drawing- 3/4

			
			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN: // -----------------DOWN-------------------

				current = new theLine();
				movement = new theLine();
				Point point = new Point();
				point.x = event.getX();
				point.y = event.getY();
				point = align(point);
				downX = point.x;
				downY = point.y;
				if (curve) {
					if (state == 2)
						state = 0; // means we begin to curve!
				}
				break;

			case MotionEvent.ACTION_MOVE:// ----------------------ACTION_MOVE-------------

				movement.startX = downX;
				movement.startY = downY;
				movement.endX = event.getX();
				movement.endY = event.getY();
//				if (!curve || (state == 0))
					move = true;
				break;

			case MotionEvent.ACTION_UP: // ---------------------------UP---------------
				if (curve) {
					switch (state) {

					case 1:
						if (!bendPoint1) {//catching first bending point
							bendPoint1 = true;
							Point benPo = new Point();
							benPo.x = event.getX();
							benPo.y = event.getY();
							thecurve.bend = benPo;
						} else {

							Point benPo1 = new Point();
							benPo1.x = event.getX();
							benPo1.y = event.getY();
							thecurve.bend1 = benPo1;
							thecurve.bent = true;
							currentFigure.lines.add(new theLine(thecurve.startX, thecurve.startY, thecurve.endX, thecurve.endY, false, true, true, thecurve.bend, thecurve.bend1));
							Log.d("I add curve", " "+ thecurve.startX);
							state = 2;
							bendPoint1 = false;
						}

						move = false;
						break;

					case 0:
						touchX = event.getX();
						touchY = event.getY();
						Point upPo = new Point();
						upPo.x = event.getX();
						upPo.y = event.getY();
						upPo = align(upPo);
						thecurve.startX = downX;
						thecurve.startY = downY;
						thecurve.endX = upPo.x;
						thecurve.endY = upPo.y;
						thecurve.curve = true;
						thecurve.bent = false;
						state = 1;
						move = false;

						break;
					}// end switch state
				}// end if curve
				else { // for straight lines
					touchX = event.getX();
					touchY = event.getY();
					Point regPo = new Point();
					regPo.x = event.getX();
					regPo.y = event.getY();
					point = align(regPo);
					current.startX = downX;
					current.startY = downY;
					current.endX = point.x;
					current.endY = point.y;
					if (dash)
						current.dash = true;
					lines.add(current);
					currentFigure.lines.add(current);
					move = false;
				}
				Log.d("_______", " Here we start");
				int count = 0;
				for (theLine line : currentFigure.lines){
				
					// --------------draw all straight lines---------
					if (!line.dash && !line.curve) {
						count++;
						Log.d("Straight number ", count+" " + line.startX + line.startY + line.endX + line.endY);
					}
					if (line.dash) {// --------------draw all dashed lines---------	
						count++;									
						Log.d("Dash number ", count+" " + line.startX + line.startY + line.endX + line.endY);
					}
					if (line.curve && line.bent) {// --------------draw curve---------
						count++;
						Log.d("currentFigure size is ", ""+currentFigure.paths.size());
						Log.d("Curve number ", count+" start " + line.startX + line.startY +" bend1 " + line.bend.x
								+ " " + line.bend.y + " bend2 " + line.bend1.x + " "
								+ line.bend1.y + " end " + line.endX + line.endY);
					}
				}
				Log.d("_______", " Here we finish");
			}// end switch eventAction

			invalidate();
		}
		return true;

	}

	public Point align(Point dot) { // -------------------method aligns points
									// to grid------------------------
		if ((dot.x % dotFrequency) >= dotFrequency / 2)
			dot.x = dot.x + (dotFrequency - (dot.x % dotFrequency));
		else
			dot.x = dot.x - (dot.x % dotFrequency);
		if ((dot.y % dotFrequency) >= dotFrequency / 2)
			dot.y = dot.y + (dotFrequency - (dot.y % dotFrequency));
		else
			dot.y = dot.y - (dot.y % dotFrequency);
		return dot;
	}

	public void dashDraw(theLine dashed, Canvas canvas) {// ------fully working dash drawing method------------------
		Point dot0 = new Point(), dot1 = new Point(), dot2 = new Point(), dot3 = new Point(), dot4 = new Point(), dot5 = new Point(), dot6 = new Point(), dot7 = new Point(), dot8 = new Point();
		dot0.x = dashed.startX;
		dot0.y = dashed.startY;
		dot8.x = dashed.endX;
		dot8.y = dashed.endY;
		dot4 = half(dot0, dot8);
		dot2 = half(dot0, dot4);
		dot1 = half(dot0, dot2);
		dot3 = half(dot2, dot4);
		dot6 = half(dot4, dot8);
		dot7 = half(dot6, dot8);
		dot5 = half(dot4, dot6);
		canvas.drawLine(dot0.x, dot0.y, dot1.x, dot1.y, paint);
		canvas.drawLine(dot2.x, dot2.y, dot3.x, dot3.y, paint);
		canvas.drawLine(dot4.x, dot4.y, dot5.x, dot5.y, paint);
		canvas.drawLine(dot6.x, dot6.y, dot7.x, dot7.y, paint);
		canvas.drawCircle(dot8.x, dot8.y, 2, paint);
	}

	public float modul(float number) {
		if (number > 0)
			return number;
		else
			return -number;
	}

	public Point half(Point st, Point ed) { // -------- function finds the
											// middle------------------
		Point mid = new Point();
		mid.x = (st.x + ed.x) / 2;
		mid.y = (st.y + ed.y) / 2;
		return mid;
	}

	class Point {
		float x, y;

		@Override
		public String toString() {
			return x + ", " + y;
		}

		public Point(float x, float y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		public Point() {
			super();
		}

	}

	class theLine {
		float startX, startY, endX, endY;
		boolean dash;
		boolean curve;
		boolean bent;
		Point bend;
		Point bend1;
		
		public theLine(float startX, float startY, float endX, float endY) {
			super();
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}
		
		public theLine() {
			super();
		}

		public theLine(float startX, float startY, float endX, float endY,
				boolean dash, boolean curve, boolean bent, Point bend, Point bend1) {
			super();
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			this.dash = dash;
			this.curve = curve;
			this.bent = bent;
			this.bend = bend;
			this.bend1 = bend1;
		}
		
		public theLine(float startX, float startY, float endX, float endY,
				boolean dash) {
			super();
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			this.dash = dash;
		}
				
	}

}
