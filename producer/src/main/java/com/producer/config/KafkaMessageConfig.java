@Configuration
public class RabbitmqMessageConfig {
    @Value("${spring.rabbitmq.topic.exchangeName}")
    private String topicExchange;
    @Value("${spring.rabbitmq.queueName}")
    private String queueName;
    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    public Queue queue(){
        return new Queue(queueName, false);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(topicExchange);
    }
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
