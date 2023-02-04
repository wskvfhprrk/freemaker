package ${pPackage}.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Integer page;
    private Integer limit;
    private Integer totalPage;
    private Long total;
    private List<T> content;
    public void setPage(Integer page) {
        this.page = page+1;
    }
}
