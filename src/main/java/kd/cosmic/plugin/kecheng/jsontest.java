package kd.cosmic.plugin.kecheng;

import org.json.JSONArray;
import org.json.JSONObject;

public class jsontest {
    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 5; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",i);
            jsonObject.put("time",1);
            jsonArray.put(jsonObject);
        }

        System.out.println(jsonArray);
    }
}
