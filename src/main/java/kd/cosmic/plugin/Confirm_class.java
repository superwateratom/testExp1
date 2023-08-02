package kd.cosmic.plugin;


import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.ILocaleString;
import kd.bos.entity.datamodel.events.ChangeData;
import kd.bos.entity.datamodel.events.PropertyChangedArgs;
import kd.bos.form.field.BasedataEdit;
import kd.bos.form.field.events.BeforeF7SelectEvent;
import kd.bos.form.field.events.BeforeF7SelectListener;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.user.UserServiceHelper;

import java.util.EventObject;
import java.util.List;

public class Confirm_class extends AbstractFormPlugin implements BeforeF7SelectListener {

    /**
     * 注册监听
     * @param e
     */
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        // 获取申请部门控件，并注册监听为本插件
        BasedataEdit applyOrg = this.getControl("kdec_applyorg");
        applyOrg.addBeforeF7SelectListener(this);
    }

    /**
     * 基础资料控件被选择触发事件
     * 基础资料控件被点击之后，弹出基础资料列表之前执行
     * @param e
     */
    @Override
    public void beforeF7Select(BeforeF7SelectEvent e) {
        //获取当前被点击的基础资料控件的标识
        String name = e.getProperty().getName();
        // 如果是申请部门被点击
        if ("kdec_applyorg".equals(name)) {
            // 获取申请人的数据
            Object kdec_applicant = this.getModel().getValue("kdec_applicant");
            if (null != kdec_applicant) {
                // 申请人为基础资料类型，需要类型转换
                DynamicObject applicant = (DynamicObject) kdec_applicant;
                // 获取到申请人的主键
                long pkValue = (long) applicant.getPkValue();
                // 获取申请人所在的组织，这里为行政组织
                List<Long> orgsUserJoin = UserServiceHelper.getOrgsUserJoin(pkValue);
                // 构造filter的过滤条件对象，
                QFilter ids = new QFilter("id", QCP.in, orgsUserJoin);
                //ListShowParameter listShowParameter = (ListShowParameter) e.getFormShowParameter();
                //listShowParameter.getListFilterParameter().setFilter(ids);
                // 添加过滤条件给本基础资料列表
                e.addCustomQFilter(ids);
            }
        }
    }

    /**
     * 页面新建的时候触发的事件
     * @param e
     */
    @Override
    public void afterCreateNewData(EventObject e) {
        // 获取当前登录用户
        String userName = RequestContext.get().getUserName();
        // 给备注字段赋值，名称后面有空格，截取的时候需要使用
        this.getModel().setValue("kdec_remark", userName + " 需要采购：");
    }

    /**
     * 界面值改变触发的事件
     * @param e
     */
    @Override
    public void propertyChanged(PropertyChangedArgs e) {
        // 获取改变的字段的标识
        String key = e.getProperty().getName();
        // 获取改变字段的集合
        ChangeData[] changeSet = e.getChangeSet();
        // 取第一个改变字段的改动后的值（这里没有联动改变，所以只有一个改变字段）
        Object newValue = changeSet[0].getNewValue();
        // 如果是申请人改动了,需要获取到新申请人,并给备注重新赋值
        if ("kdec_applicant".equalsIgnoreCase(key)) {
            if (newValue instanceof DynamicObject) {
                // 申请人为基础资料，需要转成DynamicObject类型
                DynamicObject applicant = (DynamicObject) newValue;
                // 获取申请人的名称字段，多语言字段，取当前语言环境的文本值
                ILocaleString userName = applicant.getLocaleString("name");
                // 获取备注字段，文本字段，为string类型
                String remark = (String)this.getModel().getValue("kdec_remark");
                // 获取空格的索引
                int i = remark.indexOf(" ");
                // 截取空格之前的值
                String substring = remark.substring(i);
                // 重新给备注字段赋值
                this.getModel().setValue("kdec_remark", userName + substring);
            }
        } else if ("kdec_materiel".equalsIgnoreCase(key)) {
            // 物料字段改变之后，同步修改到备注文本中
            if (newValue instanceof DynamicObject) {
                // 物料为基础资料，强转为DynamicObject类型
                DynamicObject materiel = (DynamicObject) newValue;
                // 获取名称字段值，名称为多语言字段，取当前语言文本
                ILocaleString name = materiel.getLocaleString("name");
                //取备注字段的值
                String usage = (String) this.getModel().getValue("kdec_remark");
                // 判断是否第一次添加物料信息
                if (usage.endsWith("：")) {
                    this.getModel().setValue("kdec_remark", usage + name);
                } else {
                    this.getModel().setValue("kdec_remark", usage + "、" + name);
                }
            }
        }
    }
}