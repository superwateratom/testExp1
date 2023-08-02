package kd.cosmic.plugin.kecheng;

import kd.bos.dataentity.utils.StringUtils;
import kd.bos.form.events.BeforeDoOperationEventArgs;
import kd.bos.form.operate.FormOperate;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.metadata.dao.MetaCategory;
import kd.bos.metadata.dao.MetadataDao;
import kd.bos.metadata.form.ControlAp;
import kd.bos.metadata.form.FormMetadata;
import kd.sdk.plugin.Plugin;

import java.util.HashMap;

/**
 * 动态表单
 */
public class big_text extends AbstractFormPlugin implements Plugin {

    public static final String you_opkey = "big";//填写操作标识

    @Override
    public void beforeDoOperation(BeforeDoOperationEventArgs args) {
        super.beforeDoOperation(args);
        String operateKey = ((FormOperate) args.getSource()).getOperateKey();
        String formId = this.getView().getFormShowParameter().getFormId();
        String id = MetadataDao.getIdByNumber(formId, MetaCategory.Form);
        FormMetadata metadata = (FormMetadata) MetadataDao.readRuntimeMeta(id, MetaCategory.Form);
        if (StringUtils.equals(you_opkey, operateKey)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            HashMap<String, Object> h = new HashMap<>();
            hashMap.put("fs", "20px");//字体大小调整
            h.put("h", "200px");//控件高度调整
            for (ControlAp<?> item : metadata.getItems()) {
                String name = item.getKey();
                if (name.equals("fs_baseinfo")||name.equals("yt77_test001"))//此判断用处在于跳过不需要进行样式变换的字段，请填写字段标识
                {
                    continue;
                }
                this.getView().updateControlMetadata(name, hashMap);
                this.getView().updateControlMetadata(name,h);
            }
        }
    }

}
