package com.mystig.backend.repository.transport;

import com.mystig.backend.model.transport.Transport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransportRepository extends JpaRepository<Transport, UUID>, JpaSpecificationExecutor<Transport> {}

