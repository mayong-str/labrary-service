package cn.deyuan.librarydemo.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * author: admin
 * date: 2025/12/11 15:02
 */
@Data
@Accessors(chain = true)
@TableName("plc_info_config")
@ApiModel(value = "PlcInfoConfigDO对象", description = "图书借阅日志")
public class PlcInfoConfigDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("ip")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("端口号")
    @TableField("port")
    private String port;
}
