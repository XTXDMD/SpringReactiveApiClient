package frame.proxys;

import frame.annotation.ApiServer;
import frame.beans.MethodInfo;
import frame.beans.ServerInfo;
import frame.handlers.WebClientHandler;
import frame.interfaces.ProxyCreator;
import frame.interfaces.RestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author zhy
 * @since 2021/3/26 15:34
 */

@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy:" + type);
        //根据接口得到API服务器信息
        ServerInfo serverInfo = extractServerInfo(type);

        log.info("serverInfo:" + serverInfo);
        //给每一个代理类一个实现
        RestHandler handler = new WebClientHandler();
        handler.init(serverInfo);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{type}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //根据方法和参数得到调用信息
                        MethodInfo methodInfo = extractMethodInfo(method,args);
                        log.info("methodInfo:" + methodInfo);

                        //调用rest
                        return handler.invokeRest(methodInfo);
                    }
                });
    }

    /**
     * 获取调用接口方法所需信息
     * @param method
     * @param args
     * @return
     */
    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        //获取方法的url和请求类型
        extractUrlAndMethod(method, methodInfo);
        //得到调用参数和body
        Parameter[] parameters = method.getParameters();
        extractParamsAndBody(args, methodInfo, parameters);

        //提取返回对象信息
        extractReturnInfo(method,methodInfo);


        return methodInfo;
    }

    /**
     * 提取返回信息
     * @param method
     * @param methodInfo
     */
    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
        //返回是Mono还是Flux
        //isAssignableFrom 判断类型是否是某个类的子类
        //instanceof 判断某个实例
        methodInfo.setReturnFlux(method.getReturnType().isAssignableFrom(Flux.class));
        //获得返回值类型
        Class<?> returnType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(returnType);
    }

    private Class<?> extractElementType(Type genericReturnType) {
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }

    private void extractParamsAndBody(Object[] args, MethodInfo methodInfo, Parameter[] parameters) {
        Map<String, Object> params = new LinkedHashMap<>();
        methodInfo.setParams(params);
        for (int i = 0; i < parameters.length; i++) {
            PathVariable annoPath = parameters[i].getAnnotation(PathVariable.class);
            if(annoPath != null){
                params.put(annoPath.value(),args[i]);
            }

            RequestBody annoBody = parameters[i].getAnnotation(RequestBody.class);
            if(annoBody != null){
                methodInfo.setBody((Mono<?>) args[i]);
                //请求对像实际类型
                methodInfo.setBodyElementType(extractElementType(parameters[i].getParameterizedType()));
            }

        }
    }

    /**
     * 获取请求uri和请求类型
     *
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            //GET
            if (annotation instanceof GetMapping){
                methodInfo.setUrl(((GetMapping) annotation).value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            }
            //POST
            else if (annotation instanceof PostMapping){
                methodInfo.setUrl(((PostMapping) annotation).value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            }
            //DELETE
            else if (annotation instanceof DeleteMapping){
                methodInfo.setUrl(((DeleteMapping) annotation).value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }
        }
    }

    /**
     * 获取服务信息
     *
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        ApiServer anno = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(anno.value());
        return serverInfo;
    }
}
