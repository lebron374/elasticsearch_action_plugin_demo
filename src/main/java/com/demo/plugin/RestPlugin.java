package com.demo.plugin;

import com.demo.rest.RestAction;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;

/**
 * Created by zhi.wang on 2017/12/8.
 */
public class RestPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "rest demo";
    }

    @Override
    public String description() {
        return "rest demo";
    }

    /**
     *
     * @param module
     */
    public void onModule(RestModule module) {
        module.addRestAction(RestAction.class);
    }

}
