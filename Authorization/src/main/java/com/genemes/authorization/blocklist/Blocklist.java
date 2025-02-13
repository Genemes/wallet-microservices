package com.genemes.authorization.blocklist;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("BLOCKLIST")
public record Blocklist(
        @Id Long id,
        Long userId) {
}

