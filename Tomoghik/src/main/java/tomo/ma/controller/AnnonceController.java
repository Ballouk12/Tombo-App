package tomo.ma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tomo.ma.dto.AnnonceDTO;
import tomo.ma.model.Annonce;
import tomo.ma.service.AnnonceService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("annonce")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

   /* @PostMapping("/create")
    public ResponseEntity<?> createAnnonce(@RequestBody Annonce annonce) {
        Optional<Annonce> savedAnnonce = annonceService.saveAnnonce(annonce);
        if(savedAnnonce.isPresent()) {return ResponseEntity.ok(Map.of("message", "Annonce créée avec succès", "id", savedAnnonce.get().getId()));}
        else {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }*/

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAnnonce(@RequestPart("annonce") String annonceJson ,@RequestPart("images") List<MultipartFile> images){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Annonce annonce = objectMapper.readValue(annonceJson, Annonce.class);

            Optional<Annonce> savedAnnonce = annonceService.createAnnonce(annonce, images);

            if (savedAnnonce.isPresent()) {
                return ResponseEntity.ok(Map.of("message", "Annonce créée avec succès", "id", savedAnnonce.get().getId()));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid annonce JSON");
        }
    }


    @PutMapping("update/{id}")
    public Annonce update(@PathVariable long id ,@RequestBody Annonce annonce) {
        return annonceService.updateAnnonce(annonce ,id).orElse(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        if (annonceService.deleteAnnonce(id)) { return  ResponseEntity.ok("supprimer avec succes"); }
        else { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
    }

    @GetMapping("getall/{id}")
    public ResponseEntity<List<AnnonceDTO>> getAll(@PathVariable long id) {
        Optional<List<AnnonceDTO>> annonces = annonceService.getAnnoncesByUserDTO(id);
        if(annonces.isPresent()) { return  ResponseEntity.ok(annonces.get());}
        else { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
    }

   /* @GetMapping("getall")
    public ResponseEntity<List<Annonce>> getAllAnnonce() {
        Optional<List<Annonce>> annonces = annonceService.getAllAnnonces();
        if(annonces.isPresent()) { return  ResponseEntity.ok(annonces.get());}
        else { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
    } */

    @GetMapping("/getall")
    public ResponseEntity<List<AnnonceDTO>> getAllAnnonce() {
        List<AnnonceDTO> annonces = annonceService.getAllAnnoncesDTO();
        return ResponseEntity.ok(annonces);
    }

}
