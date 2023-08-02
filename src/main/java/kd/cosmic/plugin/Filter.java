package kd.cosmic.plugin;


import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.context.RequestContext;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.form.field.BasedataEdit;
import kd.sdk.plugin.Plugin;
import kd.bos.form.field.events.BeforeF7SelectEvent;
import kd.bos.form.field.events.BeforeF7SelectListener;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import java.util.EventObject;

/**
 * 单据界面
 */
public class Filter extends AbstractBillPlugIn implements Plugin,BeforeF7SelectListener {

    private final String base_field = "yt77_basedatafield";//在此处设置基础资料字段标识
    private final String var="name";//在此设置需要比对的基础资料字段名，如number，name


    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        BasedataEdit fedit=this.getView().getControl(base_field);
        fedit.addBeforeF7SelectListener(this);

    }

    @Override
    public void beforeF7Select(BeforeF7SelectEvent beforeF7SelectEvent) {
        String key = beforeF7SelectEvent.getProperty().getName();
        if(StringUtils.equals(key,base_field)){

            //创建一个过滤器，QCP.equals是过滤条件，可以更改
            QFilter qFilter = new QFilter(var,QCP.equals, RequestContext.get().getUserName());


            ListShowParameter parameter = (ListShowParameter) beforeF7SelectEvent.getFormShowParameter();
            parameter.getListFilterParameter().setFilter(qFilter);


        }
    }

}