package com.sdgtt.model.auto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 信审员
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 名字
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * company.id
     */
    private Long companyId;

    /**
     * 角色：0-管理员，1-初审人员，2-终审人员，3-信贷员
     */
    private Integer role;

    /**
     * 是否可用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 上一次修改时间
     */
    private LocalDateTime updatedAt;


}
