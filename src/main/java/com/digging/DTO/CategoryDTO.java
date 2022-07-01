package com.digging.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryDTO {
    //标签id
    private Long id;
    //标签名
    private String name;
    //创建日期
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //该分类下的文章集合
    private List<ArticleDTO> articleList;
}
