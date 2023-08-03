package kd.cosmic.plugin.kecheng;
// import kd.bos.dataentity.entity.DynamicObjectCollection;
// import kd.bos.form.events.PreOpenFormEventArgs;
// import kd.bos.form.plugin.AbstractFormPlugin;
// import kd.bos.orm.query.QCP;
// import kd.bos.orm.query.QFilter;
// import kd.bos.servicehelper.QueryServiceHelper;
// import kd.sdk.plugin.Plugin;
// import org.json.JSONArray;
// import kd.bos.ext.form.control.CustomControl;

// import org.json.JSONObject;



// /**
//  * 动态表单
//  */
// public class set_json_data extends AbstractFormPlugin implements Plugin {
//     @Override
//     public void preOpenForm(PreOpenFormEventArgs e) {
//         super.preOpenForm(e);
//         QFilter filter = new QFilter("yt77_textfield1", QCP.not_equals, null);
//         DynamicObjectCollection yt77Test001 = QueryServiceHelper.query("yt77_choose_list", "yt77_name,yt77_week,yt77_lessons", new QFilter[]{filter});
//         JSONArray jsonArray = new JSONArray();
//         for (int i = 0; i < yt77Test001.size(); i++) {
//             JSONObject jsonObject = new JSONObject();
//             jsonObject.put("classname",yt77Test001.get(i).get("yt77_name"));
//             jsonObject.put("xingqi",Integer.parseInt(yt77Test001.get(i).get("yt77_week").toString()));
//             jsonObject.put("data",Integer.parseInt(yt77Test001.get(i).get("yt77_lessons").toString()));
//             jsonArray.put(jsonObject);
//         }
//         CustomControl control =  this.getView().getControl("yt77_helloworld");
//         control.setData(jsonArray);
//     }
// }

import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.ext.form.control.CustomControl;
import kd.bos.form.events.CustomEventArgs;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.sdk.plugin.Plugin;
import java.util.*;




/**
 * 动态表单
 */
public class set_json_data extends AbstractFormPlugin implements Plugin {


    @Override
    public void customEvent(CustomEventArgs e) {
        super.customEvent(e);

        CustomControl control =this.getView().getControl("yt77_helloworld");

        QFilter filter = new QFilter("yt77_number", QCP.not_equals, null);
        DynamicObjectCollection yt77Test001 = QueryServiceHelper.query("yt77_choose_list", "yt77_name,yt77_week,yt77_lessons", new QFilter[]{filter});
        List<Map<String,Object>>  mapArray  = new ArrayList();
        for (int i = 0; i < yt77Test001.size(); i++) {
            Map<String,Object> jsonObject = new HashMap();
            jsonObject.put("classname",yt77Test001.get(i).get("yt77_name"));
            jsonObject.put("xingqi",Integer.parseInt(yt77Test001.get(i).get("yt77_week").toString()));
            jsonObject.put("data",Integer.parseInt(yt77Test001.get(i).get("yt77_lessons").toString()));
            mapArray.add(jsonObject);

        }
        control.setData(mapArray);



    }
}
