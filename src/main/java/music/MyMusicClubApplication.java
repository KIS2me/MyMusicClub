package music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("music.mapper")
public class MyMusicClubApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyMusicClubApplication.class, args);
    }
}
