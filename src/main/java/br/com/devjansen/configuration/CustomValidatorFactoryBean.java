package br.com.devjansen.configuration;

import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Configuration;
import java.util.Optional;

public class CustomValidatorFactoryBean extends LocalValidatorFactoryBean {

	@Override
	protected void postProcessConfiguration(Configuration<?> configuration) {
		Optional
				.of(configuration)
				.filter(HibernateValidatorConfiguration.class::isInstance)
				.map(HibernateValidatorConfiguration.class::cast)
				.ifPresent(config -> config.propertyNodeNameProvider(new JacksonPropertyNodeNameProvider()));
		super.postProcessConfiguration(configuration);
	}

}
