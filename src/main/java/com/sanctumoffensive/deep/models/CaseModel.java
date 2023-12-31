package com.sanctumoffensive.deep.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_CASES")
@Getter
@Setter
public class CaseModel extends RepresentationModel<CaseModel> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCase;
    private String title;
    private String description;
    private String context;
}
