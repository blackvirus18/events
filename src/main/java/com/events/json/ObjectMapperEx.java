package com.events.json;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.InitializingBean;

public class ObjectMapperEx extends ObjectMapper implements InitializingBean {

	public ObjectMapperEx() {
	}

	//@Override
	public void afterPropertiesSet() throws Exception {
		setSerializationConfig(getSerializationConfig()
				.withSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY));
		// configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
		// configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
		configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	}
}

