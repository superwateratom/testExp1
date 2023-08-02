package kd.cosmic;

import kd.bos.ext.form.control.CustomControl;
import kd.bos.form.control.Control;
import kd.bos.form.events.CustomEventArgs;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;

/**
 * 动态表单
 *
 */
public class set_cust extends AbstractFormPlugin implements Plugin {
    @Override
    public void customEvent(CustomEventArgs e) {
        super.customEvent(e);
        String key =e.getKey();

    }

    @Override
    public void afterBindData(EventObject e) {
        super.afterBindData(e);
        CustomControl control = this.getView().getControl("yt77_helloword");


    }
}