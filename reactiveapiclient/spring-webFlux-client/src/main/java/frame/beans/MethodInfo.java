package frame.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author zhy
 * @since 2021/3/26 16:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求方法
     */
    private HttpMethod method;

    /**
     * 请求参数（url）
     */
    private Map<String,Object> params;

    /**
     * 请求body
     */
    private Mono<?> body;

    /**
     * 返回的是Flux还是Mono
     */
    private boolean returnFlux;

    /**
     * 返回数据类型
     */
    private Class<?> returnElementType;

    /**
     * 请求体类型
     */
    private Class<?> bodyElementType;

}
