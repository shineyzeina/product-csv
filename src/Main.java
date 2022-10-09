import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void createAvgCSV(double finalTotalRows, Path parentPath, Path fileName, HashMap<String, Integer>  nameTotal){
        File csvFile0 = new File(parentPath + "/../output/0_" + fileName);
        try{
        if (csvFile0.createNewFile()) {
            System.out.println("File created: " + csvFile0.getName());
        } else {
            System.out.println("File already exists.");
        }
        FileWriter fileWriter = new FileWriter(csvFile0);
        nameTotal.forEach((key, value) -> {

            try {

                fileWriter.write(key + "," + (double) value /finalTotalRows);
                fileWriter.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.close();}
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void createBrandSV(Path parentPath, Path fileName, HashMap<String, String> nameBrand ){

        File csvFile1 = new File(parentPath + "/../output/1_" + fileName);
        try{
            if (csvFile1.createNewFile()) {
                System.out.println("File created: " + csvFile1.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter(csvFile1);
            nameBrand.forEach((key, value) -> {

                try {

                    fileWriter.write(key + "," + value);
                    fileWriter.append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();}
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void CreateResults(Path in_file_path){
        try{
            Path fileName = in_file_path.getFileName();
            Path parentPath = in_file_path.getParent();

            int totalRows = 0;
            HashMap<String, Integer> nameTotal = new HashMap<>();
            HashMap<String, String> nameBrand = new HashMap<>();


            Scanner csvFile = new Scanner(new File(String.valueOf(in_file_path)));
            csvFile.useDelimiter(",");
            while(csvFile.hasNext()){
                String row = csvFile.nextLine();
                String[] cols = row.split(",");
                String key = cols[2];
                if (nameTotal.containsKey(key)) {
                    nameTotal.put(key, nameTotal.get(key) + Integer. parseInt(cols[3])); // increment the counter.
                }else{
                    nameTotal.put(key, Integer. parseInt(cols[3])); // first time
                    nameBrand.put(key,cols[4]);
                }
                totalRows++;
            }
            csvFile.close();
            if (totalRows !=0){
                createAvgCSV(totalRows, parentPath, fileName, nameTotal);
                createBrandSV( parentPath, fileName, nameBrand);
            }

        }
        catch (Error | FileNotFoundException e) {
            System.out.println(e);
        }

    }
    public static  void main(String[] args) {
        try {
            File directory = new File("./");
            String absPath = directory.getAbsolutePath();
            File dir = new File(absPath + "/samples");
            int sampleCount = 1;
            for (File file : dir.listFiles()) {
                System.out.println("Running sample" + sampleCount + " of file " + file + ": ");

                CreateResults(Path.of(String.valueOf(file)));
                sampleCount++;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Path in_file_path;
            System.out.println("Enter file path:");
            in_file_path = Path.of(br.readLine());
            CreateResults  (in_file_path);
        }
        catch (Error | IOException e){
            e.printStackTrace();
        }
    }
}
