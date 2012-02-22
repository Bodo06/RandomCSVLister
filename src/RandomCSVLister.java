import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class RandomCSVLister {
	
	String fileNameIn, fileNameOut;
	int size;
	
	public static void main(String[] args) {
		
		RandomCSVLister rLister = new RandomCSVLister(args[0], args[1], new Integer(args[2]));
		rLister.start();
	}
	
	public RandomCSVLister(String fileNameIn, String fileNameOut, int size) {
		this.fileNameIn = fileNameIn;
		this.fileNameOut = fileNameOut;
		this.size = size;
	}
	
	private void start() {
		
		try {
			System.out.println("Starting process.");
			writeCSV(getRandomEntrys(readCSV(fileNameIn), size), fileNameOut);
			System.out.println("Done.");
			
		} catch (IOException e) {
			System.err.println("An error occured:");
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	private ArrayList<ArrayList<String>> readCSV(String filename) throws IOException {
		System.out.println("Starting reading the file.");
		BufferedReader fileReader = new BufferedReader(new FileReader(filename));
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		String zeile;
		
		while ((zeile = fileReader.readLine()) != null) {
			if (zeile == "" || zeile == " ")
				continue;
			list.add(new ArrayList<String>(Arrays.asList(zeile.split(";"))));
		}
		
		System.out.println("Finished reading the file.");
		
		return list;
	}
	
	private ArrayList<ArrayList<String>> getRandomEntrys(ArrayList<ArrayList<String>> list, int size) {
		
		System.out.println("Starting extracting random rows.");
		
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
		int count = list.size();
		
		if (count == 0)
			return ret;
		
		while (ret.size() < size) {
			try {
				int index = (int) Math.round(Math.random() * count);
				ArrayList<String> row = list.get(index);
				ret.add(row);
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		
		System.out.println("Finished extracting random rows.");
		
		return ret;
	}
	
	private void writeCSV(ArrayList<ArrayList<String>> list, String filename) throws IOException {
		
		System.out.println("Starting writing new file.");
		/*
		File file = new File(filename);
		if (file.exists())
			file.delete();
		*/
		FileWriter writer = new FileWriter(filename);
		Iterator<ArrayList<String>> zeilen = list.iterator();
		Iterator<String> spalten;
		String zeile, spalte, trennzeichen = ";";
		
		while (zeilen.hasNext()) {
			spalten = zeilen.next().iterator();
			zeile = "";
			
			while (spalten.hasNext()) {
				spalte = spalten.next();
				zeile += ((spalte == null || spalte == "" || spalte == " ") ? "" : spalte) + trennzeichen;
			}
			
			writer.write(zeile + System.getProperty("line.separator"));
		}
		
		writer.flush();
		writer.close();
		
		System.out.println("Finished writing new file.");
	}
}
