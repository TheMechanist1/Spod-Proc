package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpodProc {
    public static void main(String[] args) {
        String fileName = "spodproc/src/resources/endsong_0.json";
        JsonArray masterArray = Helper.readJsonFile(fileName);
        List<EndSongObject> endSongObjectArray = new ArrayList<EndSongObject>();

        //Converte the JsonArray to a list of EndSongObjects
        for (JsonElement jsonElement : masterArray) {
            EndSongObject endSongObject = new EndSongObject(jsonElement.getAsJsonObject());
            endSongObjectArray.add(endSongObject);
        }

        Collections.sort(endSongObjectArray, new MyComparator());

        try {
            FileWriter writer = new FileWriter("spodproc/src/resources/endsong_1.json");
            //clear file
            writer.write("");
            writer.flush();
            //write new data
            for (EndSongObject obj : endSongObjectArray) {
                //If incognito mode is on, don't write the track name
                switch(obj.getIncognito_mode() ? "on" : "off") {
                    case "on":
                        break;
                    case "off":
                        writer.write(obj.toString());
                        break;
                    default:
                        writer.write("???" + obj.toString());
                        break;
                }
            }
            writer.close();
            System.out.println("File written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }  
}

//Compares two EndSongObjects by their timestamps
class MyComparator implements Comparator<EndSongObject> {
    @Override
    public int compare(EndSongObject o1, EndSongObject o2) {
        return o1.getTs().compareTo(o2.getTs());
    }
}
