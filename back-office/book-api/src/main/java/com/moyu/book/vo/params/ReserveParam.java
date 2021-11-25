package com.moyu.book.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ReserveParam {


    @JsonSerialize(using = ToStringSerializer.class)
    private Long libraryId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 开始时间
     */
    private Long sdate;

    /**
     * 结束时间
     */
    private Long ddate;
}
