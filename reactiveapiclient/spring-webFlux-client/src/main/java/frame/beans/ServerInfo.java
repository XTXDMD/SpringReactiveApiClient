package frame.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhy
 * @since 2021/3/26 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    /**
     * 服务器Url
     */
    private String url;
}
