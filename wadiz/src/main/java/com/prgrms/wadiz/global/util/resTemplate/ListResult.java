package com.prgrms.wadiz.global.util.resTemplate;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListResult<T> extends ResponseTemplate{

    private List<T> data;

}
