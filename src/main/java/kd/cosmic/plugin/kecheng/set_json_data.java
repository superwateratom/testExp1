package kd.cosmic.plugin.kecheng;


import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.ext.form.control.CustomControl;
import kd.bos.form.events.CustomEventArgs;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.sdk.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONObject;



/**
 * 动态表单
 */
public class set_json_data extends AbstractFormPlugin implements Plugin {


    @Override
    public void customEvent(CustomEventArgs e) {
        super.customEvent(e);
        String eKey = e.getKey();
        String args = e.getEventArgs();
        String eventName = e.getEventName();
        CustomControl control =this.getView().getControl("yt77_helloworld");
        QFilter filter = new QFilter("yt77_textfield1", QCP.not_equals, null);
        DynamicObjectCollection yt77Test001 = QueryServiceHelper.query("yt77_choose_list", "yt77_name,yt77_week,yt77_lessons", new QFilter[]{filter});
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < yt77Test001.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("classname",yt77Test001.get(i).get("yt77_name"));
            jsonObject.put("xingqi",Integer.parseInt(yt77Test001.get(i).get("yt77_week").toString()));
            jsonObject.put("data",Integer.parseInt(yt77Test001.get(i).get("yt77_lessons").toString()));
            jsonArray.put(jsonObject);

        }
        control.setData(jsonArray);


    }
}