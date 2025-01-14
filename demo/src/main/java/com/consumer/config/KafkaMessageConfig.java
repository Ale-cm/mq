@Configuration
@EnableKafka
public class KafkaMessageConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> messageConfig(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }
    @Bean
    public ConsumerFactory<String, MessageItem> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(messageConfig(), new StringDeserializer(), new JsonDeserializer<>(MessageItem.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageItem> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, MessageItem> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
