package com.prgrms.wadiz.global.util.resTemplate;


import lombok.Setter;

import java.util.List;

@Setter
public class ListResult<T> extends ResponseTemplate{

    private List<T> data;

}
