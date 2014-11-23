package com.example.thedrawer;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Path;
import android.util.Log;

import com.example.thedrawer.PaintView.Point;
import com.example.thedrawer.PaintView.theLine;

public class TheDrawing {
		List<theLine> lines = new ArrayList<theLine>();
		List<Path> paths = new ArrayList<Path>();

		public TheDrawing() {
				super();
			}
		
		public boolean compare(TheDrawing target){//------compares current pic to targeted--------------------
			
			int correctLinesNumber = 0;
			Log.d("Target figure size ", ""+target.lines.size());
			Log.d("Current figure size ", ""+this.lines.size());
			boolean answer = false;
			for (theLine targetLine : target.lines) { //------each line of the right figure-------------------
				for (theLine line : this.lines) {//----------each line of figure in question------------------
					if (!targetLine.curve){// we'll compare curves more precisely
						if (dotsComparison(targetLine, line) && (targetLine.dash == line.dash)){
							correctLinesNumber++; 
//							Log.d("I compared ", " dashed? "+targetLine.dash+ " "+targetLine);
//							Log.d("to ", " dashed? "+targetLine.dash+ " "+line+ " and got true");
						}
					} else if ((dotsComparison(targetLine, line) && ((isInRadius(targetLine.bend, line.bend) && isInRadius(targetLine.bend1, line.bend1)) || 
							(isInRadius(targetLine.bend1, line.bend) && isInRadius(targetLine.bend, line.bend1)))))
						correctLinesNumber++;						
				}
			}
			if (correctLinesNumber == target.lines.size())
				answer = true;
			Log.d("I found ", ""+correctLinesNumber);
			return answer;
		}


	public boolean dotsComparison(theLine one, theLine two){ //--looks if lines ends coincide 
		
		boolean answer = false;
		if ((one.startX == two.startX) && (one.startY == two.startY)){
			if ((one.endX == two.endX) && (one.endY == two.endY))
				answer = true;
		}
			else if ((one.startX == two.endX) && (one.startY == two.endY)){
					if ((one.endX == two.startX) && (one.endY == two.startY))
						answer = true;
			} else answer = false;
		return answer;
	}
	
	public void deleteLast(boolean curve){
		if (!this.lines.isEmpty()){
			this.lines.remove(this.lines.size() - 1);
			Log.d("STRAIGHT", "straight line removal");
		}
		if (!this.paths.isEmpty() && curve){
			Log.d("PATHS", "are not empty");
			this.paths.remove(this.paths.size() - 1);
		}
	}
	
	public boolean isInRadius(Point target, Point who){
		Log.d("dot In radius? target dot: ", " "+ target + " and I check "+ who);
		Log.d("and get ", " "+ ((modul(who.x-target.x) <= 50) && (modul(who.y-target.y) <= 50)));
		if ((modul(who.x-target.x) <= 50) && (modul(who.y-target.y) <= 50)) return true;
		else return false;
	}
	
	public float modul (float number){
		if (number > 0) return number; else return -number;
	}
	
	
	//---------------constructor of target figures------------------
	public TheDrawing(int figure, PaintView leView) {
		super();
		this.lines = new ArrayList<theLine>();
		switch (figure){
		case (1): //shoe box
			this.lines.add(leView.new theLine(560, 560, 160, 560));
			this.lines.add(leView.new theLine(160, 560, 160, 280));
			this.lines.add(leView.new theLine(160, 280, 560, 280));
			this.lines.add(leView.new theLine(560, 280, 560, 560));
			this.lines.add(leView.new theLine(160, 600, 560, 600));
			this.lines.add(leView.new theLine(560, 600, 560, 920));
			this.lines.add(leView.new theLine(560, 920, 160, 920));
			this.lines.add(leView.new theLine(160, 920, 160, 600));
			this.lines.add(leView.new theLine(640, 560, 640, 280));
			this.lines.add(leView.new theLine(960, 560, 960, 280));
			this.lines.add(leView.new theLine(960, 280, 640, 280));
			this.lines.add(leView.new theLine(640, 560, 960, 560));
			break;
			
		case (2)://ice cream
			this.lines.add(leView.new theLine(400, 640, 480, 640, true));
			this.lines.add(leView.new theLine(440, 160, 440, 480, false, true, true, leView.new Point(218, 186), leView.new Point(346, 521)));
			this.lines.add(leView.new theLine(440, 160, 440, 480, false, true, true, leView.new Point(635, 206), leView.new Point(566, 504)));
			this.lines.add(leView.new theLine(480, 560, 400, 560));
			this.lines.add(leView.new theLine(400, 560, 400, 480));
			this.lines.add(leView.new theLine(400, 480, 480, 480));
			this.lines.add(leView.new theLine(320, 600, 560, 600));
			this.lines.add(leView.new theLine(560, 600, 560, 680));
			this.lines.add(leView.new theLine(560, 680, 320, 680));
			this.lines.add(leView.new theLine(320, 680, 320, 600));
			this.lines.add(leView.new theLine(640, 480, 720, 480));
			this.lines.add(leView.new theLine(680, 480, 680, 560));
			this.lines.add(leView.new theLine(640, 480, 720, 480));
			this.lines.add(leView.new theLine(640, 480, 640, 160));
			this.lines.add(leView.new theLine(640, 160, 720, 160));
			this.lines.add(leView.new theLine(720, 160, 720, 480));
			break;
		case (3): //cat corner
			this.lines.add(leView.new theLine(640, 480, 920, 480, true));
			this.lines.add(leView.new theLine(920, 480, 920, 200, true));
			this.lines.add(leView.new theLine(560, 560, 560, 200));
			this.lines.add(leView.new theLine(560, 200, 200, 200));
			this.lines.add(leView.new theLine(200, 200, 200, 560));
			this.lines.add(leView.new theLine(200, 560, 560, 560));
			this.lines.add(leView.new theLine(200, 480, 480, 480));
			this.lines.add(leView.new theLine(480, 480, 480, 200));
			this.lines.add(leView.new theLine(200, 600, 560, 600));
			this.lines.add(leView.new theLine(560, 960, 560, 600));
			this.lines.add(leView.new theLine(200, 600, 200, 960));
			this.lines.add(leView.new theLine(200, 960, 560, 960));
			this.lines.add(leView.new theLine(200, 680, 480, 680));
			this.lines.add(leView.new theLine(480, 680, 480, 960));
			this.lines.add(leView.new theLine(640, 560, 640, 200));
			this.lines.add(leView.new theLine(640, 200, 1000, 200));
			this.lines.add(leView.new theLine(1000, 200, 1000, 560));
			this.lines.add(leView.new theLine(1000, 560, 640, 560));
			break;
		case (4): //sofa
			this.lines.add(leView.new theLine(640, 440, 880, 440, true));
			this.lines.add(leView.new theLine(880, 240, 880, 440, true));
			this.lines.add(leView.new theLine(560, 560, 560, 240));
			this.lines.add(leView.new theLine(560, 240, 40, 240));
			this.lines.add(leView.new theLine(40, 240, 40, 560));
			this.lines.add(leView.new theLine(40, 560, 560, 560));
			this.lines.add(leView.new theLine(120, 240, 120, 440));
			this.lines.add(leView.new theLine(120, 440, 480, 440));
			this.lines.add(leView.new theLine(480, 440, 480, 240));
			this.lines.add(leView.new theLine(40, 600, 560, 600));
			this.lines.add(leView.new theLine(560, 600, 560, 920));
			this.lines.add(leView.new theLine(560, 920, 40, 920));
			this.lines.add(leView.new theLine(40, 920, 40, 600));
			this.lines.add(leView.new theLine(120, 920, 120, 680));
			this.lines.add(leView.new theLine(120, 680, 480, 680));
			this.lines.add(leView.new theLine(480, 680, 480, 920));
			this.lines.add(leView.new theLine(640, 560, 640, 240));
			this.lines.add(leView.new theLine(960, 560, 960, 240));
			this.lines.add(leView.new theLine(960, 240, 640, 240));
			this.lines.add(leView.new theLine(640, 560, 960, 560));
			break;
		case (5): //slashed sofa
			this.lines.add(leView.new theLine(640, 440, 880, 440, true));
			this.lines.add(leView.new theLine(880, 240, 880, 440, true));
			this.lines.add(leView.new theLine(560, 560, 560, 240));
			this.lines.add(leView.new theLine(560, 560, 40, 560));
			this.lines.add(leView.new theLine(560, 240, 280, 240));
			this.lines.add(leView.new theLine(280, 240, 40, 560));
			this.lines.add(leView.new theLine(480, 240, 480, 440));
			this.lines.add(leView.new theLine(480, 440, 120, 440));
			this.lines.add(leView.new theLine(640, 560, 640, 240));
			this.lines.add(leView.new theLine(640, 240, 960, 240));
			this.lines.add(leView.new theLine(960, 240, 960, 560));
			this.lines.add(leView.new theLine(960, 560, 640, 560));
			this.lines.add(leView.new theLine(40, 600, 560, 600));
			this.lines.add(leView.new theLine(560, 600, 560, 920));
			this.lines.add(leView.new theLine(560, 920, 40, 920));
			this.lines.add(leView.new theLine(40, 920, 40, 600));
			this.lines.add(leView.new theLine(480, 920, 480, 680));
			this.lines.add(leView.new theLine(120, 920, 120, 680));
			this.lines.add(leView.new theLine(120, 680, 480, 680));
			this.lines.add(leView.new theLine(280, 600, 280, 680));
			break;
		case (6)://ice cream cone!
			this.lines.add(leView.new theLine(240, 760, 560, 760, false, true, true, leView.new Point(294, 536), leView.new Point(535, 578)));
			this.lines.add(leView.new theLine(240, 760, 560, 760, false, true, true, leView.new Point(287, 1000), leView.new Point(545, 943)));
			this.lines.add(leView.new theLine(320, 760, 520, 760, false, true, true, leView.new Point(369, 636), leView.new Point(548, 684)));
			this.lines.add(leView.new theLine(320, 760, 520, 760, false, true, true, leView.new Point(343, 849), leView.new Point(484, 876)));
			this.lines.add(leView.new theLine(680, 320, 920, 320, false, true, true, leView.new Point(720, 424), leView.new Point(892, 476)));
			this.lines.add(leView.new theLine(680, 320, 920, 320, false, true, true, leView.new Point(741, 220), leView.new Point(864, 213)));
			this.lines.add(leView.new theLine(560, 560, 240, 560));
			this.lines.add(leView.new theLine(240, 560, 320, 240));
			this.lines.add(leView.new theLine(560, 560, 520, 400));
			this.lines.add(leView.new theLine(320, 240, 520, 400));
			this.lines.add(leView.new theLine(640, 560, 680, 320));
			this.lines.add(leView.new theLine(960, 560, 920, 320));
			this.lines.add(leView.new theLine(960, 560, 640, 560));

			break;
		}
	}
	

}//-----end class