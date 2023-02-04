package ${pPackage}.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * page父类
 */
@Data
public class Page {
    @ApiModelProperty(value = "第几页",required = true,example = "1")
    @NotEmpty
    private Integer page;
    @ApiModelProperty(value = "每页多少行",required = true,example = "5")
    @NotEmpty
    private Integer limit;
    @ApiModelProperty(value = "排序如：+id或-id——+为顺序，-为倒序排列",required = true,example = "+id")
    @NotEmpty
    private String sort;
    public void setPage(Integer page) {
        this.page = page-1;
    }
}
