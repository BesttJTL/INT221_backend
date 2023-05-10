package sit.int221.sasprojectkk2;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import sit.int221.sasprojectkk2.utils.ListMapper;


@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
    @Bean
    public ListMapper listMapper() {
        return ListMapper.getInstance();
    }
}
