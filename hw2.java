import java.io.FileWriter;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class hw2 {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Задачи 1-3 на обработку json строки:");
        String strIn = ReadFromFile("data.txt");
        WriteToFile("dataOut.txt", task1(strIn));
        System.out.println();      
    }

    // Метод для получения исходной json строки из файла data.txt  
    static String ReadFromFile(String filePath) 
    { 
        StringBuilder res = new StringBuilder();
        try(Scanner sc = new Scanner(new FileReader(filePath)))
            {
                res.append(sc.nextLine());
            }
            
        catch (Exception e)
            {
                e.printStackTrace();
            }
        res.delete(0,1);
        res.delete(res.length()-2, res.length()-1);
        String s = res.toString().replaceAll("[{\"]", "");    
        return s.replaceAll("},", "}"); //res.toString().replaceAll("[{\"]", "");
    }

    // Метод для задачи 1
    static String task1(String inputStr)
    {
        String[] data = inputStr.split("}");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String[] record = data[i].split(",");
            s.append("Student ");
            s.append(record[0].substring(record[0].indexOf(":") + 1));
            s.append(" has got mark ");
            s.append(record[1].substring(record[1].indexOf(":") + 1));
            s.append(" by subject ");
            s.append(record[2].substring(record[2].indexOf(":") + 1));
            s.append("\n");
        }
        int n = s.length();  
        s.delete(n-1, n-1);
        return s.toString();
    }

    // Метод для записи строки в файл dataOut.txt
    static void WriteToFile(String filePath, String data) 
    { 
        Logger logger = Logger.getAnonymousLogger();
        FileHandler fH = null;
        try {
            fH = new FileHandler("logJSON.txt");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fH);

        try (FileWriter f = new FileWriter(filePath))
            {
                f.write(data);
                logger.log(Level.INFO, "Data has been successfully recorded");
            }
            catch (IOException e) 
            {
                logger.log(Level.WARNING, e.getMessage());
            }
    }
}
