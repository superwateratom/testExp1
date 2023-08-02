package kd.cosmic.plugin.kecheng;

import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.entity.datamodel.ListSelectedRow;
import kd.bos.form.CloseCallBack;
import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
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
public class kecheng_final extends AbstractFormPlugin implements Plugin {

    public static final String form_key = "yt77_billlistap";
    public static final String opkey = "choose";

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String operateKey = ((FormOperate) args.getSource()).getOperateKey();
        if(StringUtils.equals(operateKey,opkey)){
            BillList billList = this.getControl(form_key);
            ListSelectedRow rowInfo = billList.getCurrentSelectedRowInfo();
            String billNo = rowInfo.getBillNo();
            QFilter filter = new QFilter("billno", QCP.equals,billNo);
            DynamicObjectCollection query = QueryServiceHelper.query("yt77_choose_list", "yt77_option_number,yt77_leftnumber", new QFilter[]{filter});
            Object number = query.get(0).get("yt77_option_number");
            Object leftnumber1 = query.get(0).get("yt77_leftnumber");
            int intnumber = Integer.parseInt(number.toString());
            int anInt = Integer.parseInt(leftnumber1.toString());
            if (intnumber>0&&anInt>0){
                DynamicObjectCollection query_myclass = QueryServiceHelper.query("yt77_choose_list",
                        "yt77_name,yt77_class_cycle,yt77_week,yt77_lessons,yt77_credits,yt77_leftnumber,yt77_number",
                        new QFilter[]{filter});
                Object yt77_number = query_myclass.get(0).get("yt77_number");
                Object name = query_myclass.get(0).get("yt77_name");
                Object class_cycle = query_myclass.get(0).get("yt77_class_cycle");
                Object week = query_myclass.get(0).get("yt77_week");
                Object lessons = query_myclass.get(0).get("yt77_lessons");
                Object credits = query_myclass.get(0).get("yt77_credits");
                Object leftnumber = query_myclass.get(0).get("yt77_leftnumber");


                FormShowParameter parameter = new FormShowParameter();

                parameter.setFormId("yt77_confirm");
                parameter.setCustomParam("yt77_number",yt77_number.toString());
                parameter.setCustomParam("name",name.toString());
                parameter.setCustomParam("class_cycle",class_cycle.toString());
                parameter.setCustomParam("week",week.toString());
                parameter.setCustomParam("lessons",lessons.toString());
                parameter.setCustomParam("credits",credits.toString());
                parameter.setCustomParam("billno",billNo);
                parameter.setCustomParam("left",leftnumber);
                parameter.getOpenStyle().setShowType(ShowType.Modal);
//                CloseCallBack callBack = new CloseCallBack(this, "");
//                parameter.setCloseCallBack(callBack);

                this.getView().showForm(parameter);

            }else {
                this.getView().showErrorNotification("选课失败，剩余余量不足，请等待他人退选或候补");
            }
            billList.refresh();
            billList.refreshData();
        }
    }
}
