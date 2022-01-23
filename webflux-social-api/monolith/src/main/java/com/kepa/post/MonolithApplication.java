package com.kepa.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonolithApplication.class, args);
    }


    @Autowired
    private MongoPostRepository repo;

    @Bean
    CommandLineRunner preLoadMongo() throws Exception {
        return args -> {
            repo.save(new Post("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer non felis eu tortor ullamcorper lacinia quis at elit. Morbi non lectus a purus ultrices accumsan efficitur sit amet dolor. Nulla vehicula euismod velit, sed condimentum mi ornare sed. Fusce vitae auctor sapien. Phasellus sit amet tellus vel mi fringilla laoreet vel eget quam.","Post title 1")).subscribe();
            repo.save(new Post("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer non felis eu tortor ullamcorper lacinia quis at elit. Morbi non lectus a purus ultrices accumsan efficitur sit amet dolor. Nulla vehicula euismod velit, sed condimentum mi ornare sed. Fusce vitae auctor sapien. Phasellus sit amet tellus vel mi fringilla laoreet vel eget quam.","Post title 2")).subscribe();
            repo.save(new Post("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer non felis eu tortor ullamcorper lacinia quis at elit. Morbi non lectus a purus ultrices accumsan efficitur sit amet dolor. Nulla vehicula euismod velit, sed condimentum mi ornare sed. Fusce vitae auctor sapien. Phasellus sit amet tellus vel mi fringilla laoreet vel eget quam.","Post title 3")).subscribe();
            repo.save(new Post("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer non felis eu tortor ullamcorper lacinia quis at elit. Morbi non lectus a purus ultrices accumsan efficitur sit amet dolor. Nulla vehicula euismod velit, sed condimentum mi ornare sed. Fusce vitae auctor sapien. Phasellus sit amet tellus vel mi fringilla laoreet vel eget quam.","Post title 4")).subscribe();
            repo.save(new Post("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer non felis eu tortor ullamcorper lacinia quis at elit. Morbi non lectus a purus ultrices accumsan efficitur sit amet dolor. Nulla vehicula euismod velit, sed condimentum mi ornare sed. Fusce vitae auctor sapien. Phasellus sit amet tellus vel mi fringilla laoreet vel eget quam.","Post title 5")).subscribe();

        };
    }
}

