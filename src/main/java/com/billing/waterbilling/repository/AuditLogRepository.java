package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository
        extends JpaRepository<AuditLog,String> {

}