package kd.cosmic.plugin.kecheng;


import kd.bos.db.DB;
import kd.bos.db.DBRoute;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.sdk.plugin.Plugin;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * 动态表单
 */
public class teacher_class_form extends AbstractFormPlugin implements Plugin {
    @Override
    public void afterCreateNewData(EventObject e) {
        
        String sql = "SELECT fk_yt77_option_number FROM tk_yt77_choose_list";
        DB.queryDataSet("test",DBRoute.basedata,sql);
        //Object query = DB.query(DBRoute.basedata, sql, rs -> {
            List<String> idList = DB.query(DBRoute.basedata, sql,rs -> {
                List<String> ret = new ArrayList<>();
                while (rs.next()) {
                    ret.add(rs.getString(1));
                }
                return ret;
            });
        }

}
