package com.demo.rest;

import com.demo.handler.GetRestHandler;
import com.demo.handler.PostRestHandler;
import com.demo.handler.PutRestHandler;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;

/**
 * Created by zhi.wang on 2017/12/8.
 */

public class RestAction extends BaseRestHandler{

    @Inject
    public RestAction(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);

        controller.registerHandler(RestRequest.Method.GET, "/rest", new GetRestHandler(client));
        controller.registerHandler(RestRequest.Method.POST, "/rest", new PostRestHandler(client));
        controller.registerHandler(RestRequest.Method.PUT, "/rest", new PutRestHandler(client));
    }

    @Override
    protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {


    }
}
