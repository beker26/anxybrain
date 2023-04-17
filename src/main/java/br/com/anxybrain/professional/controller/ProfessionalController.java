package br.com.anxybrain.professional.controller;

import br.com.anxybrain.post.response.PostResponse;
import br.com.anxybrain.professional.response.ProfessionalResponse;
import br.com.anxybrain.professional.service.ProfessionalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/professional")
@AllArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> findProfessional(@PathVariable String id) {
        return new ResponseEntity<>(professionalService.findProfessional(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalResponse>> findProfessionalList() {
        return new ResponseEntity<>(professionalService.findProfessionalList(), HttpStatus.OK);
    }

    @GetMapping("/findProfessionalUserName/{userName}")
    public ResponseEntity<List<ProfessionalResponse>> findProfessionalListUserName(@PathVariable String userName) {
        return new ResponseEntity<>(professionalService.findProfessionalListUserName(userName), HttpStatus.OK);
    }

}
