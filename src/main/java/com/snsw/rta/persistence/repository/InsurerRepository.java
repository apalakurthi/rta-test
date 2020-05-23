package com.snsw.rta.persistence.repository;

import com.snsw.rta.persistence.Insurer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsurerRepository extends JpaRepository<Insurer, Long> {

  Insurer findByName(String name);

}
