package com.prgrms.wadiz.domain.order.dto.request;

import java.util.Map;

public record OrderCreateRequest(List<Reward> orderRewards) {
}
