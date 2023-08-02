package kd.cosmic.plugin.kecheng;

import kd.bos.coderule.api.CodeRuleInfo;
import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.db.DB;
import kd.bos.db.DBRoute;
import kd.bos.entity.datamodel.IDataModel;
import kd.bos.form.IFormView;
import kd.bos.form.control.Button;
import kd.bos.form.events.BeforeDoOperationEventArgs;
import kd.bos.form.events.ClosedCallBackEvent;
import kd.bos.form.operate.FormOperate;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.isc.util.dt.D;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.coderule.CodeRuleServiceHelper;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;

/**
 * 动态表单
 */
public class getparm extends AbstractFormPlugin implements Plugin {


    @Override
    public void afterCreateNewData(EventObject e) {
        String name = getView().getFormShowParameter().getCustomParam("name");
        String class_cycle = getView().getFormShowParameter().getCustomParam("class_cycle");
        String week = getView().getFormShowParameter().getCustomParam("week");
        String lessons = getView().getFormShowParameter().getCustomParam("lessons");
        String credits = getView().getFormShowParameter().getCustomParam("credits");
        //String billno = getView().getFormShowParameter().getCustomParam("billno");
        this.getModel().setValue("yt77_textfield",name);
        this.getModel().setValue("yt77_time",class_cycle);
        this.getModel().setValue("yt77_confirm_week",week);
        this.getModel().setValue("yt77_confirm_class",lessons);
        this.getModel().setValue("yt77_confirm_credit",credits);

    }

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String operateKey = ((FormOperate) args.getSource()).getOperateKey();
        if (StringUtils.equals(operateKey,"confirm")){

                //QFilter filter = new QFilter("billno", QCP.equals,getView().getFormShowParameter().getCustomParam("billno"));
                //DynamicObjectCollection query = QueryServiceHelper.query("yt77_choose_list", "yt77_option_number", new QFilter[]{filter});
                int leftnumber = Integer.parseInt(getView().getFormShowParameter().getCustomParam("left"));
                String name = getView().getFormShowParameter().getCustomParam("name");
                String class_cycle = getView().getFormShowParameter().getCustomParam("class_cycle");
                String week = getView().getFormShowParameter().getCustomParam("week");
                String lessons = getView().getFormShowParameter().getCustomParam("lessons");
                String credits = getView().getFormShowParameter().getCustomParam("credits");
                String yt77Number = getView().getFormShowParameter().getCustomParam("yt77_number");
                String s = String.valueOf(leftnumber - 1);

                String sql = "UPDATE tk_yt77_choose_list SET yt77_leftnumber=? WHERE fbillno=?";
                DB.update(DBRoute.basedata,sql,new Object[]{s,getView().getFormShowParameter().getCustomParam("billno")});
                DynamicObject myListen = BusinessDataServiceHelper.newDynamicObject("yt77_my_listen");
                CodeRuleInfo codeRule = CodeRuleServiceHelper.getCodeRule(myListen.getDataEntityType().getName(), myListen, null);
                String number = CodeRuleServiceHelper.getNumber(codeRule, myListen);
                myListen.set("yt77_textfield",yt77Number);
                myListen.set("billno",number);
                myListen.set("auditor",RequestContext.get().getUserName());
                myListen.set("billstatus","A");
                myListen.set("yt77_textfield1",name);
                myListen.set("yt77_textfield3",class_cycle);
                myListen.set("yt77_textfield4",week);
                myListen.set("yt77_textfield5",lessons);
                myListen.set("yt77_textfield6",credits);
                myListen.set("yt77_choosepeople", RequestContext.get().getUserName());
                myListen.set("yt77_textfield7","已选上");
                



            this.getView().getParentView().showSuccessNotification("选课成功");
                this.getView().close();


        }
    }

//    @Override
//    public void click(EventObject evt) {
//        if (evt.getSource() instanceof Button){
//            Button bt =(Button) evt.getSource();
//            String key = bt.getKey();
//            if (key.equals("btnok")){
//                IFormView parentView = this.getView().getParentView();
//                IDataModel parentViewModel = parentView.getModel();
//                int option_number = (int)parentViewModel.getValue("yt77_option_number");
//                int left = option_number - 1;
//
//                parentViewModel.setValue("yt77_leftnumber",String.valueOf(left));
//                parentView.updateView();
//                this.getView().close();
//
//            }
//        }
//    }
}