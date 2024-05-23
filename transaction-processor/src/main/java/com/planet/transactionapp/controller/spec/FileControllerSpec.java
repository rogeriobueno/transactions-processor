package com.planet.transactionapp.controller.spec;

import com.planet.transactionapp.controller.validator.ValidXmlFile;
import com.planet.transactionapp.model.dto.ResponseExceptionDTO;
import com.planet.transactionapp.model.dto.ResponseFileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.planet.transactionapp.controller.constants.StatusCodeConstants.*;

@Tag(
        name = "Transactions",
        description = "Operations pertaining to transactions"
)
public interface FileControllerSpec {

    @Operation(
            summary = "Upload a XML File",
            description = "REST API to upload a new XML File. The file should be in XML format and contain transaction data. The API will return a response with the file name, URI, and status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = STATUS_201_CODE,
                    description = STATUS_201_DESCRIPTION,
                    content = @Content(
                            schema = @Schema(implementation = ResponseFileDto.class),
                            examples = {
                                    @ExampleObject(name = "XMLFile1", value = "{\n\"fileName\": \"example.xml\",\n\"fileUri\": \"http://localhost:8080/api/v1/transactions/file/status/example.xml\",\n\"status\": \"Waiting\"\n}")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = STATUS_400_CODE,
                    description = STATUS_400_DESCRIPTION,
                    content = @Content(
                            schema = @Schema(implementation = ResponseExceptionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = STATUS_500_CODE,
                    description = STATUS_500_DESCRIPTION,
                    content = @Content(
                            schema = @Schema(implementation = ResponseExceptionDTO.class)
                    )
            )
    })
    ResponseEntity<?> transactionFileUpload(@Parameter(name = "file", required = true, description = "Upload a xsl and a xml file")
                                            @NotNull @ValidXmlFile @RequestParam("file") MultipartFile file);

    @Operation(
            summary = "Get the status of a file",
            description = "REST API to get the status of a file. The API will return a response with the file name, URI, and status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = STATUS_200_CODE,
                    description = STATUS_200_DESCRIPTION,
                    content = @Content(
                            schema = @Schema(implementation = ResponseFileDto.class),
                            examples = {
                                    @ExampleObject(name = "XMLFile1", value = "{\n\"fileName\": \"example.xml\",\n\"fileUri\": \"http://localhost:8080/api/v1/transactions/file/status/example.xml\",\n\"status\": \"Waiting\"\n}")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = STATUS_404_CODE,
                    description = STATUS_404_DESCRIPTION,
                    content = @Content(
                            schema = @Schema(implementation = ResponseExceptionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = STATUS_500_CODE,
                    description = STATUS_500_DESCRIPTION,
                    content = @Content(
                            schema = @Schema(implementation = ResponseExceptionDTO.class)
                    )
            )
    })
    ResponseEntity<?> transactionFileStatus(@NotEmpty @PathVariable String fileName);
}
