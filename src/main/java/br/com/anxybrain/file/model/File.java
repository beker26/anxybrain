package br.com.anxybrain.file.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "file")
public class File {

    @Id
    private String id;

    private String path;

    public static File toFile(String name) {
        return File.builder()
                .path(name)
                .build();
    }
}
