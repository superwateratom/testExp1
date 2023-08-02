package kd.cosmic.plugin.stu_card;

import kd.bos.dataentity.utils.StringUtils;
import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;

/**
 * 动态表单
 */
public class send_item extends AbstractFormPlugin implements Plugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        addItemClickListeners("tbmain");
    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        String key =evt.getItemKey();
        if(StringUtils.equals(key,"yt77_baritemap")){
            Object value = this.getModel().getValue("yt77_amountfield");
            FormShowParameter formShowParameter = new FormShowParameter();
            formShowParameter.setFormId("yt77_stu_card_confirm");
            formShowParameter.setCustomParam("value",value);
            formShowParameter.getOpenStyle().setShowType(ShowType.Modal);
            this.getView().showForm(formShowParameter);

        }
    }
}