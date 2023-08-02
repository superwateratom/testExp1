package kd.cosmic.plugin.test;

import kd.bos.dataentity.utils.StringUtils;
import kd.bos.entity.datamodel.ListSelectedRow;
import kd.bos.form.events.BeforeDoOperationEventArgs;
import kd.bos.form.operate.FormOperate;
import kd.bos.list.BillList;
import kd.bos.list.plugin.AbstractListPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.operation.DeleteServiceHelper;
import kd.sdk.plugin.Plugin;

/**
 * 标准单据列表
 */
public class delete extends AbstractListPlugin implements Plugin {

    private static final String key = "yt77_billlistap";//取出列表
    public static final String opkey_s = "delete_hard";

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String opkey = ((FormOperate) args.getSource()).getOperateKey();
        if (StringUtils.equals(opkey, opkey_s)) {
            this.clearSelection();

//            QFilter filter = new QFilter("number", QCP.equals, billNo);
//            DeleteServiceHelper helper = new DeleteServiceHelper();
//            helper.delete("yt77_test001", new QFilter[]{filter});
            //this.getView().showMessage("已经删除");


        }
    }
}