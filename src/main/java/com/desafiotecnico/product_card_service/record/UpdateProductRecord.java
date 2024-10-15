package com.desafiotecnico.product_card_service.record;

import java.math.BigDecimal;

public record UpdateProductRecord(Long id, String name, BigDecimal price) { }

