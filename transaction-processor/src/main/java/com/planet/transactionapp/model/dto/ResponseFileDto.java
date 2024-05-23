package com.planet.transactionapp.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.planet.transactionapp.model.type.StatusFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@Schema(name = "FileResponse",
        description = "Schema to hold error response information")
@JsonPropertyOrder(value = {"fileName", "fileUri", "fileUri", "size", "status"})
public class ResponseFileDto {
    @Schema(description = "Name of file")
    private String fileName;
    @Schema(description = "URI of file")
    private String fileUri;
    @Schema(description = "Status of file")
    private StatusFile status;
}
