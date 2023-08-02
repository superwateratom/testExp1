package kd.cosmic.plugin.test;


import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.entity.datamodel.ListSelectedRow;
import kd.bos.form.events.BeforeDoOperationEventArgs;
import kd.bos.form.operate.FormOperate;
import kd.bos.list.BillList;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.sdk.plugin.Plugin;
import kd.bos.servicehelper.operation.DeleteServiceHelper;


/**
 * 单据界面
 */
public class delete_data_test extends AbstractBillPlugIn implements Plugin {


    private static final String key = "yt77_billlistap";//取出列表
    public static final String opkey_s = "delete_hard";

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String opkey = ((FormOperate) args.getSource()).getOperateKey();
        if (StringUtils.equals(opkey, opkey_s)) {
            BillList billList = this.getControl(key);
            ListSelectedRow rowInfo = billList.getCurrentSelectedRowInfo();
            String billNo = rowInfo.getBillNo();
            QFilter filter = new QFilter("number", QCP.equals, billNo);
            DeleteServiceHelper helper = new DeleteServiceHelper();
            //helper.delete("entryentity", new QFilter[]{filter});
            //this.getView().showMessage("已经删除");


        }
    }
}