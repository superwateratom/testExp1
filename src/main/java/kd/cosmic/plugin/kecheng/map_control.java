package kd.cosmic.plugin.kecheng;

import kd.bos.ext.form.control.MapControl;
import kd.bos.form.plugin.AbstractMobFormPlugin;

import java.util.EventObject;

import java.util.Map;
import java.math.*;

import kd.bos.ext.form.control.events.MapSelectEvent;
import kd.bos.ext.form.control.events.MapSelectListener;
import kd.bos.ext.form.dto.MapSelectPointOption;

import kd.bos.form.control.Button;


/**
 * 动态表单(移动端)
 */
public class map_control extends AbstractMobFormPlugin implements MapSelectListener {


    public static final String button_key = "btnsubmit";//按钮标识
    public static final String map_key = "yt77_mapcontrolap";//地图控件标识
    public static final String text_key = "yt77_textareafield";//文本字段标识

    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        Button control = getControl(button_key);
        control.addClickListener(this);
        MapControl mapControl = getControl(map_key);
        mapControl.addSelectListener(this);
        mapControl.setDraggable(true);
        mapControl.setDroppable(true);
    }

    @Override
    public void click(EventObject evt) {
        Object source = evt.getSource();
        if (source instanceof Button) {

            Button bt = (Button) source;
            if (bt.getKey().equals(button_key)) {
                MapControl mapControl = getControl(map_key);
                getPageCache().put("selectLocate", "true");
                mapControl.getAddress();
                this.getView().updateView(map_key);

            }

        }
    }

    @Override
    public void select(MapSelectEvent evt) {

        Map<String, Object> map = evt.getPoint();
        Map point = (Map) map.get("point");
        if ("true".equals(getPageCache().get("selectLocate"))) {
            getPageCache().remove("selectLocate");
            MapControl mapControl = getControl(map_key);
            MapSelectPointOption mapSelectPointOption = new MapSelectPointOption();
            mapSelectPointOption.setLng(((BigDecimal) point.get("lng")).doubleValue());
            mapSelectPointOption.setLat(((BigDecimal) point.get("lat")).doubleValue());
            mapControl.selectPoint(mapSelectPointOption);
            getModel().setValue(text_key, String.valueOf(map.get("title"))
                    + "{" + String.valueOf(mapSelectPointOption.getLng()) +
                    String.valueOf(mapSelectPointOption.getLat()) + "}");
            this.getView().showSuccessNotification("签到成功");
        }
    }
}