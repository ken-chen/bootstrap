package myToolsTestNG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

	private List<String> urlLocation = new ArrayList<String>();

	public CsvReader() throws IOException {
		InputStream in = CsvReader.class
				.getResourceAsStream("HoldenAustralia_URL_20140421.csv");
		try {
			String line = "";
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			while ((line = reader.readLine()) != null) {

				System.out.println(line);
				urlLocation.add(line);

			}
		} finally {

			in.close();
		}
	}

	public static void main(String[] args) {
		try {
			new CsvReader();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	public List<String> getUrlLocation() {
		return urlLocation;
	}

	public void setUrlLocation(List<String> urlLocation) {
		this.urlLocation = urlLocation;
	}
}
