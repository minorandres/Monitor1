/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSV;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author casa
 */
public class CSVManager {
    
    public CSVManager(){}
    
    public String[] lector() {
        BufferedReader CSVFile = null;
        String[] dataArray=null;
        try {
            CSVFile = new BufferedReader(new FileReader("conector.csv"));
            String dataRow = CSVFile.readLine();
            dataArray = dataRow.split(",");         
            CSVFile.close();         
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(CSVManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
            try {
                CSVFile.close();
            } catch (IOException ex) {
                Logger.getLogger(CSVManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print("lectura: "+dataArray.toString());
           return dataArray;
    }
    
    public void escritor(String datos){
        try
	{
	    FileWriter writer = new FileWriter("conector.csv"); 
             String[] dataArray = datos.split(",");
                for (String item : dataArray) {
                    writer.append(item); 
                    System.out.print("escribiendo: "+item + ",");
                    writer.append(',');
                } 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
    
}
