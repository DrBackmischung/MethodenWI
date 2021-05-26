package de.mathisneunzig.nutzwertanalyse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		File f = new File("src/de/mathisneunzig/nutzwertanalyse/Ergebnis.txt");
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		try {
			createNutzwerttabelle(f,calcNutzwerte(readZielwert(), calcFaktoren(readGewichtung())), readKriterien(), readAlternativen());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private static void createNutzwerttabelle(File f, double[][] d, String[][] k, String[][] a) throws IOException {
		FileWriter w = null;
		try {
			w = new FileWriter(f);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println();
		int size1 = 0;
		int size2 = 0;
		for(double[] i : d) {
			size2 = 0;
			for(double j : i) {
				size2++;
			}
			size1++;
		}
		System.out.print("Kriterium___________\t");
		w.write("Kriterium___________\t");
		for(String[] s : a) {
			for(String p : s) {
				System.out.print(p+"\t");
				w.write(p+"\t");
			}
		}
		System.out.println("\n");
		w.write("\n\n");
		for(int i = 0; i<size1; i++) {
			System.out.print(""+k[0][i]+"__________\t");
			w.write(""+k[0][i]+"__________\t");
			for(int j = 0; j<size2; j++) {
				System.out.print(Math.round(100000.0 * d[i][j]) / 100000.0 + "\t");
				w.write(Math.round(100000.0 * d[i][j]) / 100000.0 + "\t");
			}
			System.out.println();
			w.write("\n");
		}
		System.out.println();
		w.write("\n");
		System.out.print("Gesamt______________\t");
		w.write("Gesamt______________\t");
		double g = 0;
		for(int i = 0; i<size2; i++) {
			g = 0;
			for(int j = 0; j<size1; j++) {
				g = Math.round(100000.0 * (g+d[j][i])) / 100000.0;
			}
			System.out.print(g+"\t");
			w.write(g+"\t");
		}
		w.close();
	}
	@SuppressWarnings("unused")
	public static double[][] calcNutzwerte(int[][] i, double[] f) {
		int size1 = 0;
		int size2 = 0;
		for(int[] j : i) {
			size2 = 0;
			for(int k : j) {
				size2++;
			}
			size1++;
		}
		double[][] d = new double[size1][size2];
		int indexj = 0;
		for(int[] j : i) {
			int indexk = 0;
			for(int k : j) {
				d[indexj][indexk] = f[indexj]*(double)k;
				indexk++;
			}
			indexj++;
		}
		return d;
	}
	public static double[] calcFaktoren(int[][] i) {
		int g = 0;
		int size = 0;
		for(int[] j : i) {
			for(int k : j) {
				g = g+k;
			}
			size++;
		}
		double[] d = new double[size];
		int index = 0;
		for(int[] j : i) {
			int wert = 0;
			for(int k : j) {
				wert = wert+k;
			}
			double r = (double)wert / (double)g;
			d[index] = r;
			index++;
		}
		return d;
	}
	public static int[][] readGewichtung() {
		File f = new File("src/de/mathisneunzig/nutzwertanalyse/lib/Gewichtung.csv");
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		TableReader tr = new TableReader();
		int[][] i = (int[][])tr.getTable(TableType.INT, f);
		return i;
	}
	public static int[][] readZielwert() {
		File f = new File("src/de/mathisneunzig/nutzwertanalyse/lib/Zielwert.csv");
		TableReader tr = new TableReader();
		int[][] i = (int[][])tr.getTable(TableType.INT, f);
		return i;
	}
	public static String[][] readKriterien() {
		File f = new File("src/de/mathisneunzig/nutzwertanalyse/lib/Kriterien.csv");
		TableReader tr = new TableReader();
		String[][] i = (String[][])tr.getTable(TableType.STRING, f);
		return i;
	}
	public static String[][] readAlternativen() {
		File f = new File("src/de/mathisneunzig/nutzwertanalyse/lib/Alternativen.csv");
		TableReader tr = new TableReader();
		String[][] i = (String[][])tr.getTable(TableType.STRING, f);
		return i;
	}
}
