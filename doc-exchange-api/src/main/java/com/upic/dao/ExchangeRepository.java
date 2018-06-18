package com.upic.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upic.po.ExchangePo;
/**
 * 
 * @author dtz
 *
 */
public interface ExchangeRepository extends JpaRepository<ExchangePo, Long> {
	public ExchangePo findByOldUrl(String oldUrl);
	
	public ExchangePo findByTargetUrl(String targetUrl);
}
