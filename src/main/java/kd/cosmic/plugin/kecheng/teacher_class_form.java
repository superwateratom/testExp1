package kd.cosmic.plugin.kecheng;


import com.google.gson.Gson;
import kd.bos.context.RequestContext;
import kd.bos.db.DB;
import kd.bos.db.DBRoute;
import kd.bos.ext.form.control.CustomControl;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.sdk.plugin.Plugin;


import java.math.BigInteger;
import java.util.*;

/**
 * 动态表单
 */
public class teacher_class_form extends AbstractFormPlugin implements Plugin {
    public static final String filed_key = "";
    @Override
    public void afterCreateNewData(EventObject e) {
        

        String query_sql = "SELECT fk_yt77_week,fk_yt77_name,fk_yt77_lessons FROM tk_yt77_choose_list WHERE fcreatorid=?";

        List<Map<String, Object>> query = DB.query(DBRoute.basedata, query_sql,new Object[]{new BigInteger(RequestContext.get().getUserId())}, rs -> {
            List<Map<String, Object>> mapList = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("xingqi", rs.getInt(1));
                objectMap.put("classname",rs.getString(2));
                objectMap.put("data",rs.getInt(3));
                mapList.add(objectMap);
            }
            return mapList;
        });
        CustomControl control = this.getView().getControl(filed_key);
        control.setData(query);
        Gson gson = new Gson();
        String s = gson.toJson(query);
        System.out.println(s);
    }


}
