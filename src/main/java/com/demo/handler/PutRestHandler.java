package com.demo.handler;

import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhi.wang on 2017/12/8.
 */
public class PutRestHandler implements RestHandler {
    private final ESLogger esLogger = ESLoggerFactory.getLogger(GetRestHandler.class.getName());

    private Client client;
    private String prefix;

    public PutRestHandler(Client client) {
        this.client = client;
    }

    @Override
    public void handleRequest(RestRequest request, RestChannel channel) throws Exception {

        /* 获取参数的方法 */
        prefix = request.param("prefix");

        /* 执行具体的动作 */
        NodesInfoResponse response = client.admin().cluster().prepareNodesInfo().all().execute().actionGet();
        List<String> nodes = new ArrayList<>();
        for (NodeInfo nodeInfo : response.getNodes()) {
            String nodeName = nodeInfo.getNode().getName();
            nodes.add(nodeName);
        }

        /* 回传数据 */
        try {
            sendResponse(request, channel, nodes);
        } catch (IOException ioe) {
            esLogger.error("Error sending response", ioe);
        }
    }

    /**
     * 定义回传数据接口，注意XContentBuilder的多种功能
     * @param request
     * @param channel
     * @param nodes
     * @throws IOException
     */
    private void sendResponse(RestRequest request, RestChannel channel, List nodes) throws IOException {

        XContentBuilder builder = channel.newBuilder();
        builder.startObject().startArray("nodes");
        if (!nodes.isEmpty()) {
            builder.value(nodes);
        }
        builder.endArray().endObject();

        channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
    }
}
