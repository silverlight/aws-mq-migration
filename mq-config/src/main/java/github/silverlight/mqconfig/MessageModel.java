package github.silverlight.mqconfig;

import lombok.Data;
import org.springframework.amqp.core.MessageProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Guang.Yang
 * @version 1.0
 * @date 2021-06-09
 */
@Data
public class MessageModel extends MessageProperties implements Serializable {

    private String id;
    private String message;
    private Date createDate;
}
