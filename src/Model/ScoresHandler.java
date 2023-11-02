package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScoresHandler {
	private String  filename ;

	public ScoresHandler(String filename) {
		this.filename = filename;
	}

	public void writeScores(ArrayList<PlayerScore> allScores) throws IOException {
		verifyTopPlayerFileExists();
		FileWriter csvWriter = new FileWriter(filename, false);
		for (int i = 0; i < allScores.size(); i++) {
			String str = allScores.get(i).getName() + "," 
							+ allScores.get(i).getScore() + "," 
							+ allScores.get(i).getLevelName() + "\n";
			csvWriter.append(str);
		}
		csvWriter.flush();
		csvWriter.close();
	}

	public ArrayList<PlayerScore> readScores() throws IOException {
		verifyTopPlayerFileExists();
		ArrayList<PlayerScore> scores = new ArrayList<PlayerScore> ();
		
		BufferedReader csvReader = new BufferedReader(new FileReader(filename));
		String row;
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			if(data.length<3)
				continue;
			scores.add(new PlayerScore(data[0], Integer.parseInt(data[1]), data[2]));
		}
		csvReader.close();

		return scores;
	}

	private void verifyTopPlayerFileExists() throws IOException {
	    File file = new File(filename);

	    if (!file.isFile() && ! file.createNewFile())
	        throw new IOException("Error creating new file: " + file.getAbsolutePath());
	}

	public boolean isFileExists() {
		File file = new File(filename);
		return  file.exists();
	}

	public void clear() throws IOException {
		FileWriter csvWriter = new FileWriter(filename);
		csvWriter.write("");
		csvWriter.close();
	}
}
