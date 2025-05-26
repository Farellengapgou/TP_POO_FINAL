import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Conference;

import java.time.LocalDateTime;

public class TestSerialization {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Conference conf = new Conference(
                "1234567",
                "IA",
                LocalDateTime.of(2025, 5, 28, 10, 30),
                "polytech",
                100,
                "comment mieux utiliser l'IA à l'école"
        );

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(conf);
        System.out.println(json);
    }
}
