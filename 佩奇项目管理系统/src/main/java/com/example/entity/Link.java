package com.example.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import java.math.BigDecimal;


@TableName("t_link")
public class Link extends Model<Link> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 名称 
      */
    private String name;

    /**
      * 地址 
      */
    private String url;

    /**
      * 类型 
      */
    private String type;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }

    public String getUrl() {
      return url;
    }
    public void setUrl(String url) {
      this.url = url;
    }

    public String getType() {
      return type;
    }
    public void setType(String type) {
      this.type = type;
    }


}
