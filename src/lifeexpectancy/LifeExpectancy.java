package lifeexpectancy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	
	private UnfoldingMap map;
	
	private Map<String, Float> loadLifeExpectancyFromCSV(String filename)
	{
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		
		String[] rows = loadStrings(filename);
		
		for(String row : rows)
		{
			String[] col = row.split(",");
			if(!col[4].isEmpty())
			{
				try
				{
					float val = Float.parseFloat(col[5]);
					lifeExpMap.put(col[4], val);
				}
				catch (Exception e)
				{
					;
				}
			}
		}

		return lifeExpMap;
	}
	
	private void shadeCountries(List<Marker> countryMarkers, Map<String, Float> lifeMap)
	{
		for(Marker marker : countryMarkers)
		{
			String countryId = marker.getId();
			
			if(lifeMap.containsKey(countryId))
			{
				float lifeExp = lifeMap.get(countryId);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else
				marker.setColor(color(150,150,150));
		}
	}
	
	public void setup()
	{
		size(800,600, OPENGL);
		
		map = new UnfoldingMap(this, 50,50,700,500,new OpenStreetMap.OpenStreetMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		Map<String, Float> lifeMap = loadLifeExpectancyFromCSV("C:\\Users\\ravi.cherukuri\\Documents\\Coursera\\Java\\UCSDUnfoldingMaps\\data\\LifeExpectancyWorldBank.csv");
		
		List<Feature> countries = new ArrayList<Feature>();
		List<Marker> countryMarkers = new ArrayList<Marker>();
		
		countries = GeoJSONReader.loadData(this, "C:\\Users\\ravi.cherukuri\\Documents\\Coursera\\Java\\UCSDUnfoldingMaps\\data\\countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		map.addMarkers(countryMarkers);
		shadeCountries(countryMarkers, lifeMap);
	}
	
	public void draw()
	{
		background(10);
		map.draw();
	}
}

