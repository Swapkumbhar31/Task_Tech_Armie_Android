package ca.lambton.task_tech_armie_android.Helper;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ListConvertor {

    @TypeConverter
    public static List<String> stringList(String paths) {
        String[] strings = paths.split(",");
        List<String> obj = new ArrayList<>();
        Collections.addAll(obj, strings);
        return obj;
    }

    @TypeConverter
    public static String string(List<String> paths) {
        StringBuilder obj = new StringBuilder();
        for (String s:paths) {
            obj.append(",").append(s);
        }
        return obj.toString();
    }
}
