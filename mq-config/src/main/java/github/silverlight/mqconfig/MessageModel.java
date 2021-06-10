package github.silverlight.mqconfig;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Guang.Yang
 * @version 1.0
 * @date 2021-06-09
 */
@Data
public class MessageModel implements Serializable {

    private String id;
    private String message;
    private Date createDate;
}
