package com.sygdsoft.conf;

import com.sygdsoft.util.HotelReportView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 舒展 on 2016-03-14.
 */
@Configuration
public class JasperConfig {
    /*@Bean
    public XmlViewResolver xmlViewResolver(){
        XmlViewResolver xmlViewResolver=new XmlViewResolver();
        FileSystemResource resource=new FileSystemResource("D:/code/springGuide/MyBatis-Spring-Boot/src/main/resources/jasper-views.xml");
        xmlViewResolver.setOrder(100);
        xmlViewResolver.setLocation(resource);
        return xmlViewResolver;
    }*/
    @Bean(name = "reportView")
    public HotelReportView hotelReportView(){
        HotelReportView hotelReportView =new HotelReportView();
        return hotelReportView;
    }
}
