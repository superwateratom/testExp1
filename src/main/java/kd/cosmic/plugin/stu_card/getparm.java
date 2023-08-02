package kd.cosmic.plugin.stu_card;

import kd.bos.form.plugin.AbstractFormPlugin;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;

/**
 * 动态表单
 */
public class getparm extends AbstractFormPlugin implements Plugin {
    @Override
    public void afterCreateNewData(EventObject e) {
        super.afterCreateNewData(e);
        Object value = this.getView().getFormShowParameter().getCustomParam("value");
        this.getModel().setValue("yt77_amountfield",value);
    }
}