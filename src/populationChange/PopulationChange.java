package populationChange;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.*;
import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import java.util.HashMap;
import de.fhpotsdam.unfolding.marker.Marker;

/** * PopulationChangeyMap
    * An application map displaying population growth data of 2016.
    * Author: UC San Diego Intermediate Software Development MOOC team
    * @author Ossama Ismaili
    * Date: Dec 25, 2019
**/

public class PopulationChange extends PApplet{
	
	UnfoldingMap worldMap;
	HashMap<String, Float> population;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	public void setup(){
		size(1200, 800);
		worldMap = new UnfoldingMap(this, 200, 20, 1100, 1000, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, worldMap);
		
		population = loadFromCSV("PopGrowth.csv");
		println("Loaded " + population.size() + " data entries");

		
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		worldMap.addMarkers(countryMarkers);
		
		shadeCountries();
	}
	
	public void draw() {
		// Draw map tiles and country markers
		background(0);
		worldMap.draw();
		mapLegend();
	}
	
	// Draw map legend 
	public void mapLegend(){
		fill(255, 250, 240);
		rect(25, 25, 200, 450);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Population change in 2016 (%) : ", 30, 50);
		
		//-9
		fill(color(70, 0, 0));
		ellipse(50, 90, 15, 15);
		//-9 -6
		fill(color(120, 0, 0));
		ellipse(50, 120, 15, 15);
		//-6 -3
		fill(color(200, 0, 0));
		ellipse(50, 150, 15, 15);
		//-3 -1
		fill(color(230, 0, 0));
		ellipse(50, 180, 15, 15);
		//-1 -0.5
		fill(color(255, 26, 26));
		ellipse(50, 210, 15, 15);
		//-0.5 0
		fill(color(255,102,102));
		ellipse(50, 240, 15, 15);
		//0 0.5
		fill(color(179,255,179));
		ellipse(50, 270, 15, 15);
		//0.5 1
		fill(color(77,255,77));
		ellipse(50, 300, 15, 15);
		//1 2
		fill(color(0,255,0));
		ellipse(50, 330, 15, 15);
		//2 3
		fill(color(0,179,0));
		ellipse(50, 360, 15, 15);
		//3 4
		fill(color(0,128,0));
		ellipse(50, 390, 15, 15);
		//4+
		fill(color(0,50,0));
		ellipse(50, 420, 15, 15);
		
		fill(0, 0, 0);
		text("lower than -9", 75, 90);
		text("between -9  and -6", 75, 120);
		text("between -6 and -3", 75, 150);
		text("between -3 and -1", 75, 180);
		text("between -1 and -0.5", 75, 210);
		text("between -0.5 and 0", 75, 240);
		text("between 0 and 0.5", 75, 270);
		text("between 0.5 and 1", 75, 300);
		text("between 1 and 2", 75, 330);
		text("between 2 and 3", 75, 360);
		text("between 3 and 4", 75, 390);
		text("greather than 4", 75, 420);
		
		text("Source: World Bank", 30, 440);
	}
	
	//Helper method to load life expectancy data from a csv file
	private HashMap<String, Float> loadFromCSV(String fileName) {
		
		HashMap<String, Float> populationChange = new HashMap<String, Float>();

		String[] rows = loadStrings(fileName);
		for (String row : rows) {
			String[] columns = row.split(",");
				
			if (columns.length == 60 && !columns[58].equals(" ")) {
				String key = columns[1].toString();
				key = key.replaceAll("^\"|\"$", "");
				String data = columns[58].toString();
				data = data.replaceAll("^\"|\"$", "");
			
				populationChange.put(key, Float.parseFloat(data));
			}
		}

		return populationChange;
	}
	
	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
			if (population.containsKey(countryId)) {
					float pop = population.get(countryId);
					System.out.println(pop);
					// Country color according to population change					
					if (pop < -9){
						marker.setColor(color(70, 0, 0));
					} 
					else if (pop > -9 && pop < -6){
	    				marker.setColor(color(120, 0, 0));
					} 
					else if (pop > -6 && pop < -3){
						marker.setColor(color(200, 0, 0));
					}
					else if (pop > -3 && pop < -1){
						marker.setColor(color(230, 0, 0));
					}
					else if (pop > -1 && pop < -0.5){
						marker.setColor(color(255, 26, 26));
					}
					else if (pop > -0.5 && pop < 0){
						marker.setColor(color(255, 102, 102));
					}
					else if (pop > 0 && pop < 0.5){
						marker.setColor(color(179, 255, 179));
					}
					else if (pop > 0.5 && pop < 1){
						marker.setColor(color(77, 255, 77));
					}
					else if (pop > 1 && pop < 2){
						marker.setColor(color(0, 255, 0));
					}
					else if (pop > 2 && pop < 3){
						marker.setColor(color(0, 179, 0));
					}
					else if (pop > 3 && pop < 4){
						marker.setColor(color(0, 128, 0));
					}
					else if (pop > 4){
						marker.setColor(color(0, 50, 0));
					}					
				}
				// No key data found default gray color
    			else {    				marker.setColor(color(150,150,150));    			}    		}    	}
}