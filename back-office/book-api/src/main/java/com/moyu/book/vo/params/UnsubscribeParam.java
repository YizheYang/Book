package com.moyu.book.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UnsubscribeParam {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long OrderId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
}
