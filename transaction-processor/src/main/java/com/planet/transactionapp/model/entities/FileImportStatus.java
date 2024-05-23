package com.planet.transactionapp.model.entities;

import com.planet.transactionapp.model.type.StatusFile;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "FILE_STATUS")
public class FileImportStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true, name = "file_name", length = 255)
    private String fileName;
    @Column(name = "import_date", nullable = false)
    private LocalDateTime importDate;
    @Lob
    @Column(name = "description", nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusFile status;

}
