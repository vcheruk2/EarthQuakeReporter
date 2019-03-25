package guimodule;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MyDisplay extends PApplet {

	private UnfoldingMap map;

	public void addKey() { 
		
		textSize(22);
		fill(0,102,153);
		text(mouseX,20, 20);
		
		//fill(255,255,255);
		//rect(10,10,100,500);
		
	}

	public void setup() {
		int yellow = color(255,255,0);
		int gray = color(150,150,150);

		size(1200, 800, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 900, 700, new OpenStreetMap.OpenStreetMapProvider());
		//map = new UnfoldingMap(this, 200, 50, 900, 700, new Google.GoogleMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		Location chile = new Location(-38.29f, -73.05f);
		PointFeature chileFeature = new PointFeature(chile);
		chileFeature.addProperty("mag", 9.5);
		chileFeature.addProperty("city", "chile");
		chileFeature.addProperty("year", 1960);
		
		Location alaska = new Location(61.021f, -147.65f);
		PointFeature alaskaFeature = new PointFeature(alaska);
		alaskaFeature.addProperty("mag", 9.2);
		alaskaFeature.addProperty("city", "alaska");
		alaskaFeature.addProperty("year", 1964);
		
		Location sumatra = new Location(3.30f, 95.78f);
		PointFeature sumatraFeature = new PointFeature(sumatra);
		sumatraFeature.addProperty("mag", 9.1);
		sumatraFeature.addProperty("city", "sumatra");
		sumatraFeature.addProperty("year", 2004);
		
		Location japan = new Location(38.322f, 142.369f);
		PointFeature japanFeature = new PointFeature(japan);
		japanFeature.addProperty("mag", 9.0);
		japanFeature.addProperty("city", "japan");
		japanFeature.addProperty("year", 2011);
		
		Location kamchatka = new Location(52.76f, 160.06f);
		PointFeature kamchatkaFeature = new PointFeature(kamchatka);
		kamchatkaFeature.addProperty("mag", 9.0);
		kamchatkaFeature.addProperty("city", "kamchatka");
		kamchatkaFeature.addProperty("year", 1952);
		
		List<PointFeature> bigEq = new ArrayList<PointFeature>();
		bigEq.add(chileFeature);
		bigEq.add(alaskaFeature);
		bigEq.add(sumatraFeature);
		bigEq.add(japanFeature);
		bigEq.add(kamchatkaFeature);
		
		List<Marker> markers = new ArrayList<Marker>();
		
		for(PointFeature eq : bigEq)
		{
			SimplePointMarker mark = new SimplePointMarker(eq.getLocation(), eq.getProperties());
			
			if(eq.getIntegerProperty("year") > 1960 )
				mark.setColor(yellow);
			else
				mark.setColor(gray);
			
			markers.add(mark);
		}
		
		map.addMarkers(markers);
	}

	public void draw() {
		background(10);
		map.draw();
		addKey();
	}
}
