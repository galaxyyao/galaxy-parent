package com.galaxy.common.id;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.AbstractUUIDGenerator;
import org.hibernate.id.Configurable;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class SnowFlakeGenerator extends AbstractUUIDGenerator implements Configurable {
	private IdWorker idWorker;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		if (idWorker == null) {
			// use config center to populate appid and nodeid
			idWorker = IdWorker.getDefaultInstance();
		}
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return idWorker.nextId();
	}

}
