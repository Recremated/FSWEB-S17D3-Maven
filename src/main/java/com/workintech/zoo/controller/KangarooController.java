package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos = new HashMap<>();

    // GET /workintech/kangaroos
    @GetMapping
    public Collection<Kangaroo> getAll() {
        return kangaroos.values();
    }

    // GET /workintech/kangaroos/{id}
    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroo;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getName() == null || kangaroo.getName().isEmpty()) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("status", 400);
            errorBody.put("message", "Name cannot be null or empty");
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorBody);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return ResponseEntity.ok(kangaroo);
    }





    // PUT /workintech/kangaroos/{id}
    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable int id, @RequestBody Kangaroo kangaroo) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        kangaroo.setId(id); // ID'yi koruyoruz
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    // DELETE /workintech/kangaroos/{id}
    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable int id) {
        Kangaroo removed = kangaroos.remove(id);
        if (removed == null) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return removed;
    }
}
