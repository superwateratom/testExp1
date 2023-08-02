package kd.cosmic.plugin.kecheng;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.entity.datamodel.ListSelectedRow;

import kd.bos.form.FormShowParameter;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.form.ShowType;
import kd.bos.form.events.BeforeDoOperationEventArgs;
import kd.bos.form.operate.FormOperate;
import kd.bos.list.BillList;

/**
 * 单据界面
 */
public class open_confirm extends AbstractBillPlugIn{
    private static String key = "yt77_billlistap";

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String opkey = ((FormOperate)args.getSource()).getOperateKey();
        if(StringUtils.equals("choose",opkey)){
            //选课操作逻辑
            //获取选中行

            BillList billList = this.getControl(key);
            ListSelectedRow rowInfo = billList.getCurrentSelectedRowInfo();
            String billNo = rowInfo.getBillNo();//choose_list
            QFilter filter = new QFilter("number", QCP.equals, billNo);
            DynamicObjectCollection query = QueryServiceHelper.query("yt77_class_group",
                             "yt77_name,yt77_class_cycle,yt77_week,yt77_lessons,yt77_credits",
                                                                            new QFilter[]{filter});
            Object name = query.get(0).get("yt77_name");
            Object class_cycle = query.get(0).get("class_cycle");
            Object week = query.get(0).get("week");
            Object lessons = query.get(0).get("lessons");
            Object credits = query.get(0).get("credits");
            //this.getView().showMessage(billNo);



            //弹窗选课页面
            FormShowParameter parameter = new FormShowParameter();
            parameter.setFormId("yt77_confirm");

            parameter.setCustomParam("name",name.toString());
            parameter.setCustomParam("class_cycle",class_cycle.toString());
            parameter.setCustomParam("week",week.toString());
            parameter.setCustomParam("lessons",lessons.toString());
            parameter.setCustomParam("credits",credits.toString());
            parameter.getOpenStyle().setShowType(ShowType.Modal);
            //parameter.setCloseCallBack(new CloseCallBack(this));
            this.getView().showForm(parameter);



        }
    }
}