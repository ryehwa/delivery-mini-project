package com.lucky_vicky.delivery_project.report.repository;

import com.lucky_vicky.delivery_project.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

}
