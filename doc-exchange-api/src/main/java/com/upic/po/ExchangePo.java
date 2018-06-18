/**
 * 
 */
/**
 * @author dtz
 *
 */
package com.upic.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ExchangePo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String oldUrl;

	private String targetUrl;
}