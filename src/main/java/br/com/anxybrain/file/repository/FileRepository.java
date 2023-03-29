package br.com.anxybrain.file.repository;


import br.com.anxybrain.file.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<File, String> {}

