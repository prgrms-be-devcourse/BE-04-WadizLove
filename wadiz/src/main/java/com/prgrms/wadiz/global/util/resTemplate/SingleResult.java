package com.prgrms.wadiz.global.util.resTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends ResponseTemplate{

    private T data;

}
