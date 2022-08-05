package unlenen.cloud.openstack.be.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileUploadResponse {
    private String message;
    
    public FileUploadResponse(String message) {
        this.message = message;
    }
    
}
