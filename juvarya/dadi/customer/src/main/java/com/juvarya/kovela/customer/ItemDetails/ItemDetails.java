package com.juvarya.kovela.customer.ItemDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemDetails {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createItem(Long itemId, String description, BigDecimal unitPrice, int stockQuantity, Long supplierId) {
        String sql = "INSERT INTO items (item_id, description, unit_price, stock_quantity, supplier_id) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, itemId, description, unitPrice, stockQuantity, supplierId);
    }
}
