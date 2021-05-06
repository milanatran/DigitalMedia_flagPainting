package Uebung1;

import ij.ImageJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

//erste Uebung (elementare Bilderzeugung)

public class GLDM_U1_s0577618 implements PlugIn {
	
	final static String[] choices = {
		"Schwarzes Bild",
		"Gelbes Bild", 
		"Belgische Fahne",
		"Schwarz/Weiss Verlauf",
		"Horiz. Schwarz/Rot vert. Schwarz/Blau Verlauf",
		"USA Fahne",
		"Tschechische Fahne"
	};
	
	private String choice;
	
	public static void main(String args[]) {
		ImageJ ij = new ImageJ(); // neue ImageJ Instanz starten und anzeigen 
		ij.exitWhenQuitting(true);
		
		GLDM_U1_s0577618 imageGeneration = new GLDM_U1_s0577618();
		imageGeneration.run("");
	}
	
	public void run(String arg) {
		
		int width  = 566;  // Breite
		int height = 400;  // Hoehe
		
		// RGB-Bild erzeugen
		ImagePlus imagePlus = NewImage.createRGBImage("GLDM_U1", width, height, 1, NewImage.FILL_BLACK);
		ImageProcessor ip = imagePlus.getProcessor();
		
		// Arrays fuer den Zugriff auf die Pixelwerte
		int[] pixels = (int[])ip.getPixels();
		
		dialog();
		
		////////////////////////////////////////////////////////////////
		// Hier bitte Ihre Aenderungen / Erweiterungen
		
		if ( choice.equals("Schwarzes Bild") ) {
			generateBlackImage(width, height, pixels);
		}
		
		if ( choice.equals("Gelbes Bild") ) {
			generateYellowImage(width, height, pixels);
		}
		
		if ( choice.equals("Belgische Fahne") ) {
			generateBelgienImage(width, height, pixels);
		}
		
		if ( choice.equals("Schwarz/Weiss Verlauf") ) {
			generateSWVImage(width, height, pixels);
		}
		
		if ( choice.equals("Horiz. Schwarz/Rot vert. Schwarz/Blau Verlauf") ) {
			generateColorfulImage(width, height, pixels);
		}
		
		if ( choice.equals("USA Fahne") ) {
			generateUSAImage(width, height, pixels);
		}
		
		if ( choice.equals("Tschechische Fahne") ) {
			generateTschechienImage(width, height, pixels);
		}
		
		////////////////////////////////////////////////////////////////////
		
		// neues Bild anzeigen
		imagePlus.show();
		imagePlus.updateAndDraw();
	}

	private void generateTschechienImage(int width, int height, int[] pixels) {
		// Schleife ueber die y-Werte
		for (int y=0; y<height; y++) {
			// Schleife ueber die x-Werte
			for (int x=0; x<width; x++) {
				int pos = y*width + x; // Arrayposition bestimmen
				//Basisfarbe der Flagge weiß
				int r = 255;
				int g = 255;
				int b = 255;
				// Untere Hälfte rot
				if(y >= height/2) { 
					r = 255;
					g = 0;
					b = 0;
				}
				// von 0 bis 400 eine Diagonale und von 400 bis 0 eine Diagonale
				// und den Zwischenbereich blau füllen
				if((0 + y) >= x && (400 - x) >= y) {
					r = 0;
					g = 0;
					b = 255;
				}
				
				// Werte zurueckschreiben
				pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
			}
		}
	}

	private void generateUSAImage(int width, int height, int[] pixels) {
		// Schleife ueber die y-Werte
		for (int y=0; y<height; y++) {
			// Schleife ueber die x-Werte
			for (int x=0; x<width; x++) {
				int pos = y*width + x; // Arrayposition bestimmen
				// Basisfarbe der Flagge weiß
				int r = 255;
				int g = 255;
				int b = 255;
				
				// Die roten Streifen der Flagge
				// 400 / 13 Streifen = 31; in 62er Schritten nur die erste Hälfte rot malen
				if(y % 62 >= 0 && y % 62 < 31) { 
					r = 255;
					g = 0;
					b = 0;
				}
				
				//blaues Rechteck oben links
				if((x <= width / 2) && (y < 7 * 31)) { // 7 * 31, da bis zum 7. Streifen
					r = 0;
					g = 0;
					b = 255;
				}
				
				// Werte zurueckschreiben
				pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
			}
		}
	}

	private void generateColorfulImage(int width, int height, int[] pixels) {
		// Schleife ueber die y-Werte
		for (int y=0; y<height; y++) {
			// vertikaler Übergang von schwarz zu blau
			int r = 0;
			int g = 0;
			int b = (int) (y / 1.5686);// 400 / 255 = 1.5686
			// Schleife ueber die x-Werte
			for (int x=0; x<width; x++) {
				int pos = y*width + x; // Arrayposition bestimmen
				
				// horizontaler Übergang von schwarz zu rot.
				r = (int) (x / 2.2196); // 566 / 255 = 2.2196
				
				
				// Werte zurueckschreiben
				pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				
			}
		}
	}

	private void generateSWVImage(int width, int height, int[] pixels) {
		// Schleife ueber die y-Werte
		for (int y=0; y<height; y++) {
			// Schleife ueber die x-Werte
			for (int x=0; x<width; x++) {
				int pos = y*width + x; // Arrayposition bestimmen
				
				//x geteilt durch (566/255), da rgb nur bis 255 geht
				// Diese Zahl casten, damit sie als int aufgenommen wird
				int r = (int) (x / 2.2196); //geht leider nur bis 254, da sonst out of bounds
				int g = (int) (x / 2.2196);
				int b = (int) (x / 2.2196);
				
				// Werte zurueckschreiben
				pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				
			}
		}
	}

	private void generateBelgienImage(int width, int height, int[] pixels) {
		// Schleife ueber die y-Werte
		for (int y=0; y<height; y++) {
			// Schleife ueber die x-Werte
			for (int x=0; x<width; x++) {
				int pos = y*width + x; // Arrayposition bestimmen
				//Basisfarbe gelb
				int r = 255;
				int g = 255;
				int b = 0;
				
				if(x <= width/3) {
					// schwarz für erstes Drittel
					r = 0;
					g = 0;
					b = 0;
				} else if(x >= (width/3)*2) {
					//rot für letztes Drittel
					r = 255;
					g = 0;
					b = 0;
				} 
				
				
				// Werte zurueckschreiben
				pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
			}
		}
	}

	private void generateYellowImage(int width, int height, int[] pixels) {
		// Schleife ueber die y-Werte
		for (int y=0; y<height; y++) {
			// Schleife ueber die x-Werte
			for (int x=0; x<width; x++) {
				int pos = y*width + x; // Arrayposition bestimmen
				
				int r = 255;
				int g = 255;
				int b = 0;
				
				// Werte zurueckschreiben
				pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
			}
		}
	}

	private void generateBlackImage(int width, int height, int[] pixels) {
		// Schleife ueber die y-Werte
		for (int y=0; y<height; y++) {
			// Schleife ueber die x-Werte
			for (int x=0; x<width; x++) {
				int pos = y*width + x; // Arrayposition bestimmen
				
				int r = 0;
				int g = 0;
				int b = 0;
				
				// Werte zurueckschreiben
				pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
			}
		}
	}
	
	
	private void dialog() {
		// Dialog fuer Auswahl der Bilderzeugung
		GenericDialog gd = new GenericDialog("Bildart");
		
		gd.addChoice("Bildtyp", choices, choices[0]);
		
		
		gd.showDialog();	// generiere Eingabefenster
		
		choice = gd.getNextChoice(); // Auswahl uebernehmen
		
		if (gd.wasCanceled())
			System.exit(0);
	}
}

