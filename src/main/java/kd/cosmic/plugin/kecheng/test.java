package kd.cosmic.plugin.kecheng;

import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.form.events.PreOpenFormEventArgs;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.sdk.plugin.Plugin;
import kd.bos.servicehelper.BusinessDataServiceHelper;

/**
 * 动态表单
 */
public class test extends AbstractFormPlugin implements Plugin {
    @Override
    public void preOpenForm(PreOpenFormEventArgs e) {
        super.preOpenForm(e);

        DynamicObject myListen = BusinessDataServiceHelper.newDynamicObject("yt77_my_listen");
        myListen.set("yt77_textfield", RequestContext.get().getUserName());
        SaveServiceHelper.save(new DynamicObject[]{myListen});

    }
}