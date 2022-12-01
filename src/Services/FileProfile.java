package Services;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class FileProfile {
    private final String directory = System.getProperty("user.dir")+"/src/Profiler/";
    public void Profiling(String x_email) {
        try {
            //Whatever the file path is.
            String userDirectory = System.getProperty("user.dir");
            File statText = new File(userDirectory+"/src/Profiler/prefetch");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(x_email);
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file prefetch...");
        }
    }

    public Boolean ProfileExist(){
        boolean checkFile = new File(directory, "prefetch").exists();
        if(checkFile){
            return true;
        }else{
            return false;
        }
    }

    public String ReadProfile(){
        try(BufferedReader br = new BufferedReader(new FileReader(directory+"prefetch"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String profile = sb.toString();
            return profile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean DestroyProfile(){
        try {
            Files.deleteIfExists(
                    Paths.get(directory+"prefetch"));
        }catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        }catch (DirectoryNotEmptyException e) {
                System.out.println("Directory is not empty.");
        }catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
        return true;
    }
}
