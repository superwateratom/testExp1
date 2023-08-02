package kd.cosmic.plugin.kecheng;

import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.db.DB;
import kd.bos.db.DBRoute;
import kd.bos.entity.datamodel.ListSelectedRow;
import kd.bos.form.events.BeforeDoOperationEventArgs;
import kd.bos.form.operate.FormOperate;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.list.BillList;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.sdk.plugin.Plugin;

/**
 * 动态表单
 */
public class set_cancel extends AbstractFormPlugin implements Plugin {
    public static final String form_key = "yt77_billlistap";

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String operateKey = ((FormOperate) args.getSource()).getOperateKey();
        if (StringUtils.equals(operateKey,"cancel")){
            BillList billList = this.getControl(form_key);
            ListSelectedRow rowInfo = billList.getCurrentSelectedRowInfo();
            String billNo = rowInfo.getBillNo();
            QFilter filter = new QFilter("billno", QCP.equals,billNo);
            DynamicObjectCollection query = QueryServiceHelper.query("yt77_choose_list", "yt77_name", new QFilter[]{filter});
            Object yt77Name = query.get(0).get("yt77_name");
            String yt77Number = query.get(0).get("yt77_number").toString();
            QFilter filter1 = new QFilter("yt77_textfield1", QCP.equals,yt77Name);
            DynamicObjectCollection query1 = QueryServiceHelper.query("yt77_my_lesson",
                    "yt77_textfield1",
                    new QFilter[]{filter1});
            if (query1.size()!=0){

                int leftnumber = Integer.parseInt(getView().getFormShowParameter().getCustomParam("left"));
                String s = String.valueOf(leftnumber + 1);
                String sql = "UPDATE tk_yt77_choose_list SET yt77_leftnumber=? WHERE fbillno=?";
                DB.update(DBRoute.basedata,sql,new Object[]{s,getView().getFormShowParameter().getCustomParam("billno")});
                String delSql = "DELETE FROM tk_yt77_my_lesson WHERE yt77_textfield=?";
                DB.update(DBRoute.basedata,delSql,new  Object[]{yt77Number});
                this.getView().showSuccessNotification("done");
            }
        }
    }
}