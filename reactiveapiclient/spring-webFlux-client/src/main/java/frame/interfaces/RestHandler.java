package frame.interfaces;

import frame.beans.MethodInfo;
import frame.beans.ServerInfo;

/**
 * @author zhy
 * @since 2021/3/26 16:39
 */
public interface RestHandler {

    //初始化服务器信息
    void init(ServerInfo serverInfo);

    /**
     * 调用rest请求返回接口结果
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
