package unlenen.cloud.openstack.be.modules.orchestration.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import unlenen.cloud.openstack.be.constant.FileUtil;
import unlenen.cloud.openstack.be.exception.UnvalidCallException;
import unlenen.cloud.openstack.be.model.response.ErrorInfo;
import unlenen.cloud.openstack.be.model.response.FileUploadResponse;
import unlenen.cloud.openstack.be.model.response.OpenStackResponse;
import unlenen.cloud.openstack.be.modules.orchestration.service.OrchestrationService;

/**
 *
 * @author Nebi
 */
@RestController
@RequestMapping("/orchestration/v1")
public class OrchestrationController {

    @Autowired
    OrchestrationService orchestrationService;

    public ResponseEntity<FileUploadResponse> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            createDirIfNotExist();
            List<String> fileNames = new ArrayList<>();

            // read and write the file to the local folder
            Arrays.asList(files).stream().forEach(file -> {
                byte[] bytes = new byte[0];
                try {
                    bytes = file.getBytes();
                    Files.write(Paths.get(FileUtil.folderPath + file.getOriginalFilename()), bytes);
                    fileNames.add(file.getOriginalFilename());
                } catch (IOException e) {

                }
            });

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new FileUploadResponse("Files uploaded successfully: " + fileNames));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new FileUploadResponse("Exception to upload files!"));
        }
    }

    private void createDirIfNotExist() {
        // create directory to save the files
        File directory = new File(FileUtil.folderPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public ResponseEntity<String[]> getListFiles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new File(FileUtil.folderPath).list());
    }

    @PostMapping("/stacks")
    public ResponseEntity<OpenStackResponse> createStack(@RequestHeader("token") String token,
            @RequestParam String name, @RequestParam String templateFile, @RequestParam String envFile)
            throws IOException {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            Path templatePath = Path.of(templateFile);
            byte[] templateContent = null;
            try {
                templateContent = Files.readAllBytes(templatePath);
            } catch (final IOException e) {
            }
            MultipartFile templateMultipartFile = new MockMultipartFile("template.yaml", "template.yaml", "text/plain",
                    templateContent);

            Path envPath = Path.of(envFile);
            byte[] envContent = null;
            try {
                envContent = Files.readAllBytes(envPath);
            } catch (final IOException e) {
            }
            MultipartFile envMultipartFile = new MockMultipartFile("env.yaml", "env.yaml", "text/plain", envContent);

            MultipartFile[] files = { templateMultipartFile, envMultipartFile };
            uploadFiles(files);
            String[] filePaths = new File(FileUtil.folderPath).list();
            openStackResponse
                    .setOpenStackResult(orchestrationService.createStack(token, name,
                            FileUtil.folderPath + filePaths[0], FileUtil.folderPath + filePaths[1]));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        } finally {
            File directoryToDelete = new File(FileUtil.folderPath);
            FileSystemUtils.deleteRecursively(directoryToDelete);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @GetMapping("/stacks")
    public ResponseEntity<OpenStackResponse> getStacks(@RequestHeader("token") String token) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            openStackResponse.setOpenStackResult(orchestrationService.getStacks(token));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    @DeleteMapping("/stacks/{stack_name}/{stack_id}")
    public ResponseEntity<OpenStackResponse> deleteStack(@RequestHeader("token") String token,
            @PathVariable String stack_name, @PathVariable String stack_id) {
        OpenStackResponse openStackResponse = new OpenStackResponse();
        HttpStatus httpStatus;
        try {
            orchestrationService.deleteStack(token, stack_name, stack_id);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            httpStatus = handleError(openStackResponse, e);
        }
        return new ResponseEntity<OpenStackResponse>(openStackResponse, httpStatus);
    }

    private HttpStatus handleError(OpenStackResponse openStackResponse, Exception e) {
        HttpStatus httpStatus;
        openStackResponse.setError(new ErrorInfo(e.getClass().getSimpleName(), e.getMessage()));
        if (e instanceof UnvalidCallException) {
            httpStatus = ((UnvalidCallException) e).getCurrentStatusCode();
        } else {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return httpStatus;
    }
}
