package com.prgrms.wadiz.global.util.resTemplate;

import lombok.Setter;

@Setter
public class SingleResult<T> extends ResponseTemplate{

    private T data;

}
