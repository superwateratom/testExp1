package kd.cosmic.plugin;

import java.util.EventObject;
import kd.bos.dataentity.utils.StringUtils;

import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
import kd.bos.form.control.Control;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.sdk.plugin.Plugin;

/**
 * 动态表单
 */
public class open_form extends AbstractFormPlugin implements Plugin {
    private final static String key = "yt77_baritemap";

    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addClickListeners(key);


    }

    @Override
    public void click(EventObject evt) {
        super.click(evt);
        Control source = (Control) evt.getSource();
        if (StringUtils.equals(key, source.getKey())) {
            FormShowParameter form = new FormShowParameter();
            form.setFormId("yt77_confirm");
            form.getOpenStyle().setShowType(ShowType.Modal);
            this.getView().showForm(form);
        }
    }
}