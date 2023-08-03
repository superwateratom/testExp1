package kd.cosmic.plugin.kecheng;

import kd.bos.dataentity.entity.DynamicObjectCollection;
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
import kd.bos.servicehelper.QueryServiceHelper;
import kd.sdk.plugin.Plugin;



/**
 * 动态表单
 */
public class kecheng_final extends AbstractFormPlugin implements Plugin {

    public static final String form_key = "yt77_billlistap";
    public static final String opkey = "choose";

    public boolean control(String class_name,int option_number,int leftnumber){

        QFilter filter1 = new QFilter("yt77_textfield1", QCP.equals,class_name);
        //sql执行
        DynamicObjectCollection query = QueryServiceHelper.query("yt77_my_lesson", "yt77_textfield1", new QFilter[]{filter1});
        //获取数组大小
        int size = query.size();
        if (size==0&&option_number>0&&leftnumber>0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String operateKey = ((FormOperate) args.getSource()).getOperateKey();
        if(StringUtils.equals(operateKey,opkey)){
            //获取选中行
            BillList billList = this.getControl(form_key);
            ListSelectedRow rowInfo = billList.getCurrentSelectedRowInfo();
            String billNo = rowInfo.getBillNo();
            //利用编号过滤所选课程
            //过滤器
            QFilter filter = new QFilter("billno", QCP.equals,billNo);
            //sql执行
            DynamicObjectCollection query = QueryServiceHelper.query("yt77_choose_list", "yt77_option_number,yt77_leftnumber", new QFilter[]{filter});
            //获取可选总人数
            Object number = query.get(0).get("yt77_option_number");
            //获取剩余人数
            Object leftnumber1 = query.get(0).get("yt77_leftnumber");
            //转换为整型
            int intnumber = Integer.parseInt(number.toString());
            int anInt = Integer.parseInt(leftnumber1.toString());
            DynamicObjectCollection query2 = QueryServiceHelper.query("yt77_choose_list", "yt77_name", new QFilter[]{filter});
            Object yt77Name = query2.get(0).get("yt77_name");

            if (control(yt77Name.toString(),intnumber,anInt)){
                //获取课程相关信息
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

                //设置弹出页面显示参数
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

                this.getView().showForm(parameter);

            }else {
                this.getView().showErrorNotification("选课失败，剩余余量不足，或您已经选过此门课程");
            }
            billList.refresh();
            billList.refreshData();
        }
    }
}
