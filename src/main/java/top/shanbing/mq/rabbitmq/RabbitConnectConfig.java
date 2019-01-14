package top.shanbing.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.impl.DefaultExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitConnectConfig {
	static Logger logger = LoggerFactory.getLogger(RabbitConnectConfig.class);
	@Value("${spring.rabbitmq.host}")
	private String mqHost;

	//@Value("${spring.rabbitmq.username}")
	private String username;

	//@Value("${spring.rabbitmq.password}")
	private String password;

	//@Value("${springl.rabbitmq.virtualHost}")
	private String virtualHost;

	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(mqHost);
		connectionFactory.setPort(ConnectionFactory.DEFAULT_AMQP_PORT);
		//connectionFactory.setUsername(username);
		//connectionFactory.setPassword(password);
		//connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setExceptionHandler(new DefaultExceptionHandler() {
			@Override
			public void handleConsumerException(Channel channel, Throwable exception, Consumer consumer,
                                                String consumerTag, String methodName) {
				logger.error(exception.getMessage(), exception);
			}
		});
		logger.info("rabbitConnect");
		return connectionFactory;

	}

}
