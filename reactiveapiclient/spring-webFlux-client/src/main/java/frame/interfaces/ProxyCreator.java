package frame.interfaces;

/**
 * 创建代理类接口
 * @author zhy
 * @since 2021/3/26 15:13
 */
public interface ProxyCreator {
    /**
     * 创建代理类
     */
    Object createProxy(Class<?> type);

}
