@Configuration
public class RabbitMessageConfig {
    @Value("${spring.rabbitmq.queueName}")
    private String queueName;

    @Bean
    public Queue queue(){
        return new Queue(queueName, false);
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(rabbitMessageConverter());
        return containerFactory;
    }
    @Bean
    public Jackson2JsonMessageConverter rabbitMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public MessageListenerAdapter rabbitListenerAdapter(RabbitMQListener listener){
        return new MessageListenerAdapter(listener);
    }
}
