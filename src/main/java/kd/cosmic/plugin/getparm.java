package kd.cosmic.plugin;

import kd.bos.context.RequestContext;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.form.field.BasedataEdit;
import kd.bos.form.field.events.BeforeF7SelectEvent;
import kd.bos.form.field.events.BeforeF7SelectListener;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.user.UserServiceHelper;
import kd.sdk.plugin.Plugin;

import java.util.EventObject;
import java.util.List;

/**
 * 动态表单
 */
public class getparm extends AbstractFormPlugin implements Plugin, BeforeF7SelectListener {
    private final String base_field = "yt77_basedatafield1";//在此处设置基础资料字段标识
    private final String var="number";//在此设置需要比对的基础资料字段名，如number，name


    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        BasedataEdit fedit=this.getView().getControl(base_field);
        fedit.addBeforeF7SelectListener(this);

    }

    @Override
    public void afterCreateNewData(EventObject e) {
        super.afterCreateNewData(e);
        RequestContext requestContext = RequestContext.get();
        //String string = Long.toString(userId);



    }

    @Override
    public void beforeF7Select(BeforeF7SelectEvent beforeF7SelectEvent) {
        //String key = beforeF7SelectEvent.getProperty().getName();
        if(true){

            //创建一个过滤器，QCP.equals是过滤条件，可以更改
            QFilter qFilter = new QFilter(var, QCP.equals, RequestContext.get().getUserName());

            beforeF7SelectEvent.setCancel(true);
            ListShowParameter parameter = (ListShowParameter) beforeF7SelectEvent.getFormShowParameter();
            parameter.getListFilterParameter().setFilter(qFilter);



        }
    }



}