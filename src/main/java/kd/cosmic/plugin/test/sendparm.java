package kd.cosmic.plugin.test;

import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.dataentity.entity.IDataStorage;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.entity.datamodel.ListSelectedRow;
import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
import kd.bos.form.events.BeforeDoOperationEventArgs;
import kd.bos.form.operate.FormOperate;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.list.BillList;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.sdk.plugin.Plugin;
import kd.bos.servicehelper.QueryServiceHelper;

import java.util.List;


/**
 * 动态表单
 */
public class sendparm extends AbstractFormPlugin implements Plugin {
    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String opkey = ((FormOperate)args.getSource()).getOperateKey();
        if(StringUtils.equals("openform",opkey)){
            BillList billList = this.getControl("yt77_billlistap");
            ListSelectedRow rowInfo = billList.getCurrentSelectedRowInfo();
            String billNo = rowInfo.getBillNo();
            QFilter qFilter = new QFilter("number", QCP.equals, billNo);
            DynamicObjectCollection query = QueryServiceHelper.query("yt77_test", "yt77_name,yt77_stu_id1", new QFilter[]{qFilter});
            //DynamicObject object = query.get(0);
            Object name = query.get(0).get("yt77_name");
            String s = name.toString();
            //IDataStorage storage = object.getDataStorage();

            //String s = object.toString();
            FormShowParameter parameter =new FormShowParameter();
            parameter.getOpenStyle().setShowType(ShowType.Modal);
            parameter.setFormId("yt77_put");



        }
    }
}