package com.genemes.authorization.blocklist;

import org.springframework.data.repository.ListCrudRepository;

public interface BlocklistRepository extends ListCrudRepository<Blocklist, Long> {
    boolean existsByUserId(Long userId);
}