package kd.cosmic.plugin;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
import kd.bos.form.control.Control;
import kd.sdk.plugin.Plugin;
import kd.bos.dataentity.utils.StringUtils;

import java.util.EventObject;

/**
 * 单据界面
 */
public class testclick extends AbstractBillPlugIn implements Plugin {
    private final static String test = "yt77_credits";
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addClickListeners(test);
    }


    @Override
    public void click(EventObject evt) {
        super.click(evt);
        Control source = (Control) evt.getSource();
        if (StringUtils.equals(test, source.getKey())) {
            FormShowParameter form = new FormShowParameter();
            form.setFormId("yt77_confirm");
            form.getOpenStyle().setShowType(ShowType.Modal);
            this.getView().showForm(form);

        }
    }
}