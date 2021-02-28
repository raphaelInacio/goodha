package br.com.goodha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.data.datastore.repository.config.EnableDatastoreRepositories;

@SpringBootApplication
@EnableDatastoreRepositories
public class GoodhaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodhaApplication.class, args);
    }

}
