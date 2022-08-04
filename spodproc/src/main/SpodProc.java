package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

        Collections.sort(endSongObjectArray, new PlatformComparator());
        Helper.writeJsonFile(endSongObjectArray, "spodproc/src/resources/endsong_1.json");

        System.out.println(Helper.getTotalMs(endSongObjectArray));
    }    
}

//Compares two EndSongObjects by their timestamps
class TimeStampComparator implements Comparator<EndSongObject> {
    @Override
    public int compare(EndSongObject o1, EndSongObject o2) {
        return o1.getTs().compareTo(o2.getTs());
    }
}

//Compares two EndSongObjects by their platform
class PlatformComparator implements Comparator<EndSongObject> {
    @Override
    public int compare(EndSongObject o1, EndSongObject o2) {
        return Comparator.comparing(EndSongObject::getPlatform).thenComparing(EndSongObject::getTs).compare(o1, o2);
    }
}
