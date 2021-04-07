package frame.handlers;

import com.example.webfluxclient.model.User;
import frame.beans.MethodInfo;
import frame.beans.ServerInfo;
import frame.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author zhy
 * @since 2021/3/29 14:35
 */
public class WebClientHandler implements RestHandler {
    private WebClient webClient;

    /**
     * 初始化webClient
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * 处理Rest请求
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        Object result = null;
        WebClient.RequestBodySpec request = this.webClient.method(methodInfo.getMethod())
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                .accept(MediaType.APPLICATION_JSON);
        //判断是否带有body
        WebClient.ResponseSpec retrieve = null;
        if(methodInfo.getBody() != null){
            retrieve = request.body(methodInfo.getBody(), methodInfo.getBodyElementType()).retrieve();
        }
        else{
            retrieve = request.retrieve();
        }
        //处理异常
        retrieve.onStatus(httpStatus -> httpStatus.value() == 400,
                clientResponse -> Mono.just(new RuntimeException("Not Found")));

        //处理body
        if(methodInfo.isReturnFlux()){
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        }
        else{
            result = retrieve.bodyToMono(User.class);
        }

        return result ;
    }
}
