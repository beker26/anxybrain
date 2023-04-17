package br.com.anxybrain.professional.service;

import br.com.anxybrain.exception.BusinessException;
import br.com.anxybrain.professional.response.ProfessionalResponse;
import br.com.anxybrain.user.domain.User;
import br.com.anxybrain.user.repository.UserRepository;
import br.com.anxybrain.user.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfessionalService {

    private final UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    public ProfessionalResponse findProfessional(String id) {

        User user = userRepository.findByIdAndEnabledFalse(id).orElseThrow(() -> new BusinessException("User not found"));

        return ProfessionalResponse.toProfessionalResponse(user);
    }

    public List<ProfessionalResponse> findProfessionalList() {

        Query query = new Query();
        query.addCriteria(Criteria.where("isAHealthProfessional").in(true));
        query.addCriteria(Criteria.where("enabled").in(true));
        List<User> users = mongoTemplate.find(query, User.class);

        return users.stream().map(ProfessionalResponse::toProfessionalResponse).collect(Collectors.toList());
    }

    public List<ProfessionalResponse> findProfessionalListUserName(String userName) {

        Query query = new Query();
        query.addCriteria((Criteria.where("userName").regex(".*" +userName+ ".*")));
        query.addCriteria(Criteria.where("isAHealthProfessional").in(true));
        query.addCriteria(Criteria.where("enabled").in(false));
        List<User> users = mongoTemplate.find(query, User.class);

        return users.stream().map(ProfessionalResponse::toProfessionalResponse).collect(Collectors.toList());
    }
}
